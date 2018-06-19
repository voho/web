<#import "../macro/wiki_link.ftl" as link/>

<#if results?has_content>
    <h2>Výsledky vyhledávání</h2>

    <#list results as result>
        <div class="search-result">
            <div class="search-result-title">
                <h3><@link.wiki_link result.pageId result.pageTitle /></h3>
            </div>
            <#list result.fragments as fragment>
                <div class="search-result-fragment">${fragment}</div>
            </#list>
        </div>
    </#list>
<#else>
    <#if query?has_content>
        <p>Žádné výsledky. Zkuste upřesnit dotaz nebo použít Google.</p>

        <form action="http://google.cz/search" method="get">
            <input name="q" type="text" maxlength="30" size="30" value="${query!""}"/>
            <input name="hl" type="hidden" value="cs"/>
            <input name="sitesearch" type="hidden" value="voho.eu"/>
            <input class="button" type="submit" value="Hledat (Google)"/>
        </form>
    </#if>
</#if>

<#if error?has_content>
    <p>Při vyhledávání došlo k chybě: ${error}</p>
</#if>