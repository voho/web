## Python 3

![Guido van Rossum (2006)](van_rossum.png){.right}

Python je interpretovaný programovací jazyk zaměřený na čitelnost výsledného kódu.
Například pro oddělování syntaktických celků se místo tradičních závorek používá odsazení a jazyk je velmi expresivní, s množstvím syntaktických zkratek.
Jazyk je to dynamicky typovaný s automatickou správou paměti

Jazyk Python vytvořil *Guido van Rossum* začátkem devadesátých let 20. století a od komunity dostal titul "benevolentní doživotní diktátor" (Benevolent Dictator For Life).

> Readability is often enhanced by reducing unnecessary variability. When possible, there's a single, obvious way to code a particular construct. This reduces the number of choices facing the programmer who is writing the code, and increases the chance that will appear familiar to a second programmer reading it. Yet another contribution to Python's readability is the choice to use punctuation mostly in a conservative, conventional manner. Most operator symbols are familiar to anyone with even a vague recollection of high school math, and no new meanings have to be learned for comic strip curse characters like @&$!. *- Guido van Rossum*

!TODO!

### Komentáře

```python
# Jednořádkový komentář

""" 
Víceřádkový komentář.
"""
```

### Datové typy

#### Primitivní datové typy

```python
# TODO
```

#### Složené datové typy

Seznam:

```python
prazdny_seznam = []
seznam_cisel = [1, 2, 3]
prvni_cislo = seznam_cisel[0]
posledni_cislo = seznam_cisel[-1]

1 in seznam_cisel 
# = True
```

Uspořádaná n-tice:

```python
tuple = (1, 2, 3)

tuple[0]
# = 1

tuple[0] = 8
# -> chyba TypeError

a, b, c = (1, 2, 3)
# a = 1, b = 2, b = 3
```

Slovník:

```python
prazdny_slovnik = {}
slovnik = {"name": "John Doe", "age": 31}

slovnik["name"]
# = "John Doe"

slovnik["missing"]  
# -> chyba KeyError

slovnik.get("missing")
# = None
```

Množina:

```python
prazdna_mnozina = set()
mnozina = {1, 2, 3, 5, 7, 11, 13}

prunik = mnozina1 & mnozina2
sjednoceni = mnozina1 | mnozina2
rozdil = mnozina1 - mnozina2

3 in mnozina 
# = True
```

### Řídící konstrukce

#### Podmínka

```python
if hodnota > 0:
    # hodnota je větší než nula
elif hodnota < 0:
    # hodnota je menší než nula
else:
    # hodnota je nula
```

#### Cyklus FOR

```python
for i in (1, 2, 3):
    # i = 1, i = 2, i = 3

for i in range(3):
    # i = 0, i = 1, i = 2
```

#### Cyklus WHILE

```python
x = 0
while x < 4:
    print(x)
```

#### Ošetřování chyb

```python
# TODO
```

### Funkce

```python
# TODO
```

### Dekorátory

```python
# TODO
```

### Modularizace

#### Moduly

```python
# TODO
```

#### Třídy

```python
# TODO
```

### Reference

- https://learnxinyminutes.com/docs/cs-cz/python3/
- https://en.wikipedia.org/wiki/Benevolent_dictator_for_life