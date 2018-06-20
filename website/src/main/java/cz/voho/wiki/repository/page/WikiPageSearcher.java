package cz.voho.wiki.repository.page;

import com.google.common.html.HtmlEscapers;
import cz.voho.wiki.model.ParsedWikiPage;
import cz.voho.wiki.model.WikiPageSearchResult;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cz.CzechAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WikiPageSearcher {
    private static final Logger LOG = LoggerFactory.getLogger(WikiPageSearcher.class);
    private static final int MAX_FRAGMENTS = 3;
    private static final int FRAGMENT_SIZE = 100;

    private final RAMDirectory index = new RAMDirectory();
    private final Analyzer analyzer = new CzechAnalyzer();
    private final Formatter formatter = new SimpleHTMLFormatter("{{{{{", "}}}}}");

    public List<WikiPageSearchResult> searchIndex(String q, int hitsPerPage) throws IOException, InvalidTokenOffsetsException, ParseException {
        String field = "source";
        QueryParser parser = new QueryParser(field, analyzer);
        Query query = parser.parse(q);

        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(query, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        QueryScorer scorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, scorer);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, FRAGMENT_SIZE);
        highlighter.setTextFragmenter(fragmenter);

        List<WikiPageSearchResult> result = new ArrayList<>(hitsPerPage);

        for (int i = 0; i < hits.length; i++) {
            int docid = hits[i].doc;
            Document doc = searcher.doc(docid);

            String id = doc.get("id");
            String title = doc.get("title");
            String source = doc.get("source");

            TokenStream stream = analyzer.tokenStream(field, source);

            String[] frags = highlighter.getBestFragments(stream, source, MAX_FRAGMENTS);

            for (int j = 0; j < frags.length; j++) {
                frags[j] = HtmlEscapers.htmlEscaper().escape(frags[j]).replace("{{{{{", "<b>").replace("}}}}}", "</b>");
            }

            WikiPageSearchResult sr = new WikiPageSearchResult();
            sr.setPageId(id);
            sr.setPageTitle(title);
            sr.setFragments(Arrays.asList(frags));
            result.add(sr);
        }

        return result;
    }

    public void addToIndex(Iterable<ParsedWikiPage> pages) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try (IndexWriter w = new IndexWriter(index, config)) {
            for (ParsedWikiPage page : pages) {
                LOG.debug("Indexing: " + page.getSource().getId());

                Document doc = new Document();
                doc.add(new StringField("id", page.getSource().getId(), Field.Store.YES));
                doc.add(new TextField("title", page.getTitle(), Field.Store.YES));
                doc.add(new TextField("source", page.getSource().getMarkdownSource(), Field.Store.YES));

                w.addDocument(doc);
            }
        }

        LOG.info("Page indexing finished.");
    }
}
