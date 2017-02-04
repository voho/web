<#macro print_wiki_tree root level max_level>
    <#if level <= max_level>

        <#if root.children?has_content && root.children.items?has_content && level < max_level>

        <div class="row level${level}">
            <div class="4u">
                <a href="/wiki/${root.id}/">${root.title}</a>
            </div>

            <div class="8u$">
                <#list root.children.items as pageRef>
                    <@print_wiki_tree pageRef level+1 max_level/>
                </#list>
            </div>
        </div>

        <#else>

        <div class="level${level}">
            <p><a href="/wiki/${root.id}/">${root.title}</a></p>
        </div>

        </#if>

    </#if>
</#macro>