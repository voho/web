package cz.voho.common.model.enrich;

public class MetaDataRoot {
    private Person person = new Person();
    private WebSite webSite = new WebSite();
    private Article[] articles = new Article[0];
    private BreadcrumbList breadcrumbs = new BreadcrumbList();

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public WebSite getWebSite() {
        return webSite;
    }

    public void setWebSite(WebSite webSite) {
        this.webSite = webSite;
    }

    public Article[] getArticles() {
        return articles;
    }

    public void setArticles(Article[] articles) {
        this.articles = articles;
    }

    public BreadcrumbList getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(BreadcrumbList breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }
}
