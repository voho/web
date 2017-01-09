package cz.voho.web.lambda.model.photo;

public class GetRecentPhotosResponse {
    private Images recentItems;

    public Images getRecentItems() {
        return recentItems;
    }

    public void setRecentItems(Images recentItems) {
        this.recentItems = recentItems;
    }
}
