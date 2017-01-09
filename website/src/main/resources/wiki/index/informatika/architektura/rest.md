## REST

REST (representional state transfer) je soubor několika poměrně přísných, ale jasných pravidel pro návrh architektury distribuovaného systému, a to především rozhraní mezi jednotlivými komponentami. Pokud jsou tato pravidla splněna, bude mít systém jako celek určité dobré vlastnosti. Motivací ke studiu těchto omezení a z nich plynoucích vlastností je fakt, že na této architektuře je postaven jeden z nejúspěšnějších systémů na světě, což je WWW (world-wide-web). 

REST představil Roy Fielding ve své dizertační práci v roce 2000. Pokud to vzbudí nějaký zájem, pan Fielding je mimochodem i spoluautorem protokolu HTTP. Zde budeme brát REST jako návod k vytvoření systému postaveného na komunikačním paradigmatu **klient-server** a konkrétně se zaměřím na HTTP webové služby, se kterými se často v praxi setkáváme asi nejčastěji.

### Pojmy

#### RESTful

RESTful je trochu podivné přídavné jméno, které o něčem říká, že je to navrženo podle principů REST.

#### Zdroj

Základním kamenem je tzv. **zdroj** (resource). Zdroj může být prakticky cokoliv, koncept nemá žádná omezení. Může to být například nějaký konkrétní objekt v databázi, dokument, výsledek nějakého výpočtu nebo webová stránka. Jediné pravidlo je, že každý zdroj musí mít *URL*, tedy své jednoznačné umístění. URL může mít například takovouto strukturu:

- *protokol://server:port/cesta?parametry#fragment*

Příklady URL:

```plain
http://localhost:8080/cms/users/JosefNovak
http://10.0.0.3:8080/cms/article/2001-09-11/comments
http://10.0.0.3:8080/cms/article/2001-09-11/rating
http://localhost:9976/calculator/factorial/42
http://localhost:9976/family/relationship/John-Mary
```

#### Reprezentace

Klient nikdy nevidí zdroj napřímo. Vždy vidí jen jeden z jeho obrazů, který se nazývá **reprezentace**. Reprezentace je strojově čitelná reprezentace aktuálního stavu zdroje. Jeden zdroj může mít mnoho reprezentací. Může to být například jednoduchý text s jeho popisem, obrázek, HTML stránka, SQL dotaz nutný k jeho vytvoření, a tak dále. Výběr reprezentace může ovlivnit klient nějakou řídící informací nebo server na základě nějaké klasifikace klienta, případně kombinací obojího.

#### Idempotence

Idempotence je vlastnost, která říká, že vícenásobné odeslání stejného požadavku má za následek jednu a tu samou odpověď. To se hodí v nestabilním prostředí, jako je například internet. Například požadavek na smazání může kdesi "zabloudit" a nakonec přijít opakovaně. Pokud je operace DELETE implementována korektně z pohledu REST, vůbec to nevadí - mazání již smazaného zdroje nemá žádný zvnějšku pozorovatelný efekt. V praxi samozřejmě není téměř nikdy dokonale splněna, protože se mohou zdroje v čase měnit (prvky kolekce mohou přibývat, atd.). Nějaká časově omezená idempotence by ale měla být zajištěna u metod GET, HEAD, PUT a DELETE. Pro tyto operace by neměly vznikat z vnějšího světa pozorovatelné změny ve zdroji.

#### Souvislost (HATEOAS)

Souvislost je názornější a tak i možná vhodnější pojmenování pro princip, který je známý pod akronymem *HATEOAS* (hypermedia as the engine of application state). Ten říká, že další stavy souvislé aplikace lze získat na základě odkazů (hyperlink) přijatých v reprezentaci stavu aktuálního. Reálný příklad je třeba web - na jednotlivých stránkách jsou formuláře a odkazy, pomocí kterých se lze dostat na další weby s dalšími odkazy a formuláři. Teoreticky by tedy mělo být možné všechny dostupné funkce systému použít bez ručního zadání nebo vygenerování jedné jediné URL adresy.

Někteří ortodoxní autoři RESTful aplikací dokonce tvrdí, že není nutné dokumentovat veřejná API. Obhajují to tím, že si jej může klient sám "osahat" a projít pomocí odkazů z nějaké hlavní kořenové stránky (URL: */*). Server tedy klientovi vždy poskytne odkazy, se kterými on pak může pracovat. V praxi to znamená, že například dostanu seznam nějakých objektů a u  každého objektu bude odkaz na jeho detail, editaci a smazání. Pokud potom například zobrazím detail jednoho z nich, v odpovědi opět naleznu odkaz pro návrat zpět na seznam.

### Rysy RESTful systémů

- komunikace přes REST je bezestavová - každý požadavek obsahuje veškeré informace nutné k jeho vykonání (jednotlivé zdroje samozřejmě stav mít mohou)
- sémantika operací je pevně daná a jejich počet je malý - v podstatě existují jen čtyři základní operace *CRUD* (create = POST, retrieve = GET, update = PUT, delete = DELETE), které však mohou různé zdroje implementovat zcela odlišně
- respektují princip *HATEOAS* (hypermedia as the engine of application state) - další stavy lze získat pomocí odkazů, které byly přijaty ze serveru

### RESTful HTTP webové služby

Dnes asi nejčastějším využitím architektury REST jsou RESTful webové služby. Komunikace klientů a serverů je zajištěna protokolem HTTP, který nabízí několik standardních operací: OPTIONS, GET, HEAD, PUT, POST, DELETE a další (určené pouze pro proxy, tedy pro nás nezajímavé): TRACE, CONNECT. 

#### Sémantika operací

##### GET

> Vrať reprezentaci stavu tohoto zdroje

Základní operací je GET, která provádí čtení nějakého zdroje. Tato operace by neměla mít žádné postranní efekty viditelné mimo aplikaci a musí být idempotentní (nezáleží na tom, kolikrát se požadavek odešle, odpověď bude vždy stejná).

Kódy odpovědí: 

- 200 v případě úspěchu

##### HEAD

> Vrať pouze hlavičky obsažené v reprezentaci tohoto zdroje

Podobné jako GET, ale nevrací žádnou entitu. Proto jsou odpovědi velmi krátké. Tato metoda je také idempotentní.

##### POST

> Vytvoř nový zdroj na základě zaslaného stavu

Operace POST se používá pro vytvoření nového zdroje. Není idempontentní. V odpovědi (typicky v hlavičce *Location*) posílá URL nově vytvořeného zdroje.

Kódy odpovědí: 

- 201 v případě úspěchu

##### PUT

> Nahradí zdroj novým zdrojem vytvořeným na základě zaslaného stavu

Operace PUT se používá v případě, kdy chceme modifikovat existující zdroj. Operace musí být idemponentní.

Kódy odpovědí: 

- 200 v případě úspěchu, pokud je vracena entita
- 204 v případě úspěchu, pokud není vracena entita

##### DELETE

> Smaž tento zdroj

Delete je podobné jako PUT, ale má jinou sémantiku - maže zadaný zdroj. Operace musí být idempotentní.

Kódy odpovědí: 

- 200/204 v případě úspěchu
- 404/410 v případě, že byl zdroj již smazán

##### OPTIONS

> Vrať metody, které mohu pro tento zdroj použít

Pokud klient neví, jaké příkazy může poslat, může se zdroje dotázat touto operací. Ta mu vrátí seznam povolených operací (např. GET, PUT, DELETE).

Kódy odpovědí: 

- 200 v případě úspěchu

### Reference

- http://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm

