<#if instagram_photos?has_content>
<section>
    <div class="inner">
        <header class="major">
            <h2>Recent Photos</h2>
        </header>
        <div class="row uniform">
            <#list recent_photos as recent_photo>
                <div class="2u 4u(medium) 6u(small)">
                    <span class="image fit">
                        <a href="${recent_photo.link}"
                                title="${recent_photo.tags?join(', ')}"
                                onclick="return !window.open(this.href)"><img src="${recent_photo.images.low_resolution.url}" alt="${recent_photo.tags?join(', ')}"/></a>
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