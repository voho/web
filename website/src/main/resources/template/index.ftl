<!DOCTYPE HTML>
<html lang="en">
<#assign title = "${website_full_name} - ${website_full_description}"/>
<#include "html/html-head.ftl"/>
<body>

<div id="universe">

    <section id="banner">
        <div class="inner">
            <header>
                <h1><span id="typed">Hi! My name is Vojta.</span></h1>
            </header>
            <div class="row">
                <p><#include "index/about.ftl"/></p>
            </div>
        </div>
    </section>

    <div id="main">
        <section id="menu">
            <div class="inner">
                <div class="row">
                    <div class="3u 6u(medium) 12u$(small)">
                        <header>
                            <h2>Wiki</h2>
                        </header>
                        <ul class="vertical actions">
                        <#include "common/menu/actions-wiki.ftl"/>
                        </ul>
                    </div>
                    <div class="3u 6u(medium) 12u$(small)">
                        <header>
                            <h2>Music</h2>
                        </header>
                        <ul class="vertical actions">
                        <#include "common/menu/actions-music.ftl"/>
                        </ul>
                    </div>
                    <div class="3u 6u(medium) 12u$(small)">
                        <header>
                            <h2>Work</h2>
                        </header>
                        <ul class="vertical actions">
                        <#include "common/menu/actions-projects.ftl"/>
                        </ul>
                    </div>
                    <div class="3u 6u(medium) 12u$(small)">
                        <header>
                            <h2>Photos</h2>
                        </header>
                        <ul class="vertical actions">
                        <#include "common/menu/actions-photos.ftl"/>
                        </ul>
                    </div>
                </div>
            </div>
        </section>
    </div>

<#include "index/recent-tracks.ftl"/>
<#include "index/recent-photos.ftl"/>

<#include "common/footer.ftl"/>

</div>

<#include "html/html-scripts.ftl"/>

</body>
</html>
