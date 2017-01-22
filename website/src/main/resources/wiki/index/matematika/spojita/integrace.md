## Integrace

### Primitivní funkce

Primitivní funkce k [funkci](wiki/zobrazeni) *f(x)* v intervalu *I* je taková funkce *F(x)*, jejíž [derivace](wiki/derivace) je v daném intervalu rovna *f(x)*. Platí tedy, že € \forall x \in (a,b) :\; F'(x) = f(x) €.

### Neurčitý integrál

Neurčitý integrál funkce *f(x)* je [množina](wiki/mnozina) všech primitivních funkcí k funkci *f(x)*. Postup hledání těchto funkcí se nazývá **integrování** a je to opačný proces k derivování.

Integrace funkce *f* se zapisuje jako €\int f(x) \mathrm{d}x = F(x) €.

### Tabulkové integrály

#### Polynomy

| Funkce | Primitivní funkce
|---|---
| €x^c€ | €\frac{x^{c+1}}{c+1} + C, c \neq -1, x > 0€
| €\frac{1}{x}€ | €\ln \lvert x \rvert + C, x \neq 0€

#### Exponenciály a logaritmy

| Funkce | Primitivní funkce
|---|---
| €e^x€ | €e^x + C€
| €c^x€ | €\frac{c^x}{\ln c} + C, c > 0, c \neq 1€
| €\log_c x€ | €x \cdot \log_c x - \frac{x}{\ln c} + C€
| €\ln x€ | €x \cdot \log_e x - \frac{x}{\ln e} = x \cdot \ln x - x + C€

#### Goniometrické funkce

| Funkce | Primitivní funkce
|---|---
| €\sin x€ | €-\cos x + C€
| €\cos x€ | €\sin x + C€
| €\sinh x€ | €\cosh x + C€
| €\cosh x€ | €\sinh x + C€

### Metody integrování

#### Substituce

**Věta o přímé substituci:** Nechť *f(x)* je funkce definovaná na intervalu *I* a má na něm primitivní funkci *F(x)*. Nechť *g(x)* je funkce z intervalu *J* do intervalu *I*, která je diferencovatelná na *J*. Pak *F(g)* je primitivní funkce k *f(g)g'* na *J*:

€€
\int f(g(x)) \cdot g'(x) \; \mathrm{d}x = (\int f(y) \; \mathrm{d}y) |_{y=g(x)}
€€

#### Per partes

**Věta o integraci per partes:** Nechť *f(x)* a *g(x)* jsou funkce diferencovatelné na intervalu *I*. Pak *f(x)g'(x)* je integrovatelná na *I* právě tehdy, je-li *f'g* integrovatelná na *I*. Navíc platí, že jestliže je *F* primitivní funkce k *f'g* na *I*, pak je *fg-F* primitivní funkce k *fg'* na *I*.

€€
\int f(x) \cdot g'(x) \; \mathrm{d}x = f(x) \cdot g(x) - \int f'(x) \cdot g(x) \; \mathrm{d}x
€€

### Reference

- předmět X01MA1 na FEL ČVUT
- http://wood.mendelu.cz/math/maw-html/index.php?lang=en&form=integral
- http://math.feld.cvut.cz/mt/txtd/3/txc3da3a.htm
- http://math.feld.cvut.cz/mt/txtd/3/txc4da3a.htm
- http://math.feld.cvut.cz/mt/txtd/3/txc4da3c.htm