## Mikroslužby

> This is the Unix philosophy: Write programs that do one thing and do it well. Write programs to work together. Write programs to handle text streams, because that is a universal interface. *Doug McIlroy*

Architektura založená na mikroslužbách (microservice architecture) popisuje návrh složitého systému pomocí malých, jednoduchých a nezávisle běžících komponent, nazývaných **mikroslužby**. Tyto komponenty spolu mohou vzájemně komunikovat, nejčastěji pomocí rozhraní postavených nad HTTP. 

Službou je myšlena nějaká vnějšímu světu užitečná funkcionalita, která je technicky realizována mikroslužbou. Příkladem služeb může být správa objednávek, účtování, správa uživatelů, přihlašování, vykreslování grafů, atd. Služby a jejich dělení většinou vychází z obchodní činnosti zadavatele a z požadované granularity mikroslužeb.

Tento druh návrhu není nikterak nový či převratný - objevuje se již při návrhu operačního systému **Unix** (sada mnoha malých jednoúčelových nástrojů - každý dělá jednu věc, ale zato dobře) nebo **internetu** (webové servery, síťová infrastruktura, síťové protokoly). Oba zde zmíněné příklady byly historicky velice úspěšné a od doby svého vzniku slouží dodnes.

Je to podobné paradigma, jakým je například [objektově orientované programování](wiki/oop). Objekty zde však netvoří nějaké struktury v paměti, ale koncepčně obecnější **služby**, a klasické volání metody v rámci jednoho procesu (například pomocí zásobníku a skoku) nahrazuje volání mezi procesy.

