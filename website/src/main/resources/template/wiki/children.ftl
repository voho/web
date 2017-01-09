<#if subPages??>
<section class="wiki-related-pages children">
    <#list subPages.items as pageRef>
        <#if pageRef?index%3==0 || pageRef?is_first>
        <div class="row">
        </#if>
        <div class="4u 12u$(medium)">
            <h4><a href="/wiki/${pageRef.id}/">${pageRef.title}</a></h4>
            <#if pageRef.children??>
                <ul>
                    <#list pageRef.children.items as childPageRef>
                        <li><a href="/wiki/${childPageRef.id}/">${childPageRef.title}</a></li>
                    </#list>
                </ul>
            </#if>
        </div>
        <#if pageRef?index%3==2 || pageRef?is_last>
        </div>
        </#if>
    </#list>
</section>
<#elseif siblingPages??>
<section class="wiki-related-pages siblings">
    <ul>
        <#list siblingPages.items as pageRef>
            <li class="icon fa-book"><a href="/wiki/${pageRef.id}/">${pageRef.title}</a></li>
        </#list>
    </ul>
</section>
</#if>