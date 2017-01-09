## Kódování

Kódování je proces, který převádí informaci z jedné formy do druhé. Před formální definicí kódování je nutné zavést několik základních pojmů z **teorie informace**.

### Informace

Pro vymezení pojmu informace je nutné přijmout následující předpoklady:

- Reálné objekty existují nezávisle na vědomí subjektu.
- Není zásadní rozdíl mezi jevem, informací a reálným objektem. Informace neexistuje bez hmoty, je produktem jejího pohybu.
- Poznání reálného objektu není úplné a neměnné, je tudíž třeba zkoumat, jak vzniká a vyvíjí se.

Pojem "informace" je spíše filozofický. Lze ji chápat jako jsoucno, které odstraňuje nejistotu či nevědění, čímž ovlivňuje stav jejího příjemce (využívání informace k rozhodování je základním projevem inteligence). Z hlediska komunikace je informace vztahem objektu a subjektu v prostoru a čase. Další možnou definicí je, že informace je abstraktní, smysluplná reprezentace výroků o věcech.

### Abeceda

Nechť je definovaná libovolná konečná množina *A*, obsahující vzájemně rozlišitelné prvky. Tato množina se může označit jako **abeceda** a její prvky jako **symboly** (znaky). Abecedou mohou být například znaky latinky (A-Z), dekadické číslice (0-9), binární číslice (0, 1), grafické symboly (Braillovo písmo) nebo vizuální symboly (barvy semaforu).

### Slovo nad abecedou

Každá konečná uspořádaná množina (posloupnost) symbolů z abecedy *A* se nazývá **slovo** nebo **řetězec** nad abecedou *A*. Celkový počet symbolů v této posloupnosti se nazývá **délka slova**. Dále je definováno speciální slovo *epsilon*, což je slovo o nulové délce (prázdný řetězec). Slova jsou generována **informačním zdrojem** (odesílatelem). Pravidla pro generování slov a jejich spojování do zpráv se nazývají **syntaxe**.

### Abecední zobrazení

Každé jednoznačné [zobrazení](wiki/zobrazeni) (jednomu vzoru odpovídá nanejvýš jeden obraz), které slovům nad abecedou *A* přiřazuje slova nad abecedou *B*, se nazývá **abecední zobrazení**. Abecední zobrazení je **inverzibilní** právě tehdy, když je prosté (každým dvěma různým vzorům odpovídají dva různé obrazy).

### Kódování

Abecední zobrazení se nazývá **kódování**, je-li **bijektivní** (každému vzoru přiřazuje právě jeden obraz). Kódování je **stejnoměrné**, mají-li obrazy všech symbolů vstupní abecedy stejnou délku.

Kódování se nejčastěji definuje pomocí kódovacího [algoritmu](wiki/algoritmus) nebo formálními pravidly, na jejichž základě se převod informace provádí.

## Kód

Kód je **syntaxe**, která předepisuje pravidla pro tvorbu slov nějaké abecedy. Formálně je to uspořádaná trojice:

€€
\begin{align*}
& (S,C,f) \\
& S \to C^{+} \\
\forall a_1,a_2 \in & S : a1 \neq a2 \Rightarrow f(a_1) \neq f(a_2)
\end{align*}
€€

- *S* - konečná množina zdrojových jednotek
- *C* - konečná množina kódových jednotek (různá slova různých abeced)
- *f* = prosté zobrazení z *S* do *C+* (kódovací funkce)

Dopředný proces převodu slova nad zdrojovou abecedou *A* na slovo nad cílovou abecedou *B* se nazývá **kódování**, opačný proces pak **dekódování**.

Slovo je **jednoznačně dekódovatelné**, právě když pro něj existuje nanejvýš jedna posloupnost zdrojových slov. Teoreticky ale mohou existovat i slova, která nemají v daném kódu vzory. Není jednoduché rozhodnout, zda je daný kód jednoznačný - je nutné uvést protipříklad.

€€ 
\forall u_1,u_2 \in C : u_1 \neq u_2 \Rightarrow f^{-1}(u_1) \neq f^{-1}(u_2)
€€

Kód je **prefixový**, pokud žádné kódové slovo není předponou (prefixem) jiného kódového slova. Prefixový kód je jednoznačně dekódovatelný symbol po symbolu při čtení zleva doprava.

Kód je **afixový**, pokud žádné kódové slovo není příponou (suffixem) jiného slova. Afixový kód je jednoznačně dekódovatelný symbol po symbolu při čtení zprava doleva.

Kód je **blokový**, pokud všechna kódová slova mají stejnou délku. Blokový kód je jednoznačně dekódovatelný při čtení libovolným směrem.

### Entropie

Entropie v obecném slova smyslu kvantifikuje **množství informace** v nějaké zprávě. Zabýval se jí například Claude Shannon, který jako první použil **bit** jako jednotku informace. Entropie by se dala popsat jako počet otázek typu ANO/NE, kterými lze odhalit obsah zprávy. Pro konečnou množinu zdrojových jednotek *S* a pravděpodobnostní distribuci *P(s)* je informační entropie *Hi* zdrojové jednotky *Si* definována jako záporný logaritmus její pravděpodobnosti:

€€ 
\begin{align*}
S &= \{ s_1, s_2, \ldots, s_n \} \\
P &= \{ P_1, P_2, \ldots, P_n \} \\
H_i &= -\log_2 P_i \mathrm{[bit]}
\end{align*}
€€

Entropie kódu *K* je vážený průměr entropie zdrojových jednotek vážený jejich pravděpodobností:

€€ 
H(K) = \sum_{s \in S} H(s) \cdot P(s)
€€

Průměrná entropie jedné zdrojové jednotky z množiny *S*:

€€
H_{avg}(S) = \sum_{s \in S} P(s) \cdot H(S) = - \sum_{i=1}^n p_i \cdot \log_2 p_i \mathrm{[bit]}
€€

Entropie zdrojové zprávy *X* o délce *n* zřetězené ze zdrojových jednotek *x1* až *xn*:

€€ 
H(X) = \sum_{i=1}^n H(x_i) \mathrm{[bit]}
€€

Entropie je maximální pro rovnoměrné rozložení pravděpodobnosti:

€€ 
H(S) = - \log_2 \frac{1}{n} = \log_2 n \mathrm{[bit]}
€€

Entropie je minimální pro zcela deterministický systém:

€€ 
H(S) = - \log_2 1 = 0 \mathrm{[bit]}
€€

### Redundance

Redundance je rozdíl délky zakódované zprávy *X* a její entropie. Je vždy nezáporná.

€€
R(X) = L(f(X)) - H(X)
€€

Je-li redundance nulová, je použitý kód **optimální**. V případě, že jsou entropie symbolů celočíselné, lze optimální kód jednoduše vytvořit jako prefixový kód, ve kterém délka kódového slova odpovídá entropii. Využít k tomu lze například Huffmanův algoritmus.

### Reference

- předmět X36KOD na FEL ČVUT
- https://akela.mendelu.cz/~rybicka/
- http://www.sds.cz/docs/prectete/epubl/mko_tcai.htm