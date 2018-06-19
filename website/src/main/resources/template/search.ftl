<!DOCTYPE HTML>
<html lang="cs">
<#assign title = "Vyhledávání - ${website_author}">
<#include "html/html-head.ftl"/>
<body>

<#include "common/header.ftl"/>

<div id="universe">

<#include "wiki/toc-wrapper.ftl"/>

    <section id="wiki-content" class="wiki">
        <div class="inner">

        <#include "search/form.ftl"/>
        <#include "search/results.ftl"/>

        </div>
    </section>

</div>

<#include "common/footer.ftl"/>

<#include "html/html-scripts.ftl"/>

</body>
</html>