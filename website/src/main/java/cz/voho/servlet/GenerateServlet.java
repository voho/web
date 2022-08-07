package cz.voho.servlet;

import cz.voho.common.utility.WikiLinkUtility;
import cz.voho.facade.Backend;
import cz.voho.facade.WikiBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class GenerateServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(GenerateServlet.class);

    private final WikiBackend wikiBackend = Backend.SINGLETON.getWiki();

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String path = WikiLinkUtility.stripSlashes(request.getRequestURI());

        try {
            if (path.contains("generate/svg/graph")) {
                final String dataEnc = request.getParameter("data");
                Objects.requireNonNull(dataEnc);
                final String dataDec = new String(Base64.getUrlDecoder().decode(dataEnc));
                LOG.info("Generating SVG Graph: {}", dataDec);
                final byte[] data = wikiBackend.generateDotSvg(dataDec);
                LOG.info("Got Graph output: {}", new String(data, StandardCharsets.UTF_8));
                writeSvgOutput(response, data);
            } else if (path.contains("generate/svg/uml")) {
                final String dataEnc = request.getParameter("data");
                Objects.requireNonNull(dataEnc);
                final String dataDec = new String(Base64.getUrlDecoder().decode(dataEnc));
                LOG.info("Generating SVG UML: {}", dataDec);
                final byte[] data = wikiBackend.generatePlantUmlSvg(dataDec);
                LOG.info("Got UML output: {}", new String(data, StandardCharsets.UTF_8));
                writeSvgOutput(response, data);
            } else {
                throw new IllegalStateException("Unsupported generator.");
            }
        } catch (Exception e) {
            LOG.warn("Error while generating image.", e);
            response.setHeader("Content-Type", "text/plain");
            response.setStatus(500);
            e.printStackTrace(response.getWriter());
        } finally {
            response.getWriter().flush();
            response.flushBuffer();
        }
    }

    private void writeSvgOutput(final HttpServletResponse response, final byte[] data) throws IOException {
        response.setHeader("Content-Type", "image/svg+xml;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache, must-revalidate");
        response.setStatus(200);
        response.getWriter().write(new String(data, StandardCharsets.UTF_8));
    }
}
