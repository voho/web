<#include "../macro/children_pages.ftl"/>
<#include "../macro/sibling_pages.ftl"/>

<#if subPages??>
    <@wiki_children_pages subPages.items />
<#elseif siblingPages??>
    <@wiki_sibling_pages siblingPages.items />
</#if>