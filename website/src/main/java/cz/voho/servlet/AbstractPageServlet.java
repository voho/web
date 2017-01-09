package cz.voho.servlet;

import freemarker.cache.ClassTemplateLoader;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Locale;

public abstract class AbstractPageServlet extends FreemarkerServlet {
    @Override
    protected final TemplateModel createModel(final ObjectWrapper objectWrapper, final ServletContext servletContext, final HttpServletRequest request, final HttpServletResponse response) throws TemplateModelException {
        final SimpleHash model = new SimpleHash(objectWrapper);
        updateModel(request, model);
        return model;
    }

    protected void updateModel(final HttpServletRequest request, final SimpleHash model) {
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
        model.put("website_author", "Vojtěch Hordějčuk");
        model.put("website_short_name", "voho");
        model.put("website_full_name", "Vojta Hordějčuk aka voho");
        model.put("website_full_description", "Software Engineer and Bedroom Music Producer");
        model.put("current_year", String.valueOf(LocalDate.now().getYear()));
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
