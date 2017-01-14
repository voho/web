package cz.voho.enrich;

import com.google.gson.annotations.SerializedName;

public class ListItem {
    @SerializedName("@context")
    private String context = "http://schema.org";
    @SerializedName("@type")
    private String type = "ListItem";
    private int position;
    private Item item;

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
