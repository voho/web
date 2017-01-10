<!DOCTYPE HTML>
<html lang="cs">
<#assign title = "${active_wiki_page_title} - ${website_author}">
<#include "html/html-head.ftl"/>
<body>

<div id="wrapper">

<#include "common/header.ftl"/>

    <div id="main">

    <#include "wiki/breadcrumbs.ftl"/>

    <#if active_wiki_page_id == 'index'>

        <#include "wiki/tiles.ftl"/>

        <section>
            <div class="inner">
                <#include "common/search.ftl"/>
            </div>
        </section>

    <#else>

        <section>
            <div class="inner">
                <header class="major">
                    <h1>
                        <#if active_wiki_page_parent_id?has_content>
                            <a href="/wiki/${active_wiki_page_parent_id}/" class="icon fa-arrow-left"
                                    title="Zpět na rodičovskou stránku"></a>
                        </#if>
                    ${active_wiki_page_title}
                    </h1>
                </header>

                <#include "wiki/children.ftl"/>
            </div>
        </section>

        <#if active_wiki_page_content?has_content>
            <#if active_wiki_page_cover>
                <section id="wiki-cover" class="wiki">
                    <div class="inner">
                    ${active_wiki_page_content}
                    </div>
                </section>
            <#else>
                <section id="wiki-content" class="wiki">
                    <div class="inner">
                    ${active_wiki_page_content}
                    </div>
                </section>
            </#if>
        </#if>

        <#include "wiki/actions.ftl"/>
        <#include "wiki/friends.ftl"/>
    </#if>

    </div>

<#assign footer_icons = true/>
<#include "common/footer.ftl"/>

</div>

<#include "html/html-scripts.ftl"/>

</body>
</html>