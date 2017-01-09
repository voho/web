<!DOCTYPE HTML>
<!--
	Forty by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html lang="cs">
<#assign title = "${website_author} - ${website_full_description}">
<#include "html/html-head.ftl"/>
<body>

<div id="wrapper">

<#include "common/header.ftl"/>

    <section class="wiki" id="wiki-content">
        <div class="inner">
            <header class="major">
                <h1>Meta</h1>
            </header>

        <#if todos?has_content>
            <section>
                <header class="major">
                    <h2>TODO</h2>
                </header>
                <ul>
                    <#list todos as todo>
                        <li><a href="/wiki/${todo.wikiPageId}/">${todo.wikiPageId}</a></li>
                    </#list>
                </ul>
            </section>
        </#if>

        <#if missing?has_content>
            <section>
                <header class="major">
                    <h2>Neplatné odkazy</h2>
                </header>
                <ul>
                    <#list missing as item>
                        <li>
                            <a href="/wiki/${item.sourceWikiPageId}/">${item.sourceWikiPageId}</a> &rarr; ${item.missingWikiPageId}
                        </li>
                    </#list>
                </ul>
            </section>
        </#if>

        <#if quotes?has_content>
            <section>
                <header class="major">
                    <h2>Citáty</h2>
                </header>
                <#list quotes as quote>
                    <blockquote>
                        <p>${quote.text}<br/><em>${quote.author}</em></p>
                    </blockquote>
                </#list>
            </section>
        </#if>

        </div>
    </section>

<#assign footer_icons = true/>
<#include "common/footer.ftl"/>

</div>

<#include "html/html-scripts.ftl"/>

</body>
</html>