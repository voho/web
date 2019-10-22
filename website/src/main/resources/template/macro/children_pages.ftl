<#macro wiki_children_pages childrenPages>

<section class="wiki-related-pages children">
    <div class="row">
        <#list childrenPages as pageRef>
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
        </#list>
    </div>
</section>

</#macro>