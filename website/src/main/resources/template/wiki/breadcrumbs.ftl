<#if breadCrumbs??>
    <span id="breadcrumbs">
    <#list breadCrumbs.items as pageRef>
        / <a href="/wiki/${pageRef.id}/">${pageRef.title}</a>
    </#list>
    </span>
</#if>