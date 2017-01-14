<!DOCTYPE HTML>
<html lang="en">
<#assign title = "${website_full_name} - ${website_full_description}"/>
<#include "html/html-head.ftl"/>
<body>

<div id="wrapper">

<#assign alt = true>
<#include "common/header.ftl"/>

    <section id="banner" class="major">
        <div class="inner">
            <header class="major">
                <h1>Hi! My name is Vojta Hordějčuk.</h1>
            </header>
            <div class="content">
                <p>
                <#include "index/about.ftl"/>
                </p>
                <ul class="vertical actions">
                    <li><a href="#what_i_do" class="button next scrolly">See what I do</a></li>
                </ul>
            </div>
        </div>
    </section>

    <div id="main">
        <section id="what_i_do">
            <div class="inner">
                <div class="row">
                    <div class="3u 12u$(medium)">
                        <header class="major">
                            <h2>Wiki</h2>
                        </header>
                        <ul class="vertical actions">
                        <#include "index/actions-wiki.ftl"/>
                        </ul>
                    </div>
                    <div class="3u 12u$(medium)">
                        <header class="major">
                            <h2>Music</h2>
                        </header>
                        <ul class="vertical actions">
                        <#include "index/actions-music.ftl"/>
                        </ul>
                    </div>
                    <div class="3u 12u$(medium)">
                        <header class="major">
                            <h2>Work</h2>
                        </header>
                        <ul class="vertical actions">
                        <#include "index/actions-projects.ftl"/>
                        </ul>
                    </div>
                    <div class="3u 12u$(medium)">
                        <header class="major">
                            <h2>Photos</h2>
                        </header>
                        <ul class="vertical actions">
                        <#include "index/actions-photos.ftl"/>
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