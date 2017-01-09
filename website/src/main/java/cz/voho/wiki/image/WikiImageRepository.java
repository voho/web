package cz.voho.wiki.image;

public interface WikiImageRepository {
    byte[] generateDotSvg(String source) throws Exception;

    byte[] generatePlantUmlSvg(String source) throws Exception;
}
