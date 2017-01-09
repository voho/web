package cz.voho.servlet;

import cz.voho.wiki.WikiBackend;
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

    private final WikiBackend wikiBackend = WikiBackend.SINGLETON;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String path = getPath(request);

        try {
            if (path.contains("generate/svg/graph")) {
                final String dataEnc = request.getParameter("data");
                Objects.requireNonNull(dataEnc);
                final String dataDec = new String(Base64.getUrlDecoder().decode(dataEnc));
                final byte[] data = wikiBackend.generateDotSvg(dataDec);
                writeSvgOutput(response, data);
            } else if (path.contains("generate/svg/uml")) {
                final String dataEnc = request.getParameter("data");
                Objects.requireNonNull(dataEnc);
                final String dataDec = new String(Base64.getUrlDecoder().decode(dataEnc));
                final byte[] data = wikiBackend.generatePlantUmlSvg(dataDec);
                writeSvgOutput(response, data);
            } else {
                throw new IllegalStateException("Unsupported generator.");
            }
        } catch (Exception e) {
            // TODO for dev only
            LOG.warn("Error while generating image.", e);
            response.setHeader("Content-Type", "text/plain");
            response.setStatus(500);
            response.getWriter().write(e.getClass().getName());
        }
    }

    private void writeSvgOutput(final HttpServletResponse response, final byte[] data) throws IOException {
        response.setHeader("Content-Type", "image/svg+xml;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache, must-revalidate");
        response.setStatus(200);
        response.getWriter().write(new String(data, StandardCharsets.UTF_8));
    }

    private String getPath(final HttpServletRequest request) {
        String url = request.getRequestURI().toString();
        while (url.startsWith("/")) {
            url = url.substring(1);
        }
        while (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }
}
