package cz.voho.wiki.repository.image;

public interface WikiImageRepository {
    byte[] generateDotSvg(String source) throws Exception;

    byte[] generatePlantUmlSvg(String source) throws Exception;

    void warmUpCacheDotSvg(String source);

    void warmUpCachePlantUmlSvg(String source);
}
