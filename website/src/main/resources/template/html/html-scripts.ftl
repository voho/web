<script src="/assets/js/jquery.min.js"></script>
<script src="/assets/js/skel.min.js"></script>
<script src="/assets/js/util.js"></script>

<!--[if lte IE 8]>
<script src="/assets/js/ie/respond.min.js"></script>
<![endif]-->

<!-- syntax highlighter -->
<script src="/assets/highlight/highlight.pack.js"></script>

<!-- stupid effects for the intro page -->
<script src="/assets/js/particles.min.js"></script>
<script src="/assets/js/typed.min.js"></script>

<!-- autocomplete -->
<script src="/assets/autocomplete/jquery.auto-complete.min.js"></script>

<!-- everything put together -->
<script src="/assets/js/main.js"></script>

<!-- Google Analytics -->
<script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-1921641-7', 'auto');
    ga('send', 'pageview');

</script>

<script>
    $("#wiki-search").autoComplete({
        minChars: 1,
        source: function (term, suggest) {
            var choices = ${wikiPagesAutoCompleteJson};
            var matches = [];
            term = term.toLowerCase();
            for (i = 0; i < choices.length; i++)
                if (~choices[i].toLowerCase().indexOf(term)) matches.push(choices[i]);
            suggest(matches);
        },
        onSelect: function (e, term, item) {
            $("#wiki-search").text(term);
        }
    });
</script>