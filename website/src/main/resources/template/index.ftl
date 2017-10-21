<!DOCTYPE HTML>
<html lang="en">
<#assign title = "${website_full_name} - ${website_full_description}"/>
<#include "html/html-head.ftl"/>
<body>

<#include "common/header.ftl"/>
<#import "macro/icons.ftl" as icons/>

<div id="universe">

    <#include "common/search.ftl"/>

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
                            <h2><span class="fa fa-book"></span> Wiki</h2>
                        </header>
                        <ul class="vertical actions">
                        <#include "common/menu/actions-wiki.ftl"/>
                        </ul>
                    </div>
                    <div class="3u 6u(medium) 12u$(small)">
                        <header>
                            <h2><span class="fa fa-music"></span> Music</h2>
                        </header>
                        <ul class="vertical actions">
                        <#include "common/menu/actions-music.ftl"/>
                        </ul>
                    </div>
                    <div class="3u 6u(medium) 12u$(small)">
                        <header>
                            <h2><span class="fa fa-building"></span> Work</h2>
                        </header>
                        <ul class="vertical actions">
                        <#include "common/menu/actions-projects.ftl"/>
                        </ul>
                    </div>
                    <div class="3u 6u(medium) 12u$(small)">
                        <header>
                            <h2><span class="fa fa-photo"></span> Photos</h2>
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
<#include "index/donate.ftl"/>

<#include "common/footer.ftl"/>

</div>

<#include "html/html-scripts.ftl"/>

</body>
</html>
