<#if recent_tracks?has_content>
<section>
    <div class="inner">
        <header class="major">
            <h2>Recent Music</h2>
        </header>
        <div class="row uniform">
            <#list recent_tracks as track>
                <div class="6u 12u$(medium)">
                    <iframe src="${track.widgetUrl}" style="border: 0; width: 100%; height: 166px;" scrolling="no" frameborder="no"
                            allow="encrypted-media"></iframe>
                </div>
            </#list>
        </div>
    </div>
</section>
</#if>
