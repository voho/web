package cz.voho.wiki.parser;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiPageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FlexmarkWikiParser implements WikiParser {
    private static final Logger LOG = LoggerFactory.getLogger(FlexmarkWikiParser.class);
    private static final String NO_HEADER = "N/A";

    private final Parser parser;
    private final HtmlRenderer renderer;
    private final Iterable<Preprocessor> preprocessors;

    public FlexmarkWikiParser(final Preprocessor... preprocessors) {
        final MutableDataHolder options = new MutableDataSet();

        final List<Extension> extensions = new LinkedList<>();
        extensions.add(TablesExtension.create());
        extensions.add(AutolinkExtension.create());
        extensions.add(DefinitionExtension.create());

        options.setFrom(ParserEmulationProfile.COMMONMARK.getOptions())
                .set(Parser.EXTENSIONS, extensions)
                .set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, "")
                .set(HtmlRenderer.GENERATE_HEADER_ID, false)
                .set(HtmlRenderer.RENDER_HEADER_ID, false)
                .set(HtmlRenderer.SOFT_BREAK, " ");

        Collections.addAll(extensions, preprocessors);

        this.parser = Parser.builder(options).build();
        this.renderer = HtmlRenderer.builder(options).build();
        this.preprocessors = Arrays.asList(preprocessors);
    }

    @Override
    public ParsedWikiPage parse(final WikiPageSource source) {
        LOG.debug("Parsing the wiki page: {}", source.getId());
        final ParsedWikiPage page = new ParsedWikiPage();
        page.setSource(source);
        preprocessSource(page);
        final Node pageRootNode = parser.parse(page.getSource().getMarkdownSource());
        preprocessNodes(page, pageRootNode);
        final String firstHeading = findFirstHeadingAndRemove(pageRootNode);
        page.setTitle(firstHeading);
        final String parsedSource = renderer.render(pageRootNode);
        page.setHtml(parsedSource);
        page.setCover(containsCoverNodesOnly(pageRootNode));
        return page;
    }

    private void preprocessNodes(final ParsedWikiPage context, final Node pageRootNode) {
        for (final Preprocessor p : preprocessors) {
            p.preprocessNodes(context, pageRootNode);
        }
    }

    private void preprocessSource(final ParsedWikiPage context) {
        for (final Preprocessor p : preprocessors) {
            p.preprocessSource(context);
        }
    }

    private String findFirstHeadingAndRemove(final Node pageRootNode) {
        for (final Node temp : pageRootNode.getDescendants()) {
            if (temp instanceof Heading) {
                // remove first heading
                temp.unlink();
                // return the first heading text
                return ((Heading) temp).getText().toString();
            }
        }

        return NO_HEADER;
    }

    private boolean containsCoverNodesOnly(final Node pageRootNode) {
        for (final Node temp : pageRootNode.getChildren()) {
            if (temp instanceof Heading) {
                // headings are allowed
            } else if (temp instanceof BlockQuote) {
                // blockquotes are allowed
            } else {
                return false;
            }
        }

        return true;
    }
}
