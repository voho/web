<#if active_wiki_page_incoming_links??>
    <#if active_wiki_page_incoming_links.items?has_content>
    <section class="wiki-related-pages incoming">
        <div class="inner lower">
            <header>
                <h4>Odkazy vedoucí na tuto stránku</h4>
            </header>
            <ul>
                <#list active_wiki_page_incoming_links.items as ref>
                    <li class="icon fa-arrow-left"><a href="/wiki/${ref.id}/">${ref.title}</a></li>
                </#list>
            </ul>
        </div>
    </section>
    </#if>
</#if>

<#if active_wiki_page_outgoing_links??>
    <#if active_wiki_page_outgoing_links.items?has_content>
    <section class="wiki-related-pages outgoing">
        <div class="inner lower">
            <header>
                <h4>Odkazy vedoucí z této stránky</h4>
            </header>
            <ul>
                <#list active_wiki_page_outgoing_links.items as ref>
                    <li class="icon fa-arrow-right"><a href="/wiki/${ref.id}/">${ref.title}</a></li>
                </#list>
            </ul>
        </div>
    </section>
    </#if>
</#if>



