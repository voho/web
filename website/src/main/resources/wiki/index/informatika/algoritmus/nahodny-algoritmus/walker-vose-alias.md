## Walker-Vose-Alias

!TODO!

- Inicializace:
    - vytvoř pole *Alias* a *Prob* o velikosti *n*
    - vytvoř dva seznamy *Small* a *Large*
    - vynásob každou pravděpodobnost *n*
    - pro každou tuto vynásobenou pravděpodobnost *p*:
        - pokud je pravděpodobnost *p(i)* menší než 1, vlož prvek na indexu *i* do seznamu *Small*, jinak do seznamu *Large*
    - dokud nejsou oba seznamy prázdné:
        - vyber první prvek ze seznamu *Small* a označ jej jako *s*
        - vyber první prvek ze seznamu *Large* a označ jej jako *g*
        - nastav *Prob[s]=p(s)*
        - nastav *Alias[s]=g*
        - nastav *p(g)=p(g)+p(s)-1* (trik pro dosažení lepší numerické stability)
        - pokud *p(g)* je menší než 1, přidej *g* do seznamu *Small*, jinak do seznamu *Large*
    - dokud není seznam *Large* prázdný:
        - vyber první prvek ze seznamu *Large* a označ jej jako *g*
        - nastav *Prob[g]=1*
    - dokud není seznam *Small* prázdný:
        - vyber první prvek ze seznamu *Small* a označ jej jako *s*
        - nastav *Prob[s]=1*
- Generování náhodného čísla:
    - vyber náhodný řádek *i* v rabulce *Prob*
    - vygeneruj náhodné číslo *r* v rozsahu 0-1
    - pokud je číslo *r* menší než pravděpodobnost na řádku *Prob[i]*, vrať *i*, jinak *Alias[i]*

```include:java
random/WalkerVoseAliasSelection.java
```

### Reference

- http://www.keithschwarz.com/darts-dice-coins/
- https://www.jstatsoft.org/index.php/jss/article/view/v011i03/v11i03.pdf
- https://oroboro.com/non-uniform-random-numbers/
- https://github.com/asmith26/Vose-Alias-Method/blob/master/vose_sampler/vose_sampler.py