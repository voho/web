<#macro print_wiki_tree root>

<h2><a href="/wiki/${root.id}/">${root.title}</a></h2>

<div class="row level1">
    <#list root.children.items as pageRef>
        <div class="6u 12u$(small)">
            <h3><a href="/wiki/${pageRef.id}/">${pageRef.title}</a></h3>
        </div>
        <div class="6u$ 12u$(small)">
            <#list pageRef.children.items as subPageRef>
                <p><a href="/wiki/${subPageRef.id}/">${subPageRef.title}</a></p>
            </#list>
        </div>
    </#list>
</div>

</#macro>