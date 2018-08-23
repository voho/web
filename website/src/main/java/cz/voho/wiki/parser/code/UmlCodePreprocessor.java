package cz.voho.wiki.parser.code;

import com.vladsch.flexmark.html.HtmlWriter;
import net.sourceforge.plantuml.code.Transcoder;
import net.sourceforge.plantuml.code.TranscoderUtil;

import java.io.IOException;

public class UmlCodePreprocessor implements CodeProcessor {
    private static final String UML_CLASS = "uml:class";
    private static final String UML_ACTIVITY = "uml:activity";
    private static final String UML_SEQUENCE = "uml:seq";

    private static final String PLANT_UML_IMAGE_URL_FORMAT = "https://www.plantuml.com/plantuml/svg/%s";
    private static final String PLANT_UML_EDIT_URL_FORMAT = "https://www.planttext.com/?text=%s";
    private static final Transcoder PLANT_TEXT_TRANSCODER = TranscoderUtil.getDefaultTranscoder();

    private final String UML_PREFIX = "@startuml\n\n" +
            "skinparam defaultFontName Arial\n" +
            "skinparam style strictuml\n" +
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
        final String codeSourceEncoded = transcodeForUrl(codeSourceFixed);
        final String imageUrl = getUmlImageUrl(codeSourceEncoded);
        final String editUrl = getUmlEditUrl(codeSourceEncoded);
        html.raw(String.format(
                "<div class='figure uml'><img src='%s' alt='UML (%s)' /><p class='code-included-disclaimer'><a href='%s'>Upravit</a></p></div>",
                imageUrl,
                alt,
                editUrl));
    }

    private String transcodeForUrl(final String codeSourceFixed) {
        try {
            return PLANT_TEXT_TRANSCODER.encode(codeSourceFixed);
        } catch (IOException e) {
            throw new RuntimeException("Error while generating UML image URL.", e);
        }
    }

    private String getUmlImageUrl(final String codeSourceEncoded) {
        return String.format(PLANT_UML_IMAGE_URL_FORMAT, codeSourceEncoded);
    }

    private String getUmlEditUrl(final String codeSourceEncoded) {
        return String.format(PLANT_UML_EDIT_URL_FORMAT, codeSourceEncoded);
    }
}
