<!--

    Vojtěch Hordějčuk: Personal website

	HTML Template: Forty by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)

-->
<#if alt??>
<header id="header" class="alt">
<#else>
<header id="header">
</#if>
    <a href="/" class="logo"><strong>${website_short_name}</strong> <span> = ${website_extended_name}</span></a>
    <nav>
        <a href="#menu">Menu</a>
    <#if active_wiki_page_toc?has_content && !active_wiki_page_cover>
        <a onclick="$('#toc').fadeToggle();" class="icon fa-map-o">Obsah</a>
    </#if>
    </nav>
</header>
<nav id="menu">
<#include "search.ftl"/>
<#include "menu.ftl"/>
</nav>