## Náhodná veličina

Náhodná reálná veličina je libovolná funkce *X*, která přiřazuje každému elementárnímu jevu z množiny *omega* právě jedno reálné číslo.

€€ X \;:\; \Omega \rightarrow \mathbb{R} €€

### Směs náhodných veličin

Náhodná veličina *Y* se nazývá **směs** veličin *X1* až *Xn* s koeficienty *c1* až *cn*, když se dá vyjádřit jako:

€€ Y = \mathrm{Mix}_{(c_1 \ldots c_n)} (X_1 \ldots X_n) = \sum_{i=1}^{n} c_i \cdot X_i €€

Pro koeficienty *c* musí platit:

€€ \sum_{i=1}^{n} c_i = 1, c_i \in \langle 0,1 \rangle €€

Její pravděpodobností míra je:

€€ P(Y) = \sum_{i=1}^{n} c_i \cdot P_{X_i} €€

Její distribuční funkce je:

€€ F(Y) = \sum_{i=1}^{n} c_i \cdot F_{X_i} €€

Speciálně pro dvě veličiny:

€€ Y = c \cdot X_1 + (1-c) \cdot X_2 €€

### Distribuční funkce

Distribuční funkce *F* vyjadřuje pravděpodobnost, že náhodná funkce ná hodnotu menší nebo rovnou reálnému číslu *x*. Je definována jako € F(x) = {\rm P}(X \le x), x \in \mathbb{R} €. Distribuční funkce nabývá hodnot 0 až 1 (včetně obou krajních hodnot) a je neklesající. Její limita v € -\infty € je 0, limita v € +\infty € je 1.

Mezi pravděpodobnostní mírou a distribuční funkcí platí následující vztahy:

€€ P[x_1\leq X \leq x_2] = F(x_2)-F(x_1) €€

Pro spojitou náhodnou veličinu s hustotou pravděpodobnosti *f* lze distribuční funkci odvodit jako její integrál:

€€ F(x) = \int\limits_{-\infty}^x \rho(t)\mathrm{d}t  €€

Pokud existuje derivace distribuční funkce *F* v bodě *x*, pak mezi hustotou pravděpodobnosti *f* a distribuční funkcí *F* platá následující vztah:

€€ f(x)=\frac{\mathrm{d}F(x)}{\mathrm{d}x} €€

### Kvantilová funkce

Kvantil *Q_p* lze definovat jako libovolnou hodnotu náhodné proměnné *x*, která rozděluje soubor dat na dvě části, a sice na část s hodnotami nižšími či rovnými kvantilu a část s hodnotami vyššími než kvantil.

Vztah mezi kvantilem *Q_p* a distribuční funkcí *F* spojité náhodné veličiny *x*:

€€ P(X ≤ Q_p) = p \rightarrow F(Q_p) = p €€

Pokud je distribuční funkce rostoucí, je kvantil její inverzí:

€€ Q_p = F^{−1}(p) €€

Nejpoužívanějším kvantilem je například *medián* (50% kvantil) nebo *percentil* (1% kvantil). Medián rozděluje statistický soubor na dvě stejně velké množiny.

### Charakteristiky náhodné veličiny

#### Střední hodnota

Střední hodnota **diskrétní** náhodné veličiny je aritmetický průměr všech jejích hodnot. Lze si ji představit jako "férovou cenu" za jednu účast ve hře, ve které je výhra rovna hodnotě veličiny. Pokud by si člověk za tuto cenu kupoval účast dostatečně dlouho, jeho majetek by se nezvětšoval, ani nezmenšoval.

€€ \mathrm{EX} = \sum_{\omega \in \Omega} \omega \cdot p_X(\omega) €€

Střední hodnota **absolutně spojité** náhodné veličiny je definována pomocí integrálu:

€€ \mathrm{EX} = \int_{-\infty}^{+\infty} x \cdot f_X(x) \;\mathrm{d}x €€

Pro všechny případy lze střední hodnotu definovat použitím **kvantilové funkce**:

€€ \mathrm{EX} = \int_{0}^{1} q_X (\alpha) \;\mathrm{d}\alpha €€

#### Kvantil

!TODO!

#### Rozptyl (disperze)

!TODO!

#### Normovaná náhodná veličina

Normovaná náhodná veličina je taková náhodná veličina, která má nulovou střední hodnotu a jednotkový rozptyl.

Normalizace náhodné veličiny:

€€ \mathrm{norm} X = \frac{X - \mathrm{EX}}{\sigma_X} €€

Zpětná transformace:

€€ X = \mathrm{EX} + \sigma_x \mathrm{norm} X €€

### Reference

- předmět X01MVT na FEL ČVUT
- M. Navara: Pravděpodobnost a matematická statistika
- V. Rogalewicz: Pravděpodobnost a statistika pro inženýry