## Derivace

Derivace [funkce](wiki/zobrazeni) vyjadřuje závislost mezi velikostí změny její hodnoty a velikostí změny jejího argumentu. Derivace funkce v bodě má geometrický význam **směrnice tečny** v tomto bodě (pokud je zde definována). Opačnou operací k derivování je [integrování](wiki/integrace). 

Pojem derivace silně souvisí s definicí **spojitosti** funkce.

€€ 
f'(x) = \frac{\mathrm{d}x}{\mathrm{d}y} = \lim_{\delta \rightarrow 0} \frac{f(x+\delta)-f(x)}{\delta} = \lim_{x \rightarrow x_0} \frac{f(x)-f(x_0)}{x-x_0}
€€

### Tabulkové derivace

#### Polynomy

€€
\begin{align*}
(c)' &= 0 \\
(x^c)' &= c \cdot x^{c-1}
\end{align*}
€€

#### Mocniny a logaritmy

€€
\begin{align*}
(c^x)' &= c^x \cdot \ln c \\
(e^x)' &= e^x \cdot \ln e = e^x \\
(\log_a x)' &= \frac{1}{x \cdot \ln a} \\
(\ln x)' &= \frac{1}{x \cdot \ln e} = \frac{1}{x}
\end{align*}
€€

#### Goniometrické funkce

€€
\begin{align*}
(\sin x)' &= \cos x \\
(\cos x)' &= -\sin x \\
(\mathrm{tg}\; x)' &= \frac{1}{\cos^2 x} \\
(\mathrm{cotg}\; x)' &= -\frac{1}{\sin^2 x} 
\end{align*}
€€

### Pravidla pro výpočet

€€
\begin{align*}
(c \cdot f(x))' &= c \cdot f'(x) \\
(f(x) + g(x))' &= f'(x) + g'(x) \\
(f(x) \cdot g(x))' &= f'(x) \cdot g(x) + f(x) \cdot g'(x) \\
(\frac{f(x)}{g(x)})' &= \frac{f'(x) \cdot g(x) - f(x) \cdot g'(x)}{(g(x))^2} \\
(f[g(x)])' &= f'[g(x)] \cdot g'(x) \\
(f(x)^{g(x)})' &= (e^{g(x) \cdot \ln f(x)})'
\end{align*}
€€

### Vícenásobná derivace

Derivace *n*-tého řádu (také *n*-tá derivace) je *n*-krát postupně provedená derivace.

€€
f''(x) = (f'(x))'
€€

### Reference

- předmět X01MA1 na FEL ČVUT