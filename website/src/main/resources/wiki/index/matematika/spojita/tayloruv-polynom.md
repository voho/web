## Taylorův polynom

Nechť má funkce *f* v bodě *a* všechny [derivace](wiki/derivace) až po řád *n*. Taylorův polynom *f* stupně *n* se středem *a* je definován jako:

€€ f(x) = f(a) + \sum_{k=1}^{n} \frac{f^{(k)}(a)}{k!} (x-a)^{k} €€

Taylorův polynom lze použít k aproximaci funkcí. Čím více členů polynomu je k dispozici, tím více se aproximace blíží původní funkci.

Taylorův polynom je založen na tomto principu: dvě funkce si jsou v okolí bodu *a* tím více podobné, čím více se podobají jejich derivace vyšších řádů v tomto bodě.

### Příklady

Taylorův polynom stupně *2* funkce € \sin(x) € se středem v bodě *3*:

€€
T_2(x) = \sin(3) + \cos(3)(x-3) - \frac{1}{2}\sin(3)(x-3)^2
€€

Taylorův polynom stupně *1* funkce € \sin(x) € se středem v bodě *-2*:

€€
T_1(x) = -\sin(2) + \cos(2)(x+2)
€€

Taylorův polynom stupně *1* funkce € \sqrt{x+8} € se středem v bodě *0*:

€€
T_2(x) = \sqrt{8} + \frac{1}{16} \sqrt{8} x - \frac{1}{512} \sqrt{8} x^2
€€

### Reference

- http://math.feld.cvut.cz/mt/txtc/4/txc3ca4e.htm
- http://ivankuckir.blogspot.com/2010/09/tayloruv-polynom-srozumitelne.html
- http://cgi.math.muni.cz/~kriz/?page=pstaylor