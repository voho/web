<#if active_wiki_page_content?has_content>
<section>
    <div class="inner lower">
        <ul class="actions">
            <li>
                <a href="${active_wiki_page_report_issue}" onclick="return !window.open(this.href);" class="button icon fa-bug">Nahlásit chybu</a>
            </li>
            <li>
                <a href="${active_wiki_page_github_url}" onclick="return !window.open(this.href);" class="button icon fa-github">Zdrojový kód</a>
            </li>
        </ul>
    </div>
</section>
</#if>
