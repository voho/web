## Handlebars

Jednoduchý šablonový systém pro Javascript, který se velmi dobře doplňuje s jQuery.

- https://cdnjs.com/libraries/handlebars.js
- http://www.jsdelivr.com/projects/handlebarsjs

### Šablona

```html
<script id="tpl-person-table" type="text/x-handlebars-template">
    <table>
        <thead>
            <tr><th>Name</th><th>Age</th></tr>
        </thead>
        <tbody>
            {{#each people}}
                <tr><td>{{name}}</td><td>{{age}}</td></tr>
            {{/each}}
        </tbody>
    </table>
</script>
<div id="person-table-target"></div>
```

### Zpracování šablony (jQuery)

```javascript
var source = $("#tpl-person-table").html();
var template = Handlebars.compile(source);
```

### Vykreslení šablony (jQuery)

```javascript
var target = $("#person-table-target");

var context = {
    "people": [
        {"age": 14, "name": "John"},
        {"age": 38, "name": "Carlos"},
        {"age": 61, "name": "Rita"}
    ]
};

target.html(template(context));
```

### Pomocné funkce (helpery)

Příklad pomocné funkce:

```javascript
Handlebars.registerHelper('link', function(text, url) {
    text = Handlebars.Utils.escapeExpression(text);
    url = Handlebars.Utils.escapeExpression(url);
    var result = '<a href="' + url + '">' + text + '</a>';
    return new Handlebars.SafeString(result);
});
```

```html
<p>{{link "Google" "http://google.com"}}</p>
```

Příklad pomocné funkce pro podmínku:

```javascript
Handlebars.registerHelper('if_eq', function (a, b, opts) {
    if (a == b)
        return opts.fn(this);
    else
        return opts.inverse(this);
});
```

```html
<p>{{#if_eq name "John"}}I am John.{{else}}I am somebody else.{{/if_eq}}</p>
```

### Pomocné funkce (utility)

Pomocné funkce pro kompilaci a vykreslení šablon:

```javascript
function compileHandlebarsTemplate(elementCssQuery) {
    return Handlebars.compile($(elementCssQuery).html());
}

function renderHandlebarsTemplate(elementCssQuery, template, context) {
    $(elementCssQuery).html(template(context));
}
```

Ukázka použití:

```html
<script id="tpl-banner" type="text/x-handlebars-template">
    <p>{{this}}</p>
</script>
<div id="target-banner"></div>
```

```javascript
var template = compileHandlebarsTemplate("#tpl-banner");
var context = "Hello world!";
renderHandlebarsTemplate("#target-banner", template, context);
```
