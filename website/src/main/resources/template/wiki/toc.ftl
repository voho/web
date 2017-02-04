<#if toc?has_content>
<ul>
    <#list toc as item>
        <li><a href="#${item.id}">${item.title}</a></li>
        <#assign toc = item.children/>
        <#include "toc.ftl"/>
    </#list>
</ul>
</#if>