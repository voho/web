## Jazyk JSON

JSON (JavaScript Object Notation) je univerzální textový formát pro výměnu dat. Je založený na syntaxi jazyka Javascript. Jazyk JSON je velmi jednoduchý a snadno se čte. Datové struktury zapsané v jazyce JSON je možné převést téměř do všech programovacích jazyků. Jeho syntaxe je postavena na dvou základních strukturách:

- množina párů (klíč - hodnota)
- seznam hodnot

### Syntaxe

Syntaxe jazyka JSON je podmnožinou jazyka Javascript. Takto vypadá jeho popis v Backus-Naurově formě:

```bnf
<objekt> ::= {} | { <páry> }
<páry> ::= <pár> | <pár> , <páry>
<pár> ::= <řetězec> : <hodnota>
<pole> ::= [] | [ <prvky> ]
<prvky> ::= <hodnota> | <hodnota> , <prvky>
<hodnota> ::= <řetězec> | <číslo> | <objekt> | <pole> | true | false | null
```

### Příklady

```javascript
13
```

```javascript
[1, 2, 3, 5, 7, 13]
```

```javascript
{
  "název" : "Legrační video",
  "vložil" : "Jan Novotný",
  "datum" :
  {
    "den" : 10,
    "měsíc" : 3,
    "rok" : 2010
  },
  "komentáře" :
  [
    "Tak to je hodně dobrý!",
    "Fakt super video!",
    "Nude, nic moc..."
  ]
}
```

```javascript
{
  "prvočísla" : [1, 2, 3, 5, 7, 13],
  "číslo 4 je prvočíslo" : false,
  "číslo 17 je prvočíslo" : true,
  "oblíbené prvočíslo" : 7,
  "největší prvočíslo" : null
}
```

```javascript
{
  "jméno" : "Karel",
  "příjmení" : "Novák",
  "věk" : 30,
  "adresa" :
  {
    "ulice" : "Blanická 42",
    "město" : "Liptákov",
    "psč" : 12331,
    "země" : "Česká republika"
  },
  "zájmy" :
  [
    "zahrádka",
    "turistika",
    "fotografie",
    "chovatelství"
  ]
}
```

### Reference

- http://www.json.org/