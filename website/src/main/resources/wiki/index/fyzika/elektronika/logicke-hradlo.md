## Logické hradlo

Logická hradla jsou základními stavebními kameny logických obvodů. Každé hradlo má **vstupy** a jeden **výstup**. Pokud hradlo funguje správně, je výstupní hodnota závislá pouze na vstupech a lze ji popsat jednou ze základních základních logických operací. Logická hradla se skládají do větších funkčních celků, zvaných [kombinační obvody](wiki/kombinacni-obvod). Přidáme-li k hradlům paměťové členy, získáme [obvody sekvenční](wiki/sekvencni-obvod).

### Značení

Existuje několik způsobů, jak hradla zakreslovat. Nejznámější notací je "americký" **ANSI** (kulatý) a druhou nejpoužívanější je zřejmě **IEC** (hranatý). Malým kolečkem (bublinkou) se obvykle značí invertovaný vstup nebo výstup.

### Realizace

K realizaci logických hradel lze použít různé technologie - od hydraulických ventilů přes elektronky, diody až k dnešním tranzistorům. Technologie ovlivňuje parametry výsledného obvodu, jako jsou napěťové úrovně, zpoždění, odolnost vůči rušení, životnost a poruchovost (spolehlivost).

### Invertor (negace)

Nejjednodušším hradlem je invertor. Jedná se o prvek, který obrátí logickou hodnotu na vstupu. Z logické 1 tedy vytvoří logickou 0 a naopak.

| Vstup | Výstup
|---|---
| 0 | 1
| 1 | 0

![invertor](gates_invertor.png)

### Hradlo AND (konjunkce)

Hradlo AND vrací na výstupu logickou 1 právě tehdy, když jsou na všech vstupech logické 1. Ve všech ostatních případech je na výstupu logická 0. Na hradlo AND se můžeme dívat i jako na jednoduchou bránu – je-li na jednom ze vstupů logická 0, i na výstupu je logická 0. Pokud však jeden vstup aktivujeme logickou 1, na výstupu se objeví hodnota druhého vstupu.

| Vstup A | Vstup B | Výstup
|---|---|---
| 0 | 0 | 0
| 0 | 1 | 0
| 1 | 0 | 0
| 1 | 1 | 1

![hradlo AND](gates_and.png)

Hradlo AND si můžeme představit jako obvod se zdrojem, žárovkou a dvěma sériově zapojenými tlačítky, kde logická 1 na vstupu znamená stisknutí tlačítka a svítící žárovka indikuje logickou 1 na výstupu. Jestliže stiskneme obě tlačítka najednou (na obou vstupech jsou logické 1), žárovka se rozsvítí. Pokud jakékoliv z nich pustíme (na libovolném vstupu se objeví logická 0), žárovka zhasne.

![vysvětlení funkce hradla AND pomocí analogie](gates_and_explain.png)

### Hradlo NAND (negace konjunkce)

Hradlo NAND se chová stejně jako hradlo AND s invertovaným výstupem. Narozdíl od něj má však jednodušší vnitřní strukturu. Hradlo NAND je nejčastěji používané hradlo. Je totiž prostorově nenáročné a s jeho pomocí můžeme realizovat jakoukoliv kombinační funkci (tato vlastnost vychází z Booleovy algebry; druhým takovým hradlem je NOR). Z těchto důvodů se většina složitějších digitálních obvodů převádí a následně realizuje právě hradly NAND. Toto hradlo a jeho pravdivostní tabulku si určitě zapamatujte, bude se vám hodit.

| Vstup A | Vstup B | Výstup
|---|---|---
| 0 | 0 | 1
| 0 | 1 | 1
| 1 | 0 | 1
| 1 | 1 | 0

![hradlo NAND](gates_nand.png)

### Hradlo OR (disjunkce)

Hradlo OR má na výstupu logickou 1 právě tehdy, když je logická 1 na libovolném jeho vstupu. V ostatních případech je na výstupu vždy logická 0.

| Vstup A | Vstup B | Výstup
|---|---|---
| 0 | 0 | 0
| 0 | 1 | 1
| 1 | 0 | 1
| 1 | 1 | 1

![hradlo OR](gates_or.png)

Hradlo OR si můžeme představit jako obvod se zdrojem, žárovkou a dvěma paralelně zapojenými tlačítky, kde logická 1 na vstupu znamená stisknutí tlačítka a svítící žárovka indikuje logickou 1 na výstupu. Jestliže stiskneme jedno z tlačítek (na libovolném vstupu se objeví logická 1), žárovka se rozsvítí. Stejný výsledek dostaneme i tehdy, když zmáčkneme obě najednou (dvě logické 1 na vstupu). Aby žárovka zhasla, musíme pustit obě dvě tlačítka (na obou vstupech se objeví logická 0).

![vysvětlení funkce hradla OR pomocí analogie](gates_or_explain.png)

### Hradlo NOR (negace disjunkce)

Hradlo NOR se chová stejně jako hradlo OR s invertovaným výstupem. Podobně jako u hradla NAND platí, že z hradla NOR můžeme vytvořit libovolnou kombinační logickou funkci (tato vlastnost vychází z Booleovy algebry).

| Vstup A | Vstup B | Výstup
|---|---|---
| 0 | 0 | 1
| 0 | 1 | 0
| 1 | 0 | 0
| 1 | 1 | 0

![hradlo NOR](gates_nor.png)

### Hradlo XOR (negace ekvivalence)

Hradlo XOR má na výstupu logickou 1 právě tehdy, když jsou jeho vstupy různé. Dalo by se tedy říci, že je to negace ekvivalence (rovnosti). Chová se podobně jako hradlo OR, ale narozdíl od něj pro dvě logické 1 na vstupu vrací logickou 0. Také si můžeme všimnout, že na výstupu je logická 1 vždy, když je na vstupech lichý počet logických 1.

| Vstup A | Vstup B | Výstup
|---|---|---
| 0 | 0 | 0
| 0 | 1 | 1
| 1 | 0 | 1
| 1 | 1 | 0

![hradlo XOR](gates_xor.png)

### Realizace vícevstupých hradel

Každé hradlo můžeme nakreslit s libovolným počtem vstupů – vícevstupé hradlo lze realizovat třeba jako kaskádu dvouvstupých hradel. Strom hradel by měl být co nejvíce vyvážený, aby bylo zpoždění minimální. Velký počet různých zapojení (se stejnou funkčností) je odůvodnitelný zákonem asociativity v Booleově algebře. Na obrázku je uvedena jedna možná realizace sedmivstupého hradla NAND:

![realizace sedmivstupého hradla NAND](gates_nand_7.png)
