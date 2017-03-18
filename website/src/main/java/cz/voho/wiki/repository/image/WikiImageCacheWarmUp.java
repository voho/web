package cz.voho.wiki.repository.image;

public interface WikiImageCacheWarmUp {
    void warmUpCacheDotSvg(String source);

    void warmUpCachePlantUmlSvg(String source);
}
