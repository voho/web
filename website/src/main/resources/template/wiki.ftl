<!DOCTYPE HTML>
<html lang="cs">
<#assign title = "${active_wiki_page_title} - ${website_author}">
<#include "html/html-head.ftl"/>
<body>

<div id="wrapper">

<#include "common/header.ftl"/>

    <div id="main">

    <#include "wiki/toc-wrapper.ftl"/>
    <#include "wiki/breadcrumbs.ftl"/>

    <#if active_wiki_page_id == 'index'>

        <section id="tree">
            <div class="inner">
                <#include "wiki/tree.ftl"/>
                <#list indexSubPages.items as pageRef>
                <@print_wiki_tree pageRef 1 3/>
            </#list>
            </div>
        </section>

    <#else>

        <section>
            <div class="inner search">
                <#include "common/search.ftl"/>
                <header class="major">
                    <h1>${active_wiki_page_title}</h1>
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

            <#if !active_wiki_page_cover>
                <#include "wiki/friends.ftl"/>
                <#include "wiki/actions.ftl"/>
            </#if>
        </#if>
    </#if>

    </div>

<#include "common/footer.ftl"/>

</div>

<#include "html/html-scripts.ftl"/>

</body>
</html>
