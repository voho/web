## Jazyk LaTeX: Sazba kódu a pseudokódu

### Makro Listings (kód)

Pro sazbu programového kódu s možností zvýraznění syntaxe lze použít balík *listings*.

```latex
\usepackage{listings}
```

Pro lepší vzhled je lepší jej nastavit zhruba takto:

```latex
\usepackage[usenames,dvipsnames]{color}
\usepackage{listings}
\lstset{
  columns=fullflexible,
  language=Java,
  numbers=left,
  numbersep=5pt,
  basicstyle=\small,
  numberstyle=\footnotesize\color{Gray},
  commentstyle=\it\footnotesize\color{Gray}
}
```

#### Příklady

##### Zdrojový kód

```latex
\begin{minipage}{\paperwidth}
\begin{lstlisting}
// zdrojový kód
\end{lstlisting}
\end{minipage}
```

##### Kód ze souboru

```latex
\lstinputlisting{source.c}
```

##### Popisek

Nad zdrojový kód stačí uvést následující řádek:

```latex
\lstset{
  language=C,
  caption={Popisek},
  label=oznaceni
}
```

### Makro Algorithmic (pseudokód)

Pro sazbu pseudokódu lze použít například balík *algorithmic*.

```latex
\usepackage{algorithmic}
```

#### Příklady

##### Genetický algoritmus

```latex
\begin{figure}
\centering
\begin{algorithmic}[1]
\STATE{create an initial population $ P_0 $ (usually random)}
\STATE{evaluate the fitness of each invidivual in $ P_0 $}
\FOR{$ g $ in range 1 .. $ g_{max} $}
\STATE{create a new empty population $ P_g $}
\STATE{take individuals from $ P_{g - 1} $ using the selection operator and copy them into the new population $ P_g $ either directly or using crossover and mutation operators}
\STATE{evaluate the fitness of each individual in $ P_g $}
\STATE{replace the old population $ P_{g - 1} $ by the new population $ P_g $}
\ENDFOR
\RETURN{best-ranked individual from $ P_g $}
\end{algorithmic}
\caption{Genetic algorithm pseudocode (generational model)}
\label{fig:ga_pseudocode_dynamic}
\end{figure}
```

##### Turnajový výběr

```latex
\begin{figure}
\centering
\begin{algorithmic}[1]
\STATE{take $ N $ random individuals from the population}
\IF{random real number $ \in \langle 0, 1) < 0.95 $}
\RETURN{individual with the best fitness}
\ELSE
\RETURN{random individual}
\ENDIF
\end{algorithmic}
\caption{The tournament selection algorithm pseudocode}
\label{fig:tournament}
\end{figure}
```

### Reference

- http://en.wikibooks.org/wiki/LaTeX/Packages/Listings
- http://www.ctan.org/pkg/algorithms
- http://developer.berlios.de/docman/display_doc.php?docid=800&group_id=3442
- http://en.wikibooks.org/wiki/LaTeX/Algorithms_and_Pseudocode#Typesetting_using_the_algorithmic_package