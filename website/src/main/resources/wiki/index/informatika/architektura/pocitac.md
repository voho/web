## Počítač

Počítač slouží jako fyzická platforma pro vykonávání [programů](wiki/algoritmus) a je jednou z možných reálných implementací [Turingova stroje](wiki/turinguv-stroj). V zájmu zachování obecnosti je zde počítačem myšlen jakýkoliv stroj, jehož primárním účelem je výpočet výsledku na základě vstupních dat.

### Vnitřní struktura

| Úroveň | Model
|---|---
| [Diskrétní součástky](wiki/elektronika) | [fyzikální rovnice](wiki/fyzika) a modely
| [Logická hradla](wiki/logicke-hradlo) | pravdivostní tabulky
| [Kombinační obvody](wiki/kombinacni-obvod) | Booleova algebra
| [Sekvenční obvody](wiki/sekvencni-obvod) | [konečné automaty](wiki/automat) [Moore](wiki/moore), [Mealy](wiki/mealy)
| Procesor | ISA, jazyky symbolických instrukcí
| Firmware a ovladače | systémové [programovací jazyky](wiki/jazyk)
| Operační systém | systémové [programovací jazyky](wiki/jazyk)
| Aplikační software | aplikační [programovací jazyky](wiki/jazyk)

### Modely

#### Von Neumannovská architektura

Von Neumannovská (někdy také Stanfordská) architektura je pojmenovaná podle slavného američana maďarského původu **Johna von Neumanna**. V této architektuře jsou instrukce i data uloženy v téže paměti a nejsou nijak explicitně rozlišeny. Paměť je organizována lineárně na buňky stejné velikosti, které jsou adresovatelné celými čísly. Data jsou reprezentována binárně a jejich datové typy se implicitně nerozlišují. Instrukce se provádí jednotlivě a to postupně tak, jak jsou zapsány v paměťi, dokud není toto pořadí ovlivněno speciální instrukcí (např. skoky). Von Neumannovská architektura najde využití všude tam, kde je požadována univerzálnost a jednoduchost.

![von Neumannovská architektura](https://dl.dropboxusercontent.com/u/5942837/voho.cz/image-wiki/arch_neumann.png)

#### Harvardská architektura

Hlavním rysem harvardské architektury je fyzické oddělení dat a instrukcí. Proto Harvardská architektura umožňuje, aby data i instrukce měly různou reprezentaci a implementaci. Paměť obsahující instrukce se často realizuje jako **ROM**, tedy paměť určená pouze pro čtení. Výhodou této architektury je také možnost přistupovat do obou paměti současně. Harvardská architektura se používá hlavně tam, kde se program téměř nikdy nemění (např. malá věstavěná zařízení).

![Harvardská architektura](https://dl.dropboxusercontent.com/u/5942837/voho.cz/image-wiki/arch_harvard.png)

### Reference

- předmět X36SKD na FEL ČVUT
- předmět X36JPO na FEL ČVUT
- http://www.ee.nmt.edu/~rison/ee308_spr99/lectures.html

