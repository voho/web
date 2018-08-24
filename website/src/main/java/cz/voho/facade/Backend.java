package cz.voho.facade;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import cz.voho.common.utility.LambdaClient;
import cz.voho.external.Configuration;
import cz.voho.wiki.parser.CustomWikiParser;
import cz.voho.wiki.parser.WikiParser;
import cz.voho.wiki.repository.image.CachingWikiImageRepository;
import cz.voho.wiki.repository.image.LambdaWikiImageRepository;
import cz.voho.wiki.repository.image.WikiImageRepository;
import cz.voho.wiki.repository.page.DefaultParsedWikiPageRepository;
import cz.voho.wiki.repository.page.DefaultWikiPageSourceRepository;
import cz.voho.wiki.repository.page.ParsedWikiPageRepository;
import cz.voho.wiki.repository.page.WikiPageSourceRepository;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Backend {
    public static final Backend SINGLETON = new Backend();

    private final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private final DefaultAWSCredentialsProviderChain awsCredentials = new DefaultAWSCredentialsProviderChain();
    private final AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.standard().withCredentials(awsCredentials).withRegion(Regions.EU_WEST_1).build();
    private final AWSLambda lambda = AWSLambdaClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain()).withRegion(Regions.EU_WEST_1).build();
    private final Configuration configuration = new Configuration(dynamoDB);
    private final LambdaClient lambdaClient = new LambdaClient(lambda);
    private final LambdaWikiImageRepository wikiImageRepositoryDelegate = new LambdaWikiImageRepository(lambdaClient);
    private final WikiImageRepository wikiImageRepository = new CachingWikiImageRepository(wikiImageRepositoryDelegate);
    private final WikiParser wikiParser = new CustomWikiParser(wikiImageRepository);
    private final WikiPageSourceRepository wikiPageSourceRepository = new DefaultWikiPageSourceRepository();
    private final ParsedWikiPageRepository parsedWikiPageRepository = new DefaultParsedWikiPageRepository(wikiPageSourceRepository, wikiParser);
    private final WikiBackend wikiBackend = new WikiBackend(wikiPageSourceRepository, parsedWikiPageRepository, wikiImageRepository);
    private final RecentBackend recentBackend = new RecentBackend(httpClient, configuration);

    public RecentBackend getRecent() {
        return recentBackend;
    }

    public WikiBackend getWiki() {
        return wikiBackend;
    }
}
