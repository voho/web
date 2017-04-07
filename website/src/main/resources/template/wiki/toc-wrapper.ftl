<#if active_wiki_page_toc?has_content && !active_wiki_page_cover>

<div id="toc">
    <div class="inner">

        <#assign toc = active_wiki_page_toc.children/>
        <#include "toc.ftl"/>

    </div>
</div>

</#if>