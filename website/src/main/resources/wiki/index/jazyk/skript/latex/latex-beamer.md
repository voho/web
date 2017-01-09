## Jazyk LaTeX: Beamer

LaTeX Beamer je balíček maker pro sázecí systém LaTeX, který umožňuje snadnou tvorbu profesionálně vypadajících prezentací. Tvorba prezentací je sice velmi odlišná od sazby odborných článků či literatury, ale i v tomto ohledu odvádí TeX svou práci výborně.

### Kompilace

```bash
pdflatex prezentace.tex
```

### Základní šablona

```latex
\documentclass{beamer}
\usetheme{Darmstadt}
\usecolortheme{default}

% balíčky

\usepackage[utf8]{inputenc}
\usepackage[czech]{babel}
\usepackage{graphicx}

% informace o dokumentu

\title[krátký titulek prezentace]{dlouhý titulek prezentace}
\subtitle[krátký název prezentace]{dlouhý název prezentace}
\author{jméno autora}
\date{\today}

% text dokumentu

\begin{document}

% titulní stránka
% (název prezentace, autor, datum...)

\begin{frame}
  \titlepage
\end{frame}

% osnova prezentace
% (většinou se pro délku vypouští)

\begin{frame}
  \tableofcontents[pausesections]
\end{frame}

% obsah prezentace
% (používají se klasické sekce)

\section{Úvod}

\begin{frame}
  \frametitle{Hlavní nadpis (nepovinný)}
  \framesubtitle{Vedlejší nadpis (nepovinný)}
  % ...
\end{frame}

\end{document}
```

**Základní témata:** Antibes, Boadilla, Frankfurt, Juanlespins, Montpellier, Singapore, Bergen, Copenhagen, Goettingen, Madrid, Paloalto, Berkeley, Darmstadt, Hannover, Malmoe, Pittsburgh, Berlin, Dresden, Ilmenau, Marburg, Rochester

**Barevná témata:** albatross, beaver, beetle, crane, default, dolphin, fly, lily, orchid, rose, seahorse, sidebartab, whale, wolverine

### Příprava a provedení prezentace

1. připravit soubory (např. ze šablony)
1. vytvořit strukturu (sekce a podsekce)
1. vytvořit slajdy
1. doladit obsah slajdů 
1. otestovat prezentaci

- hovořit nahlas a srozumitelně směrem k publiku
- nečíst slajdy, ale rozvíjet body na nich uvedené
- na slajd nedávat celé věty, ale zhruba 20 až 40 slov (maximálně 80)
- publikum bude jeden slajd vnímat zhruba 50 sekund
- při prvním použití připomenout ty nejpodstatnější věci
- nenechat se unést možnostmi LaTeXu a nepřehánět to s matematickými vzorci
- používat krátké věty, nejlépe fráze
- na každém slajdu by měl být pokud možno alespoň jeden názorný obrázek
- používat sloupce a rámce
- nepoužívat příliš dlouhé seznamy
- nepoužívat poznámky pod čarou
- nepřidávat dlouhé bibliografické reference

### Tipy a triky

#### Bloky

Bloky se musí nacházet na slajdu, tedy v prostředí *frame*.

```latex
% obyčejný blok

\begin{block}{Rodina}
  \begin{itemize}
    \item{rodiče}
    \begin{itemize}
      \item{otec}
      \item{matka}
    \end{itemize}
    \item{potomci}
    \begin{itemize}
      \item{syn}
      \item{dcera}
    \end{itemize}
  \end{itemize}
\end{block}

% výrazný blok

\begin{alertblock}{Pozor}
  Nekrmte dravá zvířata!
\end{alertblock}
```

### Postupné odkrývání bodů

```latex
\begin{itemize}[<+->]
  \item{první bod}
  \item{druhý bod}
  \item{třetí bod}
\end{itemize}
```

#### Ohraničení textu

```latex
\shadowbox{text se stínem}
\fbox{text s okrajem}
\doublebox{text s dvojitým okrajem}
\ovalbox{text oble ohraničený (slabě)}
\Ovalbox{text oble ohraničený (silně)}
```

#### Sloupce

```latex
\begin{frame}
  \frametitle{Sloupce}
  \begin{columns}

    % první sloupec (50 procent)
    \column{.5\textwidth}
  
    Ve vedlejším sloupci si vyberte svou oblíbenou zmrzlinu.  
  
    % druhý sloupec (50 procent)
    \column{.5\textwidth}
  
    \begin{itemize}
      \item{čokoládová}
      \item{vanilková}
      \item{jahodová}
      \item{šmoulová}
    \end{itemize}
  
  \end{columns}
\end{frame}
```

#### Příprava pro tisk

```latex
\documentclass[handout]{beamer}
```

#### Nastavení vlastní barvy

```latex
\documentclass[xcolor=dvipsnames]{beamer}
% ...
\usecolortheme[named=Mahogany]{structure}
```

- [seznam extra barev](http://www.informatik.uni-freiburg.de/~frank/latex-kurs/latex-kurs-3/farben/Extra-Farben.pdf)

```latex
\definecolor{clrfg}{RGB}{0,0,0}
\definecolor{clrfg2}{RGB}{50,90,130}
\definecolor{clrbg}{RGB}{255,255,255}
\definecolor{clrbg2}{RGB}{200,200,250}

\setbeamercolor{alerted text}{fg=clrfg2}
\setbeamercolor{background canvas}{bg=clrbg}
\setbeamercolor{block title}{bg=clrbg2, fg=clrfg2}
\setbeamercolor{frametitle}{fg=clrbg}
\setbeamercolor{item projected}{fg=clrbg}
\setbeamercolor{sidebar}{bg=clrbg}
\setbeamercolor{sidebar}{parent=palette primary}
\setbeamercolor{structure}{bg=clrfg2, fg=clrfg2}
\setbeamercolor{subsection in sidebar}{fg=clrbg}
\setbeamercolor{subsection in sidebar shaded}{fg=clrbg}
\setbeamercolor{title}{fg=clrbg}
\setbeamercolor{titlelike}{fg=clrbg}
```

#### Obsah a titulní stránka na jednom slajdu

```latex
\begin{frame}[shrink]
  \begin{columns}[t] 
    \column{.4\textwidth}
      \titlepage
    \column{.6\textwidth}
      \tableofcontents
  \end{columns}
\end{frame}
```

#### Vypnutí navigačních symbolů

```latex
\setbeamertemplate{navigation symbols}{}
```

#### Obrázek na celý slajd

```latex
\setbeamertemplate{background canvas}{\includegraphics[width=\paperwidth]{obrazek.pdf}}
% ...
% obsah slajdu
% ...
\setbeamertemplate{background canvas}{} 
```

#### Škrtnuté písmo

```latex
\usepackage{ulem}
% ...
\sout{škrtnuté písmo}
```

#### Výpis zdrojového kódu

Trik je v tom, že prostředí *verbatim* musí být umístěno na slajdu typu **fragile**.

```latex
\begin{frame}[fragile]
  \begin{verbatim}
  % ...
  \end{verbatim}
\end{frame}
```

### Reference

- http://www.ctan.org/tex-archive/macros/latex/contrib/beamer/doc/beameruserguide.pdf
- http://bitbucket.org/rivanvx/beamer/wiki/Home
- http://www.math-linux.com/spip.php?article77
- http://www.abclinuxu.cz/clanky/navody/beamer-latex-na-prezentace
- http://www.root.cz/clanky/beamer-prezentace-v-pdflatexu/
- http://old.nabble.com/full-framed-image-td2313538.html