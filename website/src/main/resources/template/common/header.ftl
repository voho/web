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
    <a href="/" class="logo"><strong>${website_short_name}</strong> <span> = ${website_full_name}</span></a>
    <nav><a href="#menu">Menu</a></nav>
</header>
<#include "menu.ftl"/>