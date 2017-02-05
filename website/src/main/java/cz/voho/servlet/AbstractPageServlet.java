package cz.voho.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.voho.enrich.MetaDataRoot;
import cz.voho.utility.Constants;
import freemarker.cache.ClassTemplateLoader;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.template.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Locale;

public abstract class AbstractPageServlet extends FreemarkerServlet {
    @Override
    protected boolean preprocessRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        return super.preprocessRequest(request, response);
    }

    @Override
    protected final TemplateModel createModel(final ObjectWrapper objectWrapper, final ServletContext servletContext, final HttpServletRequest request, final HttpServletResponse response) throws TemplateModelException {
        final MetaDataRoot metaDataRoot = new MetaDataRoot();
        final SimpleHash model = new SimpleHash(objectWrapper);
        updateModel(request, model, metaDataRoot);
        updateModelWithMeta(request, model, metaDataRoot);
        return model;
    }

    protected void updateModel(final HttpServletRequest request, final SimpleHash model, final MetaDataRoot metaDataRoot) {
        model.put("social_profile_email", "mailto:" + Constants.EMAIL);
        model.put("social_profile_linkedin", Constants.PROFILE_LINKED_IN);
        model.put("social_profile_github", Constants.PROFILE_GITHUB);
        model.put("social_profile_twitter", Constants.PROFILE_TWITTER);
        model.put("social_profile_instagram", Constants.PROFILE_INSTAGRAM);
        model.put("social_profile_flickr", Constants.PROFILE_FLICKR);
        model.put("social_profile_soundcloud", Constants.PROFILE_SOUNDCLOUD);
        model.put("social_profile_spotify", Constants.PROFILE_SPOTIFY);
        model.put("social_profile_itunes", Constants.PROFILE_ITUNES);
        model.put("social_profile_amazon", Constants.PROFILE_AMAZON);
        model.put("social_profile_google", Constants.PROFILE_GOOGLE_MUSIC);
        model.put("social_profile_youtube", Constants.PROFILE_YOUTUBE);
        model.put("website_external_url", Constants.WEBSITE_URL_WITH_SLASH);
        model.put("website_author", Constants.OFFICIAL_FULL_NAME);
        model.put("website_short_name", Constants.NICKNAME);
        model.put("website_extended_name", Constants.PREFERED_FULL_NAME);
        model.put("website_full_name", Constants.NAME_WITH_ALIAS);
        model.put("website_full_description", Constants.JOB_TITLE);
        model.put("current_year", String.valueOf(LocalDate.now().getYear()));
        model.put("website_author_age", LocalDate.of(1987, 9, 16).until(LocalDate.now()).getYears());
    }

    protected void updateModelWithMeta(final HttpServletRequest request, final SimpleHash model, final MetaDataRoot metaDataRoot) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

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
    }

    @Override
    protected Configuration createConfiguration() {
        final Configuration config = super.createConfiguration();
        config.setTemplateLoader(new ClassTemplateLoader(Thread.currentThread().getContextClassLoader(), "template/"));
        config.setEncoding(Locale.ROOT, StandardCharsets.UTF_8.name());
        config.setDefaultEncoding("UTF-8");
        return config;
    }
}
