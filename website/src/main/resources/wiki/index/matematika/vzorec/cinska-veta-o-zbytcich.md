## Čínská věta o zbytcích

Čínská věta o zbytcích je důležitý poznatek z **modulární aritmetiky**, který se využívá například v kryptografii. První a nejstarší zmínka o této větě pochází z knihy **Sun Tzu Suan Ching**, kterou ve 3. století napsal čínský matematik **Sun Tzu**. Větu lze zformulovat různě, ale všechny formulace jsou více či méně ekvivalentní.

Zadána je konečná množina po dvou nesoudělných přirozených čísel €m_1€ až €m_k€ větších než 2 a pro každé z nich rovnost, která uvádí hodnotu hledaného čísla v daném okruhu modulo. Zadání má formálně tento tvar:

€€
\begin{align*}
x &= a_1 \;\;\mathrm{v}\; \mathbb{Z}_{m_1} \\
& \vdots \\
x &= a_k \;\;\mathrm{v}\; \mathbb{Z}_{m_k} \\
\end{align*}
€€

Tato soustava má jednoznačné řešení *x* v okruhu modulo *M*, kde *M* je součin všech čísel €m_1€ až €m_k€. Řešení se dá zapsat jako následující lineární kombinace:

€€ x = a_1 \cdot q_1 + \ldots + a_k \cdot q_k \;\;\mathrm{v}\; \mathbb{Z}_{M} €€

€€ M = \prod_{i=1}^{k} m_i €€

Součin všech čísel €m_j€, kde *j* je různé od *i*, se pro zjednodušení následujícího zápisu označí jako €s_i€. Každý z koeficientů €q_i€ se spočítá jako součin inverze €s_i€ v okruhu modulo €m_i€ a €s_i€. Pro hledání inverze lze použít metodu pokus-omyl nebo rozšířený [Eukleidův algoritmus](wiki/algoritmus-eukleid).

€€
\begin{align*}
s_i &= \prod_{j \neq i} m_j \\
t_i &= (s_i)^{-1} \;\;\mathrm{v}\; \mathbb{Z}_{m_i} \\
q_i &= s_i \cdot t_i \;\;\mathrm{v}\; \mathbb{Z}_M \\
\end{align*}
€€

Nalezené koeficienty se dosadí do výše uvedené lineární kombinace a vypočte se řešení v okruhu modulo *M*. Řešení v oboru celých čísel je nekonečně mnoho. Každé takové řešení *y* lze zapsat v tomto tvaru:

€€ y = x + k \cdot M, k \in \mathbb{Z} €€

### Příklady

#### Zadání

Řešte následující soustavu:

€€
\begin{align*}
x &= 2 \;\;\mathrm{v}\; \mathbb{Z}_3 \\
x &= 1 \;\;\mathrm{v}\; \mathbb{Z}_8 \\
x &= 7 \;\;\mathrm{v}\; \mathbb{Z}_{13} \\
\end{align*}
€€

#### Řešení

Řešení bude v tomto tvaru:

€€ x = 2 \cdot q_1 + 1 \cdot q_2 + 7 \cdot q_3 \;\;\mathrm{v}\; Z_{3 \cdot 8 \cdot 13 = 312} €€

Výpočet jednotlivých koeficientů se provede podle výše uvedeného vzorce (je však možné použít i jiný postup). První koeficient musí být roven 1 v okruhu modulo *3* a 0 ve všech ostatních (8, 13).

€€
\begin{align*}
s_1 &= \prod_{j \neq 1} m_j = 8 \cdot 13 = 104 \\
t_1 &= (s_1)^{-1} = (104)^{-1} = (2)^{-1} = 2 \;\;\mathrm{v}\; \mathbb{Z}_3 \\
q_1 &= s_1 \cdot t_1 = 104 \cdot 2 = 208 \;\;\mathrm{v}\; \mathbb{Z}_{312} \\
\end{align*}
€€

Druhý koeficient musí být roven 1 v okruhu modulo *8* a 0 ve všech ostatních (3, 13).

€€
\begin{align*}
s_2 &= \prod_{j \neq 2} m_j = 3 \cdot 13 = 39 \\
t_2 &= (s_2)^{-1} = (39)^{-1} = (7)^{-1} = 7 \;\;\mathrm{v}\; \mathbb{Z}_8 \\
q_2 &= s_2 \cdot t_2 = 39 \cdot 7 = 273 \;\;\mathrm{v}\; \mathbb{Z}_{312} \\
\end{align*}
€€

