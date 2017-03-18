<#if active_wiki_page_content?has_content>
<section>
    <div class="inner lower">
        <ul class="actions">
            <li>
                <a href="#top" class="special button icon fa-arrow-up">Zpět nahoru</a>
            </li>
            <li>
                <a href="${active_wiki_page_report_issue}" class="button icon fa-bug">Nahlásit chybu</a>
            </li>
            <li>
                <a href="${active_wiki_page_github_url}" class="button icon fa-github">Zdrojový kód</a>
            </li>
            <li>
                <a href="${active_wiki_page_history_url}" class="button icon fa-history">Historie</a>
            </li>
        </ul>
    </div>
</section>
</#if>
