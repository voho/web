package cz.voho.external;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class Configuration {
    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);
    private static final String TABLE_NAME = "voho_configuration";
    private static final String KEY_COLUMN = "key";
    private static final String VALUE_COLUMN = "value";

    private final AmazonDynamoDB dynamoDB;

    public Configuration(final AmazonDynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    String getValue(final String key) {
        LOG.info("Loading configuration {}...", key);

        final GetItemRequest request = new GetItemRequest();
        request.setTableName(TABLE_NAME);
        request.setKey(Collections.singletonMap(KEY_COLUMN, new AttributeValue(key)));

        final GetItemResult response = dynamoDB.getItem(request);

        return response.getItem().get(VALUE_COLUMN).getS();
    }
}
