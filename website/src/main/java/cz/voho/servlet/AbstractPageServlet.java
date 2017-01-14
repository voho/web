package cz.voho.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.voho.enrich.*;
import freemarker.cache.ClassTemplateLoader;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.template.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Locale;

public abstract class AbstractPageServlet extends FreemarkerServlet {
    @Override
    protected final TemplateModel createModel(final ObjectWrapper objectWrapper, final ServletContext servletContext, final HttpServletRequest request, final HttpServletResponse response) throws TemplateModelException {
        MetaDataRoot metaDataRoot = new MetaDataRoot();
        final SimpleHash model = new SimpleHash(objectWrapper);
        updateModel(request, model, metaDataRoot);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (metaDataRoot.getPerson() != null) {
            model.put("enrich_person", gson.toJson(metaDataRoot.getPerson()));
        }
        if (metaDataRoot.getWebSite() != null) {
            model.put("enrich_website", gson.toJson(metaDataRoot.getWebSite()));
        }
        if (metaDataRoot.getArticles() != null) {
            model.put("enrich_articles", gson.toJson(metaDataRoot.getArticles()));
        }
        if (metaDataRoot.getBreadcrumbs() != null) {
            model.put("enrich_breadcrumbs", gson.toJson(metaDataRoot.getBreadcrumbs()));
        }
        return model;
    }

    protected void updateModel(final HttpServletRequest request, final SimpleHash model, final MetaDataRoot metaDataRoot) {
        model.put("social_profile_email", "mailto:vojtech.hordejcuk@gmail.com");
        model.put("social_profile_linkedin", "http://www.linkedin.com/in/vohocz");
        model.put("social_profile_github", "https://github.com/voho");
        model.put("social_profile_twitter", "http://twitter.com/vohocz");
        model.put("social_profile_instagram", "https://www.instagram.com/vohocz/");
        model.put("social_profile_flickr", "https://www.flickr.com/photos/vohocz/");
        model.put("social_profile_soundcloud", "http://soundcloud.com/voho");
        model.put("social_profile_spotify", "https://open.spotify.com/artist/02pC7TpobLA5my9tbGgNyl");
        model.put("social_profile_itunes", "https://itunes.apple.com/us/artist/vojtech-hordejcuk/id929462760");
        model.put("social_profile_amazon", "https://www.amazon.com/s/ref=ntt_srch_drd_B00OHRU6TC?ie=UTF8&amp;field-keywords=Vojtech%20Hordejcuk&amp;index=digital-music&amp;search-type=ss");
        model.put("social_profile_google", "https://play.google.com/store/music/artist/Vojtech_Hordejcuk?id=Ajqelbv4qqwma7ewjtd6pkmkkie");
        model.put("website_external_url", "http://voho.eu/");
        model.put("website_author", "Vojtěch Hordějčuk");
        model.put("website_short_name", "voho");
        model.put("website_extended_name", "Vojta Hordějčuk");
        model.put("website_full_name", "Vojta Hordějčuk aka voho");
        model.put("website_full_description", "Software Engineer and Bedroom Music Producer");
        model.put("current_year", String.valueOf(LocalDate.now().getYear()));

        metaDataRoot.setPerson(new Person());
        metaDataRoot.setWebSite(new WebSite());
        metaDataRoot.setArticles(new Article[0]);
        metaDataRoot.setBreadcrumbs(new BreadcrumbList());

        metaDataRoot.getPerson().setSameAs(new String[]{
                "http://www.linkedin.com/in/vohocz",
                "https://github.com/voho",
                "http://twitter.com/vohocz",
                "https://www.instagram.com/vohocz/",
                "https://www.flickr.com/photos/vohocz/",
                "http://soundcloud.com/voho",
                "https://open.spotify.com/artist/02pC7TpobLA5my9tbGgNyl",
                "https://itunes.apple.com/us/artist/vojtech-hordejcuk/id929462760",
                "https://www.amazon.com/s/ref=ntt_srch_drd_B00OHRU6TC?ie=UTF8&amp;field-keywords=Vojtech%20Hordejcuk&amp;index=digital-music&amp;search-type=ss",
                "https://play.google.com/store/music/artist/Vojtech_Hordejcuk?id=Ajqelbv4qqwma7ewjtd6pkmkkie"
        });
    }

    @Override
    protected Configuration createConfiguration() {
        final Configuration config = super.createConfiguration();
        config.setTemplateLoader(new ClassTemplateLoader(Thread.currentThread().getContextClassLoader(), "template/"));
        config.setEncoding(Locale.ROOT, StandardCharsets.UTF_8.name());
        config.setTemplateUpdateDelayMilliseconds(5000);
        return config;
    }
}
