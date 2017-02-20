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
                <h1><span id="typed">Hi! My name is Vojta.</span></h1>
            </header>
            <div class="content">
                <p>
                <#include "index/about.ftl"/>
                </p>
                <ul class="vertical actions">
                    <li><a href="#what_i_do" class="big button next">See what I do</a></li>
                    <li><a href="${social_profile_linkedin}" class="button">Contact me</a></li>
                </ul>
            </div>
        </div>
    </section>

    <div id="main">
        <section id="what_i_do">
            <div class="inner">
                <#include "common/menu.ftl"/>
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