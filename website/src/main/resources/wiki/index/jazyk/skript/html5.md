## HTML 5

### Základní šablona

```html
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Basic HTML 5 Template</title>
    <meta name="description" content="This is a basic HTML 5 template.">
    <meta name="author" content="The Internet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="assets/css/style.css">
    <!--[if lt IE 9]>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.js"></script>
    <![endif]-->
</head>
<body>
    <!-- put content here -->
    
    <!-- scripts on the bottom -->
    <script src="assets/js/script.js"></script>
</body>
</html>
```

### Layout Frameworky (pro responzivní web)

Responsivní web je ve zkratce takový web, který se přizpůsobuje koncovému zákazníkovi. 
V praxi to nejčastěji znamená, že existuje několik různých verzí webové stránky pro různá zařízení, případně se obsah dynamicky mění podle velikosti okna.
Na mobilním zařízení mohou být například některé prvky stránky skryty, nebo jsou zvětšeny prvky, které by byly příliš malé.

Frameworky nejčastěji nabízí tzv. **mřížkový systém**, ve kterém lze relativně definovat velikosti prvků a měnit jejich vlastnosti podle vlastností koncového zařízení.

- [Boilerplate](https://html5boilerplate.com/)
- [Bootstrap](http://getbootstrap.com/)
- [Skel](http://skel.io/)

### Populární Javascript Frameworky

Protože je Javascript široce rozšířen a podporovaný, vyrostl kolem něho obrovský ekosystém.
V Javascriptu se běžně píší uživatelská rozhraní a různé složité aplikace pro web.
Výhodou tohoto jazyka je, že běh programů probíhá mimo server v internetovém prohlížeči uživatele, a tak umožňuje vytvářet velmi dynamická rozhraní, která nepotřebují neustále komunikovat se serverem.

- [React](https://facebook.github.io/react/)
- [AngularJS](https://angularjs.org/)
- [jQuery](https://jquery.com/)
- [Ember.js](https://emberjs.com/)
- [Handlebars](http://handlebarsjs.com/)

### CSS Frameworky

CSS frameworky se snaží zjednodušit práci se stylování webových aplikací. 
Většinou se jedná o nějaké syntaktické nadstavby nad CSS a jeho různé nadmnožiny, které zvyšují znovupoužitelnost různých částí kódu.

- [LESS](http://lesscss.org/)
- [Bulma](http://bulma.io/)
