(function ($) {
    "use strict";

    skel.breakpoints({
        xlarge: "(max-width: 1680px)",
        large: "(max-width: 1280px)",
        medium: "(max-width: 980px)",
        small: "(max-width: 736px)",
        xsmall: "(max-width: 480px)"
    });

    skel.layout({
        reset: false,
        grid: true,
        containers: true
    });

    $('.hljs').each(function (i, block) {
        hljs.highlightBlock(block);
    });

    var $typed = $("#typed");

    if ($typed.length) {
        $typed.typed({
            strings: ["Ing. Vojta Hordějčuk", "Hi! My name is Vojta."],
            loop: true,
            typeSpeed: 100
        });
    }
})(jQuery);

function displayTocBar() {
    $("#toc").fadeToggle(300);
}