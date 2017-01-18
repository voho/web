## Bencode

Bencode (čte se jako B-encode) je způsob [kódování](wiki/kodovani) dat, které umožňuje kódovat **řetězce**, **celá čísla**, **seznamy** a **slovníky**. Tato metoda je známá především proto, že je použita v protokolu [BitTorrent](http://www.bittorrent.org/beps/bep_0003.html). Je to velmi jednoduchý a platformově nezávislý formát, podobně jako [XML](wiki/xml) nebo [JSON](wiki/json). Není však bohužel příliš dobře čitelný člověkem, pokud se do něj nepřidají například mezery.

### Řetězec

Řetezec je kódován takto:

- přirozené číslo značící délku řetězce v bajtech jako [ASCII](wiki/ascii) (dekadicky)
- *:* (dvojtečka)
- řetězec
  - kódování není standardizováno, ale většina klientů podporuje UTF-8 a [ASCII](wiki/ascii)

| Hodnota | Zakódovaná hodnota |
|---|---|
| hello | `5:hello`
| Hello world! | `12:Hello world!`
| (prázdný řetězec) | `0:`

### Celé číslo

Celé číslo je kódováno takto:

- *i*
- *-* (volitelně pro záporná čísla)
- vlastní číslo jako [ASCII](wiki/ascii) (dekadicky)
  - čísla různá od nuly nesmí začínat nulou
  - záporná nula je zakázaná
- *e*

| Hodnota | Zakódovaná hodnota |
|---|---|
| 0 | `i0e`
| 42 | `i42e`
| -487 | `i-487e`

### Seznamy

Seznam může obsahovat libovolné hodnoty (řetězce, čísla, jiné seznamy a slovníky) a je kódován takto:

- *l*
- obsah seznamu (libovolné zakódované hodnoty)
- *e*

| Hodnota | Zakódovaná hodnota |
|---|---|
| [0, 1, 1, 2, 3] | `li0ei1ei1ei2ei3ee`
| [hello] | `l5:helloe`
| [0, hello, -4] | `li0e5:helloi-4ee`

### Slovníky

Slovník je posloupnost párů **klíč - hodnota**, kde klíč je řetězec. Hodnota může být libovolného typu.

- *l*
- obsah slovníku
  - za klíčem okamžitě následuje jeho hodnota
  - klíč musí být řetězec
- *e*

| Hodnota | Zakódovaná hodnota |
|---|---|
| a &rarr; 10, b &rarr; [0, 1] | `d1:ai10e1:bli0ei1eee`

### Vlastnosti

- Kódování Bencode je [bijektivní zobrazení](wiki/zobrazeni), takže lze například porovnávat zakódované hodnoty 

### Reference

- http://www.bittorrent.org/beps/bep_0003.html
- https://wiki.theory.org/BitTorrentSpecification#Bencoding
- https://en.wikipedia.org/wiki/Bencode