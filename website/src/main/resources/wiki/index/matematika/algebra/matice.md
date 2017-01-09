## Matice

Matice typu € (m,n) € je uspořádaná *m*-tice prvků z € \mathbb{R}^n €. Jednotlivé prvky takové *m*-tice se nazývají **řádky matice**. Nechť € a_r € je *r*-tý řádek matice *A*. Potom se *s*-tý prvek tohoto řádku nazývá **prvek matice** na pozici € (r,s) €. 

Matice se nejčastěji zapisuje jako tabulka čísel ohraničená závorkami. 

€€
\begin{pmatrix}
a_{1,1} & \ldots & a_{1,n} \\
\vdots & & \vdots \\
a_{m,1} & \ldots & a_{m,n} \\
\end{pmatrix}
€€

Jako **hlavní diagonála** matice se označují všechny její prvky € a(i,j) €, kde € i = j €. Je to tedy první prvek prvního řádku, druhý prvek druhého řádku, a tak dále.

### Transpozice

Nechť *A* je matice s prvky € a(i,j) €. Jako **transponovaná matice** k matici *A* se označuje matice € A^T € s prvky € a^T(j,i) €. Transponovat matici tedy znamená zaměnit její řádky a sloupce.

€€
A = \begin{pmatrix}
1 & 2 & 3 \\
10 & 20 & 30 \\
\end{pmatrix},
\; \; \;
A^T = \begin{pmatrix}
1 & 10 \\
2 & 20 \\
3 & 30\\
\end{pmatrix}
€€

### Druhy matic

Matice typu € (m,n) €, kde € m = n €, se označuje jako **čtvercová matice**.

€€
\begin{pmatrix}
1 & 5 \\
-3 & 0 \\
\end{pmatrix}
€€

Matice libovolného typu, která má všechny prvky všech řádků rovny nule, se nazývá **nulová matice**.

€€
\mathbb{0} = \begin{pmatrix}
0 & 0 & 0 \\
0 & 0 & 0 \\
\end{pmatrix}
€€

Čtvercová matice typu € (n,n) €, která má všechny prvky na pozicích € (i,j) € kde € i = j € rovny *1* a ostatní prvky nulové, se označuje jako **jednotková matice** € E_n €.

€€
E_2 = \begin{pmatrix}
1 & 0 \\
0 & 1 \\
\end{pmatrix},
\; \; \;
E_4 = \begin{pmatrix}
1 & 0 & 0 & 0 \\
0 & 1 & 0 & 0 \\
0 & 0 & 1 & 0 \\
0 & 0 & 0 & 1 \\
\end{pmatrix}
€€

Matice je **symetrická**, jestliže se při transpozici nemění, tedy platí, že € A = A^T €.

€€
\begin{pmatrix}
1 & 2 \\
2 & 0 \\
\end{pmatrix},
\; \;
\begin{pmatrix}
1 & 2 & 3 \\
2 & 0 & 1 \\
3 & 1 & 0 \\
\end{pmatrix}
€€

Jestliže platí, že € A = -A^T €, mluví se o **matici antisymetrické**.

€€
\begin{pmatrix}
1 & 2 \\
-2 & 0 \\
\end{pmatrix},
\; \;
\begin{pmatrix}
1 & -2 & -3 \\
2 & 0 & -1 \\
3 & 1 & 0 \\
\end{pmatrix}
€€

Jako **diagonální matice** se označuje taková matice, jejíž prvky € a(i,j) € jsou nulové pro € i \neq j € a zároveň existuje alespoň jeden nenulový prvek € a(i,j) € pro € i = j € (na hlavní diagonále).

€€
\begin{pmatrix}
0 & 0 \\
0 & 8 \\
\end{pmatrix},
\; \;
\begin{pmatrix}
1 & 0 & 0 \\
0 & 9 & 0 \\
0 & 0 & 0 \\
\end{pmatrix}
€€

### Operace

#### Sčítání

Sčítat lze pouze dvě matice *A*, *B* stejného typu € (m,n) €. Výsledná matice *C* je opět stejného typu a každý její prvek je roven součtu odpovídajících prvků obou matic *A* a *B*, tedy € c(i,j) = a(i,j) + b(i,j) €.

€€
\begin{pmatrix}
a_{1,1} & \ldots & a_{1,n} \\
\vdots & & \vdots \\
a_{m,1} & \ldots & a_{m,n} \\
\end{pmatrix}
+
\begin{pmatrix}
b_{1,1} & \ldots & b_{1,n} \\
\vdots & & \vdots \\
b_{m,1} & \ldots & b_{m,n} \\
\end{pmatrix}
=
\begin{pmatrix}
a_{1,1}+b_{1,1} & \ldots & a_{1,n}+b_{1,n} \\
\vdots & & \vdots \\
a_{m,1}+b_{m,1} & \ldots & a_{m,n}+b_{m,n} \\
\end{pmatrix}
€€

#### Násobení matice konstantou

Matice se násobí konstantou tak, že se tou samou konstantou vynásobí každý její prvek. Násobením matice s prvky € a(i,j) € konstantou *gamma* tedy vznikne matice s prvky € \gamma \cdot a(i,j) €.

€€
\gamma \cdot
\begin{pmatrix}
a_{1,1} & \ldots & a_{1,n} \\
\vdots & & \vdots \\
a_{m,1} & \ldots & a_{m,n} \\
\end{pmatrix}
=
\begin{pmatrix}
\gamma \cdot a_{1,1} & \ldots & \gamma \cdot a_{1,n} \\
\vdots & & \vdots \\
\gamma \cdot a_{m,1} & \ldots & \gamma \cdot a_{m,n} \\
\end{pmatrix}
€€

#### Násobení matice maticí

Matici *A* typu € (r,s) € lze násobit pouze s libovolnou maticí *B* typu € (s,t) €, kde € r,s,t \in N €. Vzniklá matice bude typu € (r,t) €, bude tedy mít tolik řádků jako první matice *A* a tolik sloupců jako druhá matice *B*. Operace násobení matic není komutativní.

€€
C = A \cdot B \rightarrow c(i,j) = \sum_{k=1}^s a(i,k) \cdot b(k,j)
€€

Násobení matic ilustruje následující obrázek.

€€
\begin{pmatrix}
\blacksquare & \blacksquare & \blacksquare \\
\Box & \Box & \Box \\
\end{pmatrix}
\cdot
\begin{pmatrix}
\blacksquare & \Box \\
\blacksquare & \Box \\
\blacksquare & \Box \\
\end{pmatrix}
=
\begin{pmatrix}
\blacksquare & ?  \\
? & ? \\
\end{pmatrix}
€€

### Reference

- předmět X01AVT na ČVUT
- předmět X01ALG na ČVUT
- Petr Olšák: Úvod do algebry, zejména lineární