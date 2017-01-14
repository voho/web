package cz.voho.enrich;

import com.google.gson.annotations.SerializedName;

public class BreadcrumbList {
    @SerializedName("@context")
    private String context = "http://schema.org";
    @SerializedName("@type")
    private String type = "BreadcrumbList";
    private ListItem[] itemListElement;

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

    public ListItem[] getItemListElement() {
        return itemListElement;
    }

    public void setItemListElement(ListItem[] itemListElement) {
        this.itemListElement = itemListElement;
    }
}
