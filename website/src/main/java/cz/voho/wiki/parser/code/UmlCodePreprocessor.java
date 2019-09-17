package cz.voho.wiki.parser.code;

import com.vladsch.flexmark.html.HtmlWriter;
import cz.voho.wiki.repository.image.WikiImageRepository;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UmlCodePreprocessor implements CodeProcessor {
    private static final String UML_CLASS = "uml:class";
    private static final String UML_ACTIVITY = "uml:activity";
    private static final String UML_SEQUENCE = "uml:seq";

    private final String UML_PREFIX = "@startuml\n\n" +
            "skinparam defaultFontName Arial\n" +
            "skinparam style strictuml\n" +
            "skinparam padding 3\n" +
            "skinparam noteFontSize 12\n" +
            "skinparam noteBackgroundColor #f6f6f6\n" +
            "skinparam noteFontColor #666666\n" +
            "skinparam noteBorderColor #CCCCCC\n" +
            "skinparam backgroundColor transparent\n" +
            "skinparam classBackgroundColor #FFFA96\n" +
            "skinparam classBorderColor #999999\n" +
            "skinparam classFontSize 16\n" +
            "skinparam classFontStyle bold\n" +
            "skinparam classAttributeFontSize 12\n" +
            "skinparam activityFontSize 16\n" +
            "skinparam classStereotypeFontSize 12\n" +
            "skinparam classArrowColor #000000\n" +
            "skinparam sequenceDividerFontStyle plain\n" +
            "skinparam sequenceDividerBackgroundColor #f6f6f6\n" +
            "skinparam sequenceDividerFontColor #666666\n" +
            "skinparam sequenceActorFontStyle bold\n" +
            "skinparam sequenceParticipantFontStyle bold\n" +
            "skinparam sequenceArrowColor #333333\n" +
            "skinparam sequenceParticipantBackgroundColor #FFFA96\n" +
            "skinparam sequenceParticipantBorderColor #999999\n" +
            "skinparam sequenceLifeLineBorderColor #999999\n";

    private final String UML_SUFFIX = "\n\n@enduml";

    private final WikiImageRepository wikiImageCacheWarmUp;

    public UmlCodePreprocessor(final WikiImageRepository wikiImageCacheWarmUp) {
        this.wikiImageCacheWarmUp = wikiImageCacheWarmUp;
    }

    @Override
    public boolean handle(final HtmlWriter html, final String codeLang, final String codeSource) {
        if (codeLang.equalsIgnoreCase(UML_CLASS)) {
            uml(html, codeSource, "diagram tříd");
            return true;
        } else if (codeLang.equalsIgnoreCase(UML_ACTIVITY)) {
            uml(html, codeSource, "diagram aktivit");
            return true;
        } else if (codeLang.equalsIgnoreCase(UML_SEQUENCE)) {
            uml(html, codeSource, "sekvenční diagram");
            return true;
        }

        return false;
    }

    private void uml(final HtmlWriter html, final String codeSource, final String alt) {
        final String codeSourceFixed = UML_PREFIX + codeSource + UML_SUFFIX;
        wikiImageCacheWarmUp.warmUpCachePlantUmlSvg(codeSourceFixed);
        final String sourceEncoded = encodeForUrl(codeSourceFixed);
        html.raw(String.format("<div class='figure graph'><img src='/generate/svg/uml?data=%s' alt='%s (UML)' /></div>", sourceEncoded, alt));
    }

    private String encodeForUrl(final String source) {
        return Base64.getUrlEncoder().encodeToString(source.getBytes(StandardCharsets.UTF_8));
    }
}
