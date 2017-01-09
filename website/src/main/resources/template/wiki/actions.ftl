<section id="contact">
    <div class="inner">
        <section>
        <#include "../common/search.ftl"/>
        </section>
    <#if active_wiki_page_content?has_content>
        <section class="split">
            <ul class="actions">
                <li>
                    <a href="#top" class="button icon fa-arrow-up scrolly">Zpět nahoru</a>
                </li>
            </ul>
            <ul class="actions">
                <li>
                    <a href="${active_wiki_page_report_issue}" onclick="return !window.open(this.href);" class="button icon fa-bug">Nahlásit chybu</a>
                </li>
                <li>
                    <a href="${active_wiki_page_github_url}" onclick="return !window.open(this.href);" class="button icon fa-github">Zdroj</a>
                </li>
            </ul>
        </section>
    </#if>
    </div>
</section>