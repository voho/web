<#if recent_tracks?has_content>
<section>
    <div class="inner">
        <header class="major">
            <h2>Recent Music</h2>
        </header>
        <div class="row uniform">
            <#list recent_tracks as track>
                <div class="6u 12u$(medium)">
                    <iframe src="${track.widgetUrl}" style="border: 0; width: 100%; height: 80px;" allowfullscreen allow="encrypted-media"></iframe>
                </div>
            </#list>
        </div>
        <div class="row uniform">
            <ul class="actions">
                <#include "../common/menu/actions-music.ftl"/>
            </ul>
        </div>
    </div>
</section>
</#if>