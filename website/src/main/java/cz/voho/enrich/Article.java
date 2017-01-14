package cz.voho.enrich;

import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName("@context")
    private String context = "http://schema.org";
    @SerializedName("@type")
    private String type = "Article";
    private String name;
    private String url;
    private String author;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
