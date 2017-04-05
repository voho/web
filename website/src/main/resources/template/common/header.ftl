<#import "../macro/icons.ftl" as icons />
<header id="header">
    <div class="inner">
        <a href="/" class="logo"><strong>${website_short_name}</strong><span class="explain"> = ${website_extended_name}</span></a>
        <a onclick="$('#search').slideToggle(200)" class="button"><@icons.add "search"/> Hledat</a>
    <#if active_wiki_page_toc?has_content && !active_wiki_page_cover>
        <a onclick="$('#toc').slideToggle(200)" class="button"><@icons.add "bars"/> Obsah</a>
    </#if>
    </div>
</header>