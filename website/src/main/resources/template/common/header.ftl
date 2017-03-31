<#import "../macro/icons.ftl" as icons />
<header id="header">
    <div class="inner">
        <a href="/" class="logo"><strong>${website_short_name}</strong><span class="explain"> = ${website_extended_name}</span></a>
    <#if active_wiki_page_toc?has_content && !active_wiki_page_cover>
        <a onclick="$('#toc').toggle()" class="button"><@icons.add "bars"/> Obsah</a>
    </#if>
    </div>
</header>