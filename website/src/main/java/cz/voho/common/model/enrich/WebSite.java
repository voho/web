package cz.voho.common.model.enrich;

import com.google.gson.annotations.SerializedName;
import cz.voho.common.utility.WebsiteConstants;

public class WebSite {
    @SerializedName("@context")
    private String context = "http://schema.org";
    @SerializedName("@type")
    private String type = "WebSite";
    private String name = WebsiteConstants.PREFERED_FULL_NAME;
    private String alternateName = WebsiteConstants.NAME_WITH_ALIAS;
    private String url = WebsiteConstants.WEBSITE_URL_WITH_SLASH;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
