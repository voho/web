package cz.voho.wiki.image;

public interface WikiImageCacheWarmUp {
    void warmUpCacheDotSvg(String source);

    void warmUpCachePlantUmlSvg(String source);
}
