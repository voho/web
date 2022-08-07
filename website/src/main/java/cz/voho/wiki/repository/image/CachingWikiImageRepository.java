package cz.voho.wiki.repository.image;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.io.Resources;
import cz.voho.common.exception.InitializationException;
import cz.voho.common.utility.ExecutorProvider;
import cz.voho.external.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CachingWikiImageRepository implements WikiImageRepository {
    private static final Logger LOG = LoggerFactory.getLogger(CachingWikiImageRepository.class);
    private static final int NUM_RETRIES = 3;
    private static final byte[] DUMMY_IMAGE = loadDummyImage();
    private static final MessageDigest SHA_256 = getMessageDigest();

    private final Configuration configuration;
    private final WikiImageRepository primaryDelegate;
    private final Cache<String, byte[]> cache;

    public CachingWikiImageRepository(final Configuration configuration, final WikiImageRepository primaryDelegate) {
        this.configuration = configuration;
        this.primaryDelegate = primaryDelegate;
        cache = CacheBuilder.newBuilder().build();
    }

    @Override
    public byte[] generateDotSvg(final String source) {
        return generateImage(source, primaryDelegate::generateDotSvg);
    }

    @Override
    public byte[] generatePlantUmlSvg(final String source) {
        return generateImage(source, primaryDelegate::generatePlantUmlSvg);
    }

    private byte[] generateImage(final String source, final ImageGenerator primaryGenerator) {
        if (configuration.isImageOffline()) {
            LOG.warn("Image offline, returning a dummy image.");
            return DUMMY_IMAGE;
        }

        final String hash = hash(source);
        final byte[] loaded = cache.getIfPresent(hash);

        if (loaded == null) {
            LOG.warn("Image being generated in background, returning a dummy image.");
            generateImageInBackground(source, primaryGenerator, hash);
            return DUMMY_IMAGE;
        }

        return loaded;
    }

    private void generateImageInBackground(final String source, final ImageGenerator primaryGenerator, final String hash) {
        ExecutorProvider.IMAGE_GENERATOR_EXECUTOR.submit(() -> {
            int attemptsLeft = NUM_RETRIES;

            while (attemptsLeft > 0) {
                try {
                    final byte[] generated = primaryGenerator.generate(source);

                    if (generated != null && generated.length > 0) {
                        LOG.debug("Generated image put into the cache: {}", hash);
                        cache.put(hash, generated);
                        attemptsLeft = 0;
                    } else {
                        // the delegate has generated some bullshit
                        LOG.warn("Invalid image returned from the delegate.");
                        attemptsLeft = 0;
                    }
                } catch (Exception e) {
                    LOG.warn("Error while loading image: " + source, e);
                    attemptsLeft--;
                }
            }
        });
    }

    public long getCacheSizeInItems() {
        return cache.size();
    }

    public long getCacheSizeInBytes() {
        return cache.asMap().values().stream().mapToLong(a -> a.length).sum();
    }

    @Override
    public void warmUpCacheDotSvg(final String source) {
        try {
            generateDotSvg(source);
        } catch (Exception e) {
            LOG.warn("Error while warming-up cache for DOT graph.", e);
        }
    }

    @Override
    public void warmUpCachePlantUmlSvg(final String source) {
        try {
            generatePlantUmlSvg(source);
        } catch (Exception e) {
            LOG.warn("Error while warming-up cache for Plant UML graph.", e);
        }
    }

    @FunctionalInterface
    private interface ImageGenerator {
        byte[] generate(String source) throws Exception;
    }

    private static byte[] loadDummyImage() {
        try {
            return Resources.toByteArray(Resources.getResource("loading.svg"));
        } catch (IOException e) {
            throw new InitializationException("Unable to load dummy image.", e);
        }
    }

    private static String hash(final String data) {
        return hash(data.getBytes(StandardCharsets.UTF_8));
    }

    private static String hash(final byte[] data) {
        final byte[] hash = SHA_256.digest(data);
        return Base64.getEncoder().encodeToString(hash);
    }

    private static MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new InitializationException("Unable to load message digest.", e);
        }
    }
}
