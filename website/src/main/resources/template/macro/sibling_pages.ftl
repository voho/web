<#macro wiki_sibling_pages siblingPages>

<section class="wiki-related-pages siblings">
    <ul>
        <#list siblingPages as pageRef>
            <li><a href="/wiki/${pageRef.id}/">${pageRef.title}</a></li>
        </#list>
    </ul>
</section>

</#macro>