Třetí koeficient musí být roven 1 v okruhu modulo *13* a 0 ve všech ostatních (3, 8).

€€
\begin{align*}
s_3 &= \prod_{j \neq 3} m_j = 3 \cdot 8 = 24 \\
t_3 &= (s_3)^{-1} = (24)^{-1} = (11)^{-1} = 6 \;\;\mathrm{v}\; \mathbb{Z}_{13} \\
q_3 &= s_3 \cdot t_3 = 24 \cdot 6 = 144 \;\;\mathrm{v}\; \mathbb{Z}_{312} \\
\end{align*}
€€

Vyjádříme hledaný výsledek:

€€ x = 2 \cdot 208 + 1 \cdot 273 + 7 \cdot 144 = 1697 = 137 \;\;\mathrm{v}\; Z_{312} €€

Soustavu rovnic tedy řeší tato celá čísla:

€€ x = 137 + k \cdot 312, k \in \mathbb{Z} €€

#### Zadání

V nůši je *n* vajec. Pokud z ní odebíráme vejce po dvou, třech a po pěti najednou, v nůši nakonec zůstane 1, 2, respektive 4 vejce. Pokud odebíráme vejce po sedmi kusech, v nůši nakonec nezůstane vejce žádné. Jaká je nejmenší hodnota *n*?

#### Řešení

K výpočtu využijeme čínskou větu o zbytcích. Nejprve si zadání přepíšeme na soustavu rovností:

€€
\begin{align*}
n &= 1 \;\;\mathrm{v}\; \mathbb{Z}_2 \\
n &= 2 \;\;\mathrm{v}\; \mathbb{Z}_3 \\
n &= 4 \;\;\mathrm{v}\; \mathbb{Z}_5 \\
n &= 0 \;\;\mathrm{v}\; \mathbb{Z}_7 \\
\end{align*}
€€

Řešení bude v tomto tvaru:

€€ x = 1 \cdot q_1 + 2 \cdot q_2 + 4 \cdot q_3 + 0 \cdot q_4 \;\;\mathrm{v}\; Z_{2 \cdot 3 \cdot 5 \cdot 7 = 210} €€

Nyní se provede výpočet koeficientů.

€€
\begin{align*}
s_1 &= \prod_{j \neq 1} m_j = 3 \cdot 5 \cdot 7 = 105 \\
t_1 &= (s_1)^{-1} = (105)^{-1} = (1)^{-1} = 1 \;\;\mathrm{v}\; \mathbb{Z}_2 \\
q_1 &= s_1 \cdot t_1 = 105 \cdot 1 = 105 \;\;\mathrm{v}\; \mathbb{Z}_{210} \\
\end{align*}
€€

€€
\begin{align*}
s_2 &= \prod_{j \neq 2} m_j = 2 \cdot 5 \cdot 7 = 70 \\
t_2 &= (s_2)^{-1} = (70)^{-1} = (1)^{-1} = 1 \;\;\mathrm{v}\; \mathbb{Z}_3 \\
q_2 &= s_2 \cdot t_2 = 70 \cdot 1 = 70 \;\;\mathrm{v}\; \mathbb{Z}_{210} \\
\end{align*}
€€

€€
\begin{align*}
s_3 &= \prod_{j \neq 3} m_j = 2 \cdot 3 \cdot 7 = 42 \\
t_3 &= (s_3)^{-1} = (42)^{-1} = (2)^{-1} = 3 \;\;\mathrm{v}\; \mathbb{Z}_5 \\
q_3 &= s_3 \cdot t_3 = 42 \cdot 3 = 126 \;\;\mathrm{v}\; \mathbb{Z}_{210} \\
\end{align*}
€€

Poslední koeficient počítat nemusíme, protože je ve výsledku stejně násoben nulou a proto se vůbec neprojeví. Nyní tedy koeficienty dosadíme do vzorce:

€€ n = 1 \cdot 105 + 2 \cdot 70 + 4 \cdot 126 = 749 = 119 \;\;\mathrm{v}\; Z_{210} €€

V nůši tedy může být minimálně **119** vajec. V oboru celých čísel by ale řešení opět bylo nekonečně mnoho. Všechna by se dala zapsat ve tvaru:

€€ x = 119 + k \cdot 210, k \in \mathbb{Z} €€

### Reference

- předmět X01DML na FEL ČVUT
- předmět X01AVT na FEL ČVUT