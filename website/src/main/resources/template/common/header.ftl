<#import "../macro/icons.ftl" as icons />
<header id="header">
    <div class="inner">
        <a href="/" class="logo"><strong>${website_short_name}</strong><span class="explain"> = ${website_extended_name}</span></a>
        <span id="search">
            <@icons.add "search"/>
            <form action="http://google.cz/search" method="get">
                <input id="search-query" name="q" type="text" size="30" placeholder=""/>
                <input name="hl" type="hidden" value="cs"/>
                <input name="sitesearch" type="hidden" value="voho.eu"/>
                <input class="button" type="submit" value="Hledat"/>
            </form>
        </span>
        <#if active_wiki_page_toc?has_content && !active_wiki_page_cover>
            <a onclick="displayTocBar()" class="button"><@icons.add "bars"/> Obsah</a>
        </#if>
    </div>
</header>