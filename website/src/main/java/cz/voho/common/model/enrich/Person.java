package cz.voho.common.model.enrich;

import com.google.gson.annotations.SerializedName;
import cz.voho.common.utility.Constants;

public class Person {
    @SerializedName("@context")
    private String context = "http://schema.org";
    @SerializedName("@type")
    private String type = "Person";
    private String name = Constants.OFFICIAL_FULL_NAME;
    private String url = Constants.WEBSITE_URL_WITH_SLASH;
    private String honorificPrefix = Constants.DEGREE;
    private String givenName = Constants.OFFICIAL_FIRST_NAME;
    private String familyName = Constants.OFFICIAL_FAMILY_NAME;
    private String jobTitle = Constants.JOB_TITLE;
    private String gender = Constants.GENDER;
    private String email = Constants.EMAIL;
    private String homeLocation = Constants.HOME_LOCATION;
    private String[] sameAs = {
            Constants.PROFILE_LINKED_IN,
            Constants.PROFILE_GITHUB,
            Constants.PROFILE_TWITTER,
            Constants.PROFILE_INSTAGRAM,
            Constants.PROFILE_FLICKR,
            Constants.PROFILE_SOUNDCLOUD,
            Constants.PROFILE_SPOTIFY,
            Constants.PROFILE_ITUNES,
            Constants.PROFILE_YOUTUBE,
            Constants.PROFILE_AMAZON,
            Constants.PROFILE_GOOGLE_MUSIC
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
