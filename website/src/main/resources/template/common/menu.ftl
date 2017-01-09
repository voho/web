<nav id="menu">
<#include "search.ftl"/>
<#if indexSubPages??>
    <div class="row">
        <#list indexSubPages.items as pageRef>
            <div class="3u 12u$(medium)">
                <h4><a href="/wiki/${pageRef.id}/">${pageRef.title}</a></h4>
                <#if pageRef.children??>
                    <ul>
                        <#list pageRef.children.items as childPageRef>
                            <li><a href="/wiki/${childPageRef.id}/">${childPageRef.title}</a></li>
                        </#list>
                    </ul>
                </#if>
            </div>
        </#list>
    </div>
</#if>
</nav>