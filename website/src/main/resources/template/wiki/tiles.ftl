<section class="tiles">
<#list indexSubPages.items as pageRef>
    <article>
        <header class="major">
            <h3><a href="/wiki/${pageRef.id}/" class="link">${pageRef.title}</a></h3>
        </header>
    </article>
</#list>
</section>