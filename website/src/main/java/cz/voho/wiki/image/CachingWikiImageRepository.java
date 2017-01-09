package cz.voho.wiki.image;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import cz.voho.exception.ContentNotFoundException;
import cz.voho.exception.InitializationException;
import cz.voho.utility.ExecutorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CachingWikiImageRepository implements WikiImageRepository, WikiImageCacheWarmUp {
    private static final Logger LOG = LoggerFactory.getLogger(CachingWikiImageRepository.class);
    private static final MessageDigest SHA_256 = getMessageDigest();

    private final WikiImageRepository primaryDelegate;
    private final WikiImageRepository fallbackDelegate;
    private final Cache<String, byte[]> cache;

    public CachingWikiImageRepository(final WikiImageRepository primaryDelegate, final WikiImageRepository fallbackDelegate) {
        this.primaryDelegate = primaryDelegate;
        this.fallbackDelegate = fallbackDelegate;
        this.cache = CacheBuilder.newBuilder().build();
    }

    @Override
    public byte[] generateDotSvg(final String source) throws Exception {
        return generateImage(source, primaryDelegate::generateDotSvg, fallbackDelegate::generateDotSvg);
    }

    @Override
    public byte[] generatePlantUmlSvg(final String source) throws Exception {
        return generateImage(source, primaryDelegate::generatePlantUmlSvg, fallbackDelegate::generatePlantUmlSvg);
    }

    private byte[] generateImage(final String source, final ImageGenerator primaryGenerator, final ImageGenerator fallbackGenerator) {
        final String hash = hash(source);
        final byte[] loaded = cache.getIfPresent(hash);
        if (loaded == null) {
            // create background task to generate the primary image
            ExecutorProvider.IMAGE_GENERATOR_EXECUTOR.submit(() -> {
                try {
                    final byte[] generated = primaryGenerator.generate(source);

                    if (generated != null && generated.length > 0) {
                        cache.put(hash, generated);
                    } else {
                        // the delegate has generated some bullshit
                        LOG.warn("Invalid image returned from the delegate.");
                    }
                } catch (Exception e) {
                    LOG.warn("Error while loading image: " + source, e);
                }
            });
            try {
                // use image from the fallback generator in the meantime
                return fallbackGenerator.generate(source);
            } catch (Exception e) {
                throw new ContentNotFoundException("Fallback generator did not work.");
            }
        }
        return loaded;
    }

    public long getCacheSizeInItems() {
        return cache.size();
    }

    public long getCacheSizeInBytes() {
        return cache.asMap().values().stream().mapToLong(a -> a.length).sum();
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

    @Override
    public void warmUpCacheDotSvg(final String source) {
        try {
            generateDotSvg(source);
        } catch (Exception e) {
            LOG.warn("Error while warming-up cache for DOT graph.");
        }
    }

    @Override
    public void warmUpCachePlantUmlSvg(final String source) {
        try {
            generatePlantUmlSvg(source);
        } catch (Exception e) {
            LOG.warn("Error while warming-up cache for Plant UML graph.");
        }
    }

    @FunctionalInterface
    private interface ImageGenerator {
        byte[] generate(String source) throws Exception;
    }
}
