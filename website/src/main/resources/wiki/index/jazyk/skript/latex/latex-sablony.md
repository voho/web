## Jazyk LaTeX: Šablony

### Online knihovna

- [Latex Templates](http://www.latextemplates.com/)

### Oficální šablony pro LaTeX

Několik šablon pro LaTeX, které se používají na konferencích nebo ve vědeckých časopisech.

- [IEEE Transaction](http://tug.ctan.org/tex-archive/macros/latex/contrib/IEEEtran/)
- [Springer](ftp://ftp.springer.de/pub/tex/latex/llncs/latex2e/llncs2e.zip)
- [Proceedings of the SPIE](http://kmh-lanl.hansonhub.com/spie/)
- [NIPS 2005](http://www.kyb.tuebingen.mpg.de/bs/people/bs/nips05.html)
- [další šablony](http://www.tech.plym.ac.uk/spmc/links/latex/latex_templates.html)

### Základní šablona pro report

Jednoduchá univerzální šablona pro různé referáty, semestrálky a eseje.

```latex
\documentclass{report}
\usepackage[utf8]{inputenc}
\usepackage[czech]{babel}
\usepackage{graphicx}

% šablona z webu http://voho.cz

\title{Název dokumentu}
\author{Jméno autora}

% #########
% # START #
% #########

\begin{document} 

% titulní stránka
\maketitle

% obsah
\tableofcontents

% abstrakt
\begin{abstract}
abstrakt  
\end{abstract}

% ########
% # TEXT #
% ########

\chapter{Kapitola}

\section{Sekce}

text dokumentu

% #########
% # KONEC #
% #########

\end{document}
```

### Základní šablona pro Beamer

Jednoduchá univerzální šablona pro prezentace využívající LaTeX Beamer.

```latex
\documentclass[xcolor=dvipsnames]{beamer}
\usepackage[utf8]{inputenc}
\usepackage[czech]{babel}
\usepackage{graphicx}
\usepackage{ulem}
\usepackage{tikz}
\usetheme{Warsaw} 
\usecolortheme[named=Mahogany]{structure} 
\setbeamertemplate{navigation symbols}{}

% šablona z webu http://voho.cz

\title{Název prezentace}
\author{Jméno autora}
\institute{Instituce}
\date{Datum}

\begin{document}

% #########
% # START #
% #########

% úvod a obsah

\begin{frame}[shrink]
  \titlepage
\end{frame}

% odrážky

\begin{frame}
  \frametitle{Postupně odkrývané odrážky}

  \begin{itemize}[<+->]
    \item{odrážka}
    \item{odrážka}
    \item{odrážka}
  \end{itemize}
  
\end{frame}

% obrázek

\begin{frame}
  \frametitle{Obrázek}
  
  \begin{figure}
    \begin{center}
      \includegraphics[width=.6\textwidth]{obrazek.jpg}
    \end{center}
    \caption{Ukázka obrázku}
    \label{fig:obrazek}
  \end{figure}
  
\end{frame}

% tabulka

\begin{frame}
  \frametitle{Tabulka}
  
  \begin{table}
    \begin{center}
      \begin{tabular}{c|c|c}
      A & B & C \\
      \hline
      0 & 1 & 2 \\
      3 & 4 & 5 \\
      \end{tabular}
    \end{center}
    \caption{Ukázka tabulky}
    \label{tab:tabulka}
  \end{table}
  
\end{frame}

% zdrojový kód

\section{Zdrojový kód}

\begin{frame}[fragile,shrink]
  \frametitle{Zdrojový kód}
  
\begin{verbatim}
int sum (int a, int b)
{
  return a + b;
}
\end{verbatim}

\end{frame}

% #########
% # KONEC #
% #########

\end{document}
````

### Tisk rukověti (handout)

Použitím níže uvedeného kódu lze automaticky vygenerovat tzv. **rukověť** (handout), která na každé stránce obsahuje 8 původních slajdů (2 sloupce, 4 řádky). 

```latex
\documentclass[a4paper]{article}
\usepackage{pdfpages}

\begin{document}
\includepdf[pages=1-last,nup=2x4,landscape=false,frame=true,noautoscale=true,scale=0.68,delta=2mm 5mm]{presentation.pdf}
\end{document}
```