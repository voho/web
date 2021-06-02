<!DOCTYPE HTML>
<html lang="cs">
<#assign title = "${active_wiki_page_title} - ${website_author}">
<#include "html/html-head.ftl"/>
<body>

<#include "common/header.ftl"/>

<div id="universe">

<#include "wiki/toc-wrapper.ftl"/>
<#include "macro/children_pages.ftl"/>

<#if active_wiki_page_id == 'index'>

    <section id="heading" class="wiki-cover">
        <div class="inner">
            <#list indexSubPages.items as pageRef>
                <header class="major">
                    <h1>${pageRef.title}</h1>
                </header>
                <@wiki_children_pages pageRef.children.items/>
            </#list>
        </div>
    </section>

<#else>

    <#if active_wiki_page_cover>
    <section id="heading" class="wiki-cover">
    <#else>
    <section id="heading">
    </#if>
    <div class="inner">
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
                    <div class="wrapper">
                    ${active_wiki_page_content}
                    </div>
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

<#include "html/html-scripts.ftl"/>

</body>
</html>
