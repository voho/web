<#if breadCrumbs??>
<section id="breadcrumbs">
    <div class="inner">
        <header>
            <#list breadCrumbs.items as pageRef>
                <a href="/wiki/${pageRef.id}/">${pageRef.title}</a> <span>/</span>
            </#list>
        </header>
    </div>
</section>
</#if>