<!DOCTYPE HTML>
<html lang="en">
<#assign title = "${website_full_name} - ${website_full_description}"/>
<#include "html/html-head.ftl"/>
<body>

<#include "common/header.ftl"/>
<#import "macro/icons.ftl" as icons/>

<div id="universe">

    <section id="banner">
        <div class="inner">
            <header>
                <h1><span id="typed">Hi! My name is Vojta.</span></h1>
            </header>
            <div class="row">
                <div class="8u">
                    <p><#include "index/about.ftl"/></p>
                </div>
                <div class="4u">
                    <ul class="vertical actions">
                        <li><a href="#menu" class="big button"><@icons.add "user-circle"/> See what I do</a></li>
                        <li><a href="${social_profile_linkedin}" class="button"><@icons.add "envelope"/> Contact me</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </section>

    <div id="main">
        <section id="menu">
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