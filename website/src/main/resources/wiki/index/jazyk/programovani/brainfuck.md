## Jazyk Brainfuck

Programovací jazyk Brainfuck patří mezi ezoterické jazyky, určené k pobavení a zamyšlení programátorů. Navrhl jej v roce 1993 švýcarský programátor **Urban Müller** a obsahuje pouze 8 příkazů. Každý příkaz je reprezentován právě jedním znakem, neznámé znaky jsou ignorovány. Brainfuck je Turingovsky úplný a formálně je tedy stejně silný jako ostatní programovací jazyky.

### Seznam příkazů

Programovací jazyk Brainfuck obsahuje celkem 8 příkazů. Každý z nich se zapisuje právě jedním znakem ASCII. Má k dispozici pole buněk o zadané velikosti (obvykle 30000), které je na počátku vynulováno. Každá buňka může nabývat hodnot 0-255 (rozsah je tedy jeden bajt). Aktuální buňku určuje tzv. **datový ukazatel**.

| Příkaz | Význam
|---|---
| < | Přesune datový ukazatel o jednu buňku vlevo
| > | Přesune datový ukazatel o jednu buňku vpravo
| + | Inkrementuje hodnotu v aktuální buňce
| - | Dekrementuje hodnotu v aktuální buňce
| . | Vypíše hodnotu v aktuální buňce (obvykle jako odpovídající znak ASCII)
| , | Načte hodnotu na vstupu do aktuální buňky (obvykle ASCII hodnotu zadaného znaku)
| &#91; | Je-li hodnota v aktuální buňce různá od 0, přesune instrukční ukazatel na odpovídající pravou závorku
| &#93; | Je-li hodnota v aktuální buňce rovna 0, přesune instrukční ukazatel na odpovídající levou závorku

### Program "Hello World!"

#### Triviální verze

```bf
+++++ +++++ +++++ +++++ +++++
+++++ +++++ +++++ +++++ +++++
+++++ +++++ +++++ +++++ ++ .
+++++ +++++ +++++ +++++ +++++
++++ .
+++++ ++ ..
+++ .
----- ----- ----- ----- -----
----- ----- ----- ----- -----
----- ----- ----- ----- -----
----- ----- .
+++++ +++++ +++++ +++++ +++++
+++++ +++++ +++++ +++++ +++++
+++++ +++++ +++++ +++++ +++++
+++++ +++++ +++++ +++ .
----- --- .
+++ .
----- - .
----- --- .
----- ----- ----- ----- -----
----- ----- ----- ----- -----
----- ----- ----- -- .
----- ----- ----- ----- .
--- .
```

#### Optimalizovaná verze

Využitím cyklů lze kód značně zkrátit.

```bf
++++++++++[>+++++++>++++
++++++>+++>+<<<<-]>++.>+
.+++++++..+++.>++.<<++++
+++++++++++.>.+++.------
.--------.>+.>.
```

### Jednoduché programy

#### Nulování aktuální buňky

```bf
[-]
```

#### Vyhledání první nenulové buňky

Dopředné hledání (fast forward):

```bf
[>]<
```

Zpětné hledání (rewind):

```bf
[<]>
```

#### Sčítání

Nechť buňka *#0* obsahuje první sčítanec a buňka *#1* druhý. Po skončení následující cyklu bude v buňce *#0* nula a v buňce *#1* součet.

```bf
[->+<]
```

### Reference

- http://en.wikipedia.org/wiki/Brainfuck