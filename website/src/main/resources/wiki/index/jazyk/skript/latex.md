## Základy jazyka LaTeX

Pokud chcete vytvořit profesionálně vypadající technický článek, knihu anebo rovnou celá skripta, měli byste zapomenout na legrácky typu Microsoft Word a naučit se pracovat s pořádným sazebním systémem - třeba s LaTeXem. Předem upozorňuji, že se nejedná o gumu :). Název se správně vyslovuje jako [latech], jelikož znak "X" je ve skutečnosti řecké "chí". Toto písmeno symbolizuje umění nebo dovednost. Co tedy LaTeX umí a dovede?

LaTeX je komplexní **systém pro sazbu dokumentů**. Stará se tedy o zalamování textu do odstavců, vkládání obrázků, seznamů, nadpisů, matematických výrazů, rozdělování dokumentu na jednotlivé stránky, ligatury, "vdovy" a "sirotky", odsazení... zkrátka o to, co spousta lidí správně neumí. Málokdo totiž zná všechna pravidla dobré sazby a podle toho také většina dokumentů vypadá.

Technicky se jedná se o **soubor maker** (o nadstavbu) publikačního systému *TeX*, který v roce 1977 začal tvořit **Donald Knuth** (jelikož prý nebyl spokojen se sazbou svých matematických článků). Primárně byl *TeX* určen právě pro sazbu matematických odborných prací, ale dnes už se dá *TeX* a jeho nadstavby používat i pro tvorbu krásných barevných prezentací a dalších zajímavých dokumentů.

Rovnou zapomeňte na to, že je LaTeX něco jako Microsoft Word. Není, a to ani zdaleka. Jeho filozofie je jiná. Nejedná se o editor, kde ihned vidíte co bude výsledkem (WYSIWYG - what you see is what you get), ale o systém, který se snaží porozumět **struktuře** a **sémantice** vašeho textu a vytvořit pro něj vhodnou grafickou podobu (WYWIWYG - what you want is what you get).

- Uživatelé Linuxu najdou LaTeX v balíčku **texlive**.
- Uživatelé Windows si mohou stáhnout [MiKTex](http://miktex.org/).

### Jak psát v LaTeXu?

Psaní dokumentů v LaTeXu je velmi podobné psaní HTML stránek. Máte zdrojové soubory (obvykle s příponou *.tex*), ve kterých se nachází vlastní obsah dokumentu a dodatečné formátovací značky. Tyto zdrojové soubory si nemůžete rovnou prohlédnout (k HTML také potřebujete internetový prohlížeč), nejprve z nich musíte kompilací vytvořit univerzálnější formát, který si budete moci vytisknout, odeslat e-mailem, apod.

Většina lidí chce z LaTeXu dostat soubor *PDF*, který vytvoříte příkazem "pdflatex soubor.tex" v příkazové řádce (ano, i Windows mají příkazovou řádku...). Pokud zatoužíte po formátech DVI, PS, PNG a dalších, hledejte přesnější postupy na internetu.

### Speciální znaky

Některé znaky mají v TeXu speciální význam. Označují se jako **speciální znaky**. Je-li potřeba jejich speciální význam zrušit a vypsat je tak jak jsou, je před ně nutné vložit **zpětné lomítko**, jak je uvedeno zde:

```latex
\# \$ \% \& \_ \{ \}
```

### Příkazy

Příkazem se v jazyce TeX označuje posloupnost alfanumerických znaků, začínající zpětným lomítkem. Příkaz může mít povinné i nepovinné parametry. Ty nepovinné se uvádí v **hranatých závorkách** a lze je vynechat, povinné v závorkách **složených** a vynechat je nelze. Názvy příkazů jsou citlivé na velikost písmen (case-sensitive). Někdy se příkazy alternativně označují jako **makra**.

```latex
\prikaz[nepovinny_parametr]{povinny_parametr}
```

### Prostředí

Prostředím se v jazyce TeX nazývá část dokumentu, ohraničená speciálními příkazy (začátek prostředí a konec prostředí). Prostředí se vytvoří následovně:

```latex
\begin{nazev_prostredi}
...
obsah prostředí
...
\end{nazev_prostredi}
```

### Základní šablona

Nebudeme chodit okolo horké kaše a rovnou si ukážeme základní šablonu pro dokument v systému LaTeX:

```latex
% oznamujeme typ dokumentu, velikost písma, papíru...

\documentclass[11pt,a4paper,oneside]{report}

% oznamujeme, že budeme psát česky v UTF-8

\usepackage[czech]{babel}
\usepackage[utf8]{inputenc}

% přidáme podporu pro vkládání grafiky

\usepackage{graphicx}

% nastavení mezer mezi odstavci

\parskip=6.5pt plus 4.5pt minus 4.5pt

% odsazení prvního řádku odstavce

\parindent=1.2em

% zde nastavíme název dokumentu, autora a datum
% pokud datum neuvedete, bude použito aktuální (v době kompilace)

\title{Ukázková šablona v LaTeXu}
\author{Vojtěch Hordějčuk}
\date{Duben 2009}

\begin{document}

% ...
% zde bude obsah dokumentu
% ...

\end{document}
```

### Struktura dokumentu

Dobrá struktura dokumentu umožňuje čtenářům lépe **pochopit jeho obsah**. Dobrý dokument by měl tedy mít **stromovou strukturu**, jak daná problematika zabíhá do větších a větších detailů. Dokument by měl být rozdělen do částí, části do kapitol, kapitoly do sekcí, sekce do podsekcí...

Je důležité si uvědomit, že zde nejde pouze o to, aby LaTeX udělal na daném místě nadpis velkým tučným písmem. Tato struktura bude použita mimo jiné i pro generování obsahu, sazbu a číslování obrázků a tabulek. Hiearchii značek pro tvorbu struktury dokumentu si ukážeme v dalším příkladu:

```latex
\part{Část}
\chapter{Kapitola} % pouze dokumenty typu "book" a "report"
\section{Sekce}
\subsection{Podsekce}
\subsubsection{Podpodsekce}
\paragraph{Odstavec}
\subparagraoh{Pododstavec}
```

### Automatický obsah, seznam obrázků a tabulek

Výhoda LaTeXu je v tom, že vašemu dokumentu rozumí a po prvním "průchodu" vaším dokumentem již ví, na jaké části ho máte rozdělen. Proto je schopen vytvořit automaticky obsah. K jeho vytvoření je však nutné zkompilovat dokument dvakrát (nejdříve si LaTeX vytvoří pomocné soubory a podruhé je načte a obsah vytvoří).

```latex
\maketitle % titulek
\tableofcontents % obsah
\listoftables % seznam tabulek
\listoffigures % seznam obrázků
```

### Formátování

Formátování textu (tučné písmo, kurzíva...) opět slouží hlavně čtenáři k lepšímu pochopení textu. Pomocí odlišného formátu lze **zdůraznit** důležitý pojem, jméno nebo název. Další funkcí, kterou se budeme zabývat, je změna velikosti písma. Věřím, že to se změnou velikostí nebudete příliš přehánět. Mějte na paměti, že po vás bude dokument pravděpodobně někdo číst...

```latex
% formátování

\textbf{tučné písmo}
\emph{kurzíva}
\uline{podtržené písmo}
\textsc{malé kapitálky}
\sout{přeškrtnuté písmo}
\texttt{psací stroj}

{\bf tučné písmo}
{\em kurzíva}
{\sc malé kapitálky}
{\tt psací stroj}

% velikost písma

\tiny{titěrné}
\scriptsize{malinkaté}
\footnotesize{malinké}
\small{malé}
\normalsize{normální}
\large{větší}
\Large{velké}
\LARGE{velikánské}
\huge{obrovské}
\Huge{obrovitánské}
```

### Ruční zalomení řádku, nová stránka

Někdy je nutné ručně zalomit řádek jinak, než novým odstavcem - například v adrese. Ruční zalomení řádku se provede příkazem *\\\\* (dvěma lomítky).

Ačkoliv by se rozdělení dokumentu na stránky mělo nechat na LaTeXu, můžete samozřejmě vynutit zalomení stránky ručně. K tomuto účelu slouží příkazy *\newpage* (na tomto místě chci ukončit stránku), *\clearpage* (obsah za tímto příkazem chci mít na začátku čisté stránky) a *\cleardoublepage* (obsah za tímto příkazem chci mít na liché stránce čisté dvojstránky).

### Číslované a nečíslované seznamy

Jak v LaTeXu vytvořit číslované a nečíslované seznamy? Použijte tzv. **prostředí** (příkaz pro zahájení bloku, obsah bloku a příkaz pro ukončení bloku) *\enumerate* (číslovaný seznam) nebo *\itemize* (nečíslovaný seznam). Každá položka začíná příkazem *\item*. Seznamy lze do sebe i vnořovat. Lépe to pochopíte z příkladu:

```latex
% číslovaný seznam

\begin{enumerate}
\item{Pozdrav}
\item{Rozhovor}
\item{Rozloučení}
\end{enumerate}

% nečíslovaný seznam

\begin{itemize}
\item{Dobrý den!}
\item{Ahoj!}
\item{Zdááár!}
\end{itemize}

% vnořený seznam

\begin{itemize}
\item{Barvy}
\begin{itemize}
\item{Oranžová}
\item{Zelená}
\item{Bílá}
\end{itemize}
\item{Tvary}
\begin{itemize}
\item{Obdélník}
\item{Lichoběžník}
\item{Kruh}
\end{itemize}
\end{itemize}
```

### Obrázky a reference v textu

Před vkládáním obrázků je nutné do souboru importovat balíček *graphicx* pomocí příkazu *\usepackage*.

```latex
\usepackage{graphicx}
```

Základní a holý kód pro vložení jednoho obrázku vypadá takto:

```latex
\includegraphics{obrazek.png}
```

Obvykle však potřebujete obrázky systematicky **číslovat** a mít u nich nějaký **popis**. V textu by mělo být možné se na obrázek **odkázat** nějakým jednoduchým způsobem. V tomto směru vychází LaTeX autorům maximálně vstříc prostředím *\figure*. Ukážeme si o něco delší kód, který demonstruje tuto užitečnou funkcionalitu:

```latex
% obrázek umístíme na střed

\begin{center}

% deklarujeme obrázek s popisem
% parametr v hranatých závorkách určuje preferované umístění
% (lze jich vypsat i více, LaTeX si jednu vybere)
% h - právě zde
% t - nahoře na stránce
% b - dole na stránce
% p - na samostatné stránce

\begin{figure}[hp]

% nyní vložíme vlastní obrázek
% můžeme definovat rozměry (width,height,scale)

\includegraphics[width=13cm]{labute.png}

% popis obrázku

\caption{Fotografie labutí}

% název obrázku (používá se pro referenci)
% (prefix není povinný, používá se pro přehlednost)
% fig: - obrázek
% tab: - tabulka
% chap: - kapitola
% eq: - rovnice

% název musí být pod popisem, aby odkazy
% směřovaly na obrázek a ne na kapitolu

\label{fig:labute}

\end{figure}
\end{center}
```

V textu se dá na obrázek odkazovat pomocí příkazu *\ref*, který na dané místo vloží číslo obrázku s uvedeným názvem (labelem) nebo příkazem *\pageref*, který LaTeX nahradí číslem stránky, na kterém je daný obrázek umístěn:

```latex
% \ref - vloží číslo obrázku
% \pageref - vloží číslo stránky, na které obrázek je

Včera jsem vyfotil krásné labutě.
Podívejte se na obrázek \ref{fig:labute},
který najdete na stránce \pageref{fig:labute}
```

Někdy vás možná zarazí, že se obrázky nezobrazí tam, kde byste je čekali. LaTeX používá k rozmisťování obrázků poněkud jinou filozofii - snaží se je rozmístit tak, aby zbytečně nenarušovaly tok textu. Je to také pozůstatek ze starých dob, kdy byla obrázková příloha k technickým knihám vytvářena zvlášť, např. jako ilustrace.

### Poznámky pod čarou a na okraji

Poznámky pod čarou se někdy mohou hodit. Místo poznámky se do textu vloží malé číslo nebo písmeno a na spodním okraji stránky poté čtenář najde poznámku rozepsanou.

```latex
Husy jsou lepší hlídači než psi \footnote{Při neobvyklém hluku začnou kejhat}.
```

Poznámky na okraji se zobrazí na levém či pravém okraji stránky.

```latex
Chovají se hlavně kvůli produkci kuřecího masa \marginpar{brojlerová kuřata}.
```

Bohužel, v tabulkách, seznamech a dalších prostředích je to s poznámkami trochu složitější. Celé prostředí je nutné uzavřít do prostředí *\minipage*, přičemž poznámky se poté zobrazí na spodním okraji tohoto prostoru (tedy ne na spodním okraji stránky).

```latex
\begin{minipage}
\begin{itemize}
\item{první položka \footnote{poznámka k první položce}}
\item{druhá položka \footnote{poznámka k druhé položce}}
\end{itemize}
\end{minipage}
```

### Základy sazby matematických výrazů

Sazba matematiky byla prvotní motivací ke vzniku celého *TeXu*, takže není překvapením, že umožňuje zápis snad všech možných výrazů, integrálů, rovnic, vzorců, teorémů, matic, atd.

#### Vložení výrazu do textu

Chcete-li do dokumentu vložit nějakou tu formulku, stačí ji od okolního textu v odstavci oddělit symbolem dolaru ($). Také je možné udělat nový odstavec, který bude dolarem začínat i končit a bude se celý skládat jen z matematických výrazů.

```latex
Diskriminant se vypočítá jako $ D = b^{2} - 4 \cdot a \cdot c $.
```

Popis všech možných symbolů a funkcí je by byl obrovský, takže to nechám na Google a vaší kreativitě. Určitě se nemusíte nazpaměť učit vše, najdete si vždy jen to, co budete v dané chvíli potřebovat.

#### Několik příkladů

##### Algebra, vnořený zlomek

€€ x = a_0 + \frac{1}{a_1 + \frac{1}{a_2 + \frac{1}{a_3 + a_4}}} €€

```latex
x = a_0 + \frac{1}{a_1 + \frac{1}{a_2 + \frac{1}{a_3 + a_4}}}
```

€€ x = a_0 + \frac{1}{a_1 + \frac{1}{a_2 + \frac{1}{a_3 + a_4}}} €€

```latex
x = a_0 + \frac{1}{a_1 + \frac{1}{a_2 + \frac{1}{a_3 + a_4}}}
```

##### Goniometrické funkce, logaritmy, suma

€€ \lbrace \sin{x}^2 + \cos{x}^2 - (\sum_{k=0}^{x} \log_{2} kx) \rbrace, x \in N €€

```latex
\lbrace \sin{x}^2 + \cos{x}^2 - (\sum_{k=0}^{x} \log_{2} kx) \rbrace, x \in N
```

€€ \lbrace \sin{x}^2 + \cos{x}^2 - (\sum_{k=0}^{x} \log_{2} kx) \rbrace, x \in N €€

```latex
\lbrace \sin{x}^2 + \cos{x}^2 - (\sum_{k=0}^{x} \log_{2} kx) \rbrace, x \in N
```

##### Matice 3x3, vektory

€€ 
\begin{pmatrix}
0 & \beta & 2 \alpha \\
3 & \overrightarrow{c} & 0 \\
3 \rho & 4 & \overrightarrow{a}
\end{pmatrix}
€€

```latex
\begin{pmatrix}
0 & \beta & 2 \alpha \\
3 & \overrightarrow{c} & 0 \\
3 \rho & 4 & \overrightarrow{a}
\end{pmatrix}
```

€€ 
\begin{pmatrix} 0 & \beta & 2 \alpha \\ 3 & \overrightarrow{c} & 0 \\ 3 \rho & 4 & \overrightarrow{a} \end{pmatrix} 
€€

```latex
\begin{pmatrix} 0 & \beta & 2 \alpha \\ 3 & \overrightarrow{c} & 0 \\ 3 \rho & 4 & \overrightarrow{a} \end{pmatrix} 
```

##### Víceřádkový výraz

€€ 
\begin{align*}
x &= a+b+3ab \\
y &= \sqrt[3]{a+b + \frac{1}{5a+b}} \\
z &= -3a+b
\end{align*}
€€

```latex
\begin{align*}
x &= a+b+3ab \\
y &= \sqrt[3]{a+b + \frac{1}{5a+b}} \\
z &= -3a+b
\end{align*}
```

##### Integrál

€€ S_0 = \int_{-\infty}^{0} \sin{x} \; dx €€

```latex
S_0 = \int_{-\infty}^{0} \sin{x} \; dx
```

€€ S_0 = \int_{-\infty}^{0} \sin{x} \; dx €€

```latex
S_0 = \int_{-\infty}^{0} \sin{x} \; dx
```

#### České uvozovky

Typografická pravidla pro psaní uvozovek se liší jazyk od jazyka a je nutné je znát, protože LaTeX sám od sebe uživateli žádné uvozovky nenutí.
Čeština obsahuje jednoduché a dvojité uvozovky, které lze je nalézt ve znakové sadě Unicode pod kódy 201E, 201C, 201A a 2018. Využít lze i příkaz *uv*.

```latex
malíř prodal svou \uv{mazanici}
```

### Reference

- http://www-h.eng.cam.ac.uk/help/tpl/textprocessing/teTeX/latex/latex2e-html/ltx-2.html
- http://ctan.tug.org/tex-archive/info/lshort/english/lshort.pdf
- http://en.wikibooks.org/wiki/LaTeX
- http://vavricek.cs.vsb.cz/index.php/LaTeX/Syntaxe
- http://uvozovky.davi.cz/