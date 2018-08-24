package cz.voho.common.model.enrich;

import com.google.gson.annotations.SerializedName;
import cz.voho.common.utility.WebsiteConstants;

public class Person {
    @SerializedName("@context")
    private String context = "http://schema.org";
    @SerializedName("@type")
    private String type = "Person";
    private String name = WebsiteConstants.OFFICIAL_FULL_NAME;
    private String url = WebsiteConstants.WEBSITE_URL_WITH_SLASH;
    private String honorificPrefix = WebsiteConstants.DEGREE;
    private String givenName = WebsiteConstants.OFFICIAL_FIRST_NAME;
    private String familyName = WebsiteConstants.OFFICIAL_FAMILY_NAME;
    private String jobTitle = WebsiteConstants.JOB_TITLE;
    private String gender = WebsiteConstants.GENDER;
    private String email = WebsiteConstants.EMAIL;
    private String homeLocation = WebsiteConstants.HOME_LOCATION;
    private String[] sameAs = {
            WebsiteConstants.PROFILE_LINKED_IN,
            WebsiteConstants.PROFILE_GITHUB,
            WebsiteConstants.PROFILE_TWITTER,
            WebsiteConstants.PROFILE_INSTAGRAM,
            WebsiteConstants.PROFILE_FLICKR,
            WebsiteConstants.PROFILE_SOUNDCLOUD,
            WebsiteConstants.PROFILE_SPOTIFY,
            WebsiteConstants.PROFILE_ITUNES,
            WebsiteConstants.PROFILE_YOUTUBE,
            WebsiteConstants.PROFILE_AMAZON,
            WebsiteConstants.PROFILE_GOOGLE_MUSIC
    };

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

    public String getHonorificPrefix() {
        return honorificPrefix;
    }

    public void setHonorificPrefix(String honorificPrefix) {
        this.honorificPrefix = honorificPrefix;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String[] getSameAs() {
        return sameAs;
    }

    public void setSameAs(String[] sameAs) {
        this.sameAs = sameAs;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
