<#if subPages??>
<section class="wiki-related-pages children">
    <div class="row">
        <#list subPages.items as pageRef>
            <div class="4u 12u$(medium)">
                <h4><a href="/wiki/${pageRef.id}/" class="button">${pageRef.title}</a></h4>
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
</section>
<#elseif siblingPages??>
<section class="wiki-related-pages siblings">
    <ul>
        <#list siblingPages.items as pageRef>
            <li><a href="/wiki/${pageRef.id}/">${pageRef.title}</a></li>
        </#list>
    </ul>
</section>
</#if>