package cz.voho.wiki.repository.image;

public class NoWikiImageRepository implements WikiImageRepository {
    @Override
    public byte[] generateDotSvg(String source) throws Exception {
        return new byte[0];
    }

    @Override
    public byte[] generatePlantUmlSvg(String source) throws Exception {
        return new byte[0];
    }

    @Override
    public void warmUpCacheDotSvg(String source) {
        // NOP
    }

    @Override
    public void warmUpCachePlantUmlSvg(String source) {
        // NOP
    }
}