![typická architektura monolitické aplikace](https://dl.dropboxusercontent.com/u/5942837/voho.cz/image-wiki/ms_monolith_arch.png)

![typická architektura aplikace složené z mikroslužeb](https://dl.dropboxusercontent.com/u/5942837/voho.cz/image-wiki/ms_uservice_arch.png)

### Základní rysy

Neexistuje žádná přesná a formální definice mikroslužby, ani univerzální návod, jak navrhovat jejich API. Každý projekt může vyžadovat jinou granularitu mikroslužeb a některé zvyklosti lze samozřejmě po zvážení porušit. Přesto je třeba mít při návrhu na paměti několik zásad.

#### Jednoduchost

Každá mikroslužba by měla být co nejjednodušší, aby se tzv. "vešla do hlavy". Jednoduchou aplikaci je jednodušší navrhnout, vyvíjet, spravovat, přepsat i zahodit.

#### Nezávislost

Jednotlivé mikroslužby by na sobě měly být maximálně nezávislé. To znamená nezávislost ideálně ve všech následujících bodech:

- nezávislý repositář se zdrojovým kódem
- nezávislé tempo vývoje
- nezávislé datové úložiště
- nezávislý runtime proces (ve kterém služba běží)
- možnost simulace všech závislostí (okolních mikroslužeb)
- nezávislé nasazení

#### Automatizace

Počet mikroslužeb může narůst rychle. Proto musí existovat automatizace všude, kde je to smysluplné a možné. Automatizovat lze tyto kroky:

- jednotkové testy
- integrační testy
- nasazení (ideálně "na jeden klik")
- monitoring

### Problémy monolitické aplikace

Mikroslužby se často dávají do kontrastu s velkými monolitickými aplikacemi. Takové aplikace mají několik nevýhod:

- při vydávání nové verze nebo po opravě chyby se musí celé znovu kompilovat, testovat a nasadit
- u starších monolitických projektů často chybí někdo, kdo by znal celou aplikaci do hloubky - každý programátor zná jen nějakou její část, kterou naposledy upravoval
- při škálování lze replikovat pouze celou aplikaci se všemi jejími funkcemi, takže zbytečně i s těmi, které se využívají málo (to vede k neefektivnímu využití serverů)
- každá chyba může teoreticky způsobit nefunkčnost celé aplikace
- velké množství komponent a tříd vede ke zpomalení vývojových nástrojů (IDE)

![škálování monolitických aplikací](https://dl.dropboxusercontent.com/u/5942837/voho.cz/image-wiki/ms_monolith_deploy.png)

![škálování mikroslužeb](https://dl.dropboxusercontent.com/u/5942837/voho.cz/image-wiki/ms_uservice_deploy.png)

Funkcionalita se k monolitickým aplikacím postupně "lepí", protože funkcionalita přitahuje další funkcionalitu.

### Přechod k mikroslužbám

Pokud se společnost rozhodne přejít k mikroslužbám, měla by změnit i svou organizaci a složení týmů. Říká nám to (velmi trefný) [Conwayův zákon](http://en.wikipedia.org/wiki/Conway's_law). Pokud tedy máme například tým vývojářů UI v prvním patře, tým vývojářů enterprise Javy v druhém a odborníky na Oracle ve třetím, bude mít aplikace tři moduly - UI, webovou aplikaci a Oracle databázi. Pro vývoj mikroslužeb bude daleko příhodnější tyto lidi namíchat do menších týmů a posadit je vedle sebe. Velmi vhodné jsou agilní metodiky (např. Scrum).

### Výhody mikroslužeb

#### Jednoduchost

Lidé v IT mají zpravidla rádi jednoduchost, protože se nemusí při každé úpravě nořit do složitých a často nepotřebných detailů. Také platí zlaté pravidlo techniků, že složitější mechanismy jsou daleko náchylnější k chybám než jednoduché (je tam toho zkrátka více, co se může pokazit).

Jednoduchá mikroslužba také nevyžaduje tak velký tým, který by se o ní staral. Každý tým tak může dostat na starost i několik mikroslužeb.

Je-li snadné mikroslužbu napsat, je také snadné ji přepsat, vyhodit či nahradit.

#### Nezávislost

Nezávislost jedné mikroslužby na druhé (ve smyslu vývoje a nasazení) má spoustu výhod. Největšími z nich jsou:

- **oddělení zodpovědnosti** (low coupling) - každá mikroslužba má jasný účel a je na tento účel abstrahovatelná
- **oddělení technologie** - každá mikroslužba může být vyvinuta v jiném programovacím jazyce nebo s využitím jiných frameworků a knihoven - je také možné celou mikroslužbu přepsat bez dopadu na ostatní
- **oddělení procesu vývoje** - nezávislost komponent obecně umožňuje paralelizmus, v tomto případě paralelní vývoj
- **testovatelnost** - okolí mikroslužby lze jednoduše nasimulovat (například mocky v integračních testech nebo pomocí nějaké testovací HTTP proxy) a tak pohodlně otestovat end-to-end
- **škálovatelnost** - jednotlivé mikroslužby lze paralelizovat nezávisle, což umožňuje lépe rozložit výkon na serverech (výkonově náročná služba může běžet na více instancích než nenáročná)
- **vlastnictví** - vlastnictví a zodpovědnost za menší celky lze lépe rozložit v týmech
- **izolace chyby** - v případě, že v jedné mikroslužbě dojde k chybě nebo přestane fungovat, ostatní mikroslužby tím často nejsou (alespoň ne přímo) ovlivněny

#### Soběstačnost

Ideální mikroslužba se nachází v jednom souboru, který lze jedním příkazem spustit bez nutnosti instalovat desítky dalších služeb, databází či serverů, což přinejmenším velmi zdržuje, přinejhorším podporuje šíření chyb.

#### Synergie s agilními metodikami

Mikroslužby jsou vděčnými produkty agilního vývoje software. Jsou totiž podobně jako správně definované uživatelské stories **vertikální** (průřezové). Jestliže každá mikroslužba realizuje kompletně jednu separátní uživatelsky cennou funkcionalitu (např. uchovávání objednávek a jejich správa), každé uživatelské story se zpravidla týká jedné či několika málo služeb.

### Nevýhody mikroslužeb

#### Meziprocesní komunikace

Komunikace mezi procesy je logicky řádově pomalejší, než komunikace v rámci jednom procesu. Musí proběhnout kódování zprávy, její odeslání, přijetí, dekódování a zpracování, nemluvě často o nutnosti zaslat stejným způsobem i odpověď.

#### Distribuované transakce

Přiznejme si, že distribuované transakce nepatří mezi nejjednodušší problémy řešené v IT. Komplikace nastanou, pokud je nutné mezi několika mikroslužbami zajistit atomicitu operací, obzvláště v situaci, kdy každá mikroslužba používá vlastní databázi. Řeší se často oklikou - případy užití se změní tak, aby se systém transakcím vyhnul.

#### Změny rozhraní (API)

Je logické, že čím více komponent využívá rozhraní nějaké mikroslužby, tím obtížněji se toto rozhraní mění. Nekompatibilní změna pak vyžaduje úpravu všech závislých komponent, což mohou být v nejhorším možném případě i všechny. Dobrým přístupem je v tomto případě navrhnout aplikaci tak, aby byla co nejtolerantnější ve zpracování přijímaných dat (například aby ignorovala obsah "navíc" nebo nepředpokládala pevné pořadí prvků)

#### Nutnost monitoringu

Pokud začnete s implementací mikroslužeb, časem jich už z principu budete mít hodně. Tím pádem se v nich budete muset nějak vyznat a vědět, co se s nimi děje. Proto se do mikroslužeb často přidává nějaká možnost vzdáleného sledování či správy, která umožňuje rychlou reakci v případě chyby (nasazení opravené verze, hotswap, restart služby, atd.). Sledováním výkonu v čase je také možné včas identifikovat nutnost škálování některé z nich.

#### Nepřímé vzájemné ovlivnění

Mikroslužby běžící na jednom serveru se vzájemně nutně ovlivňují - zátěž jedné mikroslužby se negativně projeví na výkonu druhé služby. Musí se tedy řešit, jak budou mikroslužby v rámci jednoho serveru nakombinované, i když na sobě nejsou přímo závislé.

#### Infrastrukturní kód

Aby byla mikroslužba užitečná, musí být schopná komunikovat s okolním světem. Tento základní požadavek s sebou však již implicitně nese určitou úroveň složitosti - každá služba musí minimálně obsahovat kód pro obsluhu vnějšího rozhraní. A tento kód se velmi často opakuje.

#### Duplicitní modely rozhraní

Při komunikaci dvou komponent musí zprávě rozumět oba účastníci - formát zprávy tedy musí být nutně definovaný na obou stranách. V případě mikroslužeb to může znamenat určitou duplicitu

#### Obtížnost návrhu

Často není vůbec jednoduché rozdělit systém na takové části, aby každá z nich tvořila nezávislou mikroslužbu a zároveň režie vzájemné komunikace nepřekročila únosnou mez. Vyžaduje to velmi pečlivou analýzu.

### Reference

- http://projects.spring.io/spring-boot/
- http://martinfowler.com/articles/microservices.html
- http://microservices.io/patterns/microservices.html
- http://blog.krecan.net/2013/09/22/conwayuv-zakon/
- http://harmful.cat-v.org/cat-v/unix_prog_design.pdf