<#if instagram_photos?has_content>
<section>
    <div class="inner">
        <header class="major">
            <h2>Recent Photos</h2>
        </header>
        <div class="row uniform">
            <#list instagram_photos as instagram_photo>
                <div class="2u 6u$(medium)">
                    <span class="image fit">
                        <a href="${instagram_photo.link}"
                                title="${instagram_photo.tags?join(', ')}"
                                onclick="return !window.open(this.href)"><img src="${instagram_photo.images.low_resolution.url}" alt=""/></a>
                    </span>
                </div>
            </#list>
        </div>
        <div class="row uniform">
            <ul class="actions">
                <#include "actions-photos.ftl"/>
            </ul>
        </div>
    </div>
</section>
</#if>