package cz.voho.facade;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import cz.voho.common.utility.LambdaClient;
import cz.voho.external.Configuration;
import cz.voho.wiki.parser.CustomWikiParser;
import cz.voho.wiki.parser.WikiParser;
import cz.voho.wiki.repository.image.CachingWikiImageRepository;
import cz.voho.wiki.repository.image.LambdaWikiImageRepository;
import cz.voho.wiki.repository.image.NoWikiImageRepository;
import cz.voho.wiki.repository.image.WikiImageRepository;
import cz.voho.wiki.repository.page.DefaultParsedWikiPageRepository;
import cz.voho.wiki.repository.page.DefaultWikiPageSourceRepository;
import cz.voho.wiki.repository.page.ParsedWikiPageRepository;
import cz.voho.wiki.repository.page.WikiPageSourceRepository;
import org.apache.http.impl.client.HttpClientBuilder;

public class Backend {
    public static final Backend SINGLETON = new Backend();

    private final boolean isLocalDev = System.getProperty("os.name").contains("Windows");
    private final LambdaClient lambdaClient = new LambdaClient();
    private final LambdaWikiImageRepository wikiImageRepositoryDelegate = new LambdaWikiImageRepository(lambdaClient);
    private final WikiImageRepository wikiImageRepository = isLocalDev ? new NoWikiImageRepository() : new CachingWikiImageRepository(wikiImageRepositoryDelegate);
    private final WikiParser wikiParser = new CustomWikiParser(wikiImageRepository);
    private final WikiPageSourceRepository wikiPageSourceRepository = new DefaultWikiPageSourceRepository();
    private final ParsedWikiPageRepository parsedWikiPageRepository = new DefaultParsedWikiPageRepository(wikiPageSourceRepository, wikiParser);
    private final WikiBackend wikiBackend = new WikiBackend(wikiPageSourceRepository, parsedWikiPageRepository, wikiImageRepository);
    private final AmazonDynamoDB dynamoDB = AmazonDynamoDBClient.builder().withRegion(Regions.EU_WEST_1).build();
    private final Configuration configuration = new Configuration(dynamoDB);
    private final RecentBackend recentBackend = new RecentBackend(HttpClientBuilder.create().build(), configuration);

    public RecentBackend getRecentBackend() {
        return recentBackend;
    }

    public WikiBackend getWikiBackend() {
        return wikiBackend;
    }
}
