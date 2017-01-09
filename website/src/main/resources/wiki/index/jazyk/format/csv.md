## CSV

Formát CSV (comma-separated values) je univerzální a velmi rozšířený textový formát pro výměnu dat mezi dvěma systémy. Je podporovaný téměř všemi nástroji, které pracují s daty. Je standardizovaný v [RFC-4180](http://tools.ietf.org/html/rfc4180).

Soubor CSV se skládá z libovolného počtu řádků, přičemž každý řádek se skládá z libovolného počtu sloupců. Standard RFC uvádí, že oddělovačem řádků je posloupnost znaků **CRLF** (*\r\n*) a oddělovačem sloupců je čárka (*,*). Většina nástrojů však umožňuje libovolnou volbu těchto znaků podle potřeby (častým případem je například použití svislítka *|* či středníku *;* jako oddělovače sloupců). 

Na prvním řádku může být umístěna hlavička, která popisuje názvy jednotlivých sloupců. Formát hlavičky je stejný jako formát ostatních sloupců. V případě, že je v souboru definována hlavička, měl by být počet sloupců na každém řádku stejný, jinak hlavička nedává smysl.

Formát CSV nespecifikuje žádnou znakovou sadu a soubor sám o sobě neobsahuje žádné údaje o tom, jak jej naparsovat. Proto je nutné před výměnou CSV souboru přesně dohodnout formát sloupců a řádků včetně kódování souboru.

### Problematické znaky

Co když se však ve sloupcích vyskytují konce řádků? V tomto případě je nutné udělat výjimku a sloupec uzavřít do dvojitých uvozovek. To však vede k další výjimce: dvojité uvozovky se stávají speciálním znakem a proto je nutné ošetřit i je. Z těchto příčin vznikají další pravidla, která je nutná dodržovat:

**V případě, že sloupec obsahuje dvojitou uvozovku nebo konec řádku, musí být sloupec uzavřen ve dvojitých uvozovkách a každá dvojitá uvozovka uvnitř sloupce musí být zdvojena.**

Někdy se opět můžete setkat s výjimkami, kdy se například dvojitá uvozovka nahrazuje posloupností znaků *\"* (tzv. escape sekvence).

```text
value1,"some values are ""special"" for CSV",value3
```

### Gramatika

```bnf
file = [header CRLF] record *(CRLF record) [CRLF]
header = name *(COMMA name)
record = field *(COMMA field)
name = field
field = (escaped / non-escaped)
escaped = DQUOTE *(TEXTDATA / COMMA / CRLF / 2DQUOTE) DQUOTE
non-escaped = *TEXTDATA
2DQUOTE = '""'
DQUOTE = '"'
COMMA = ','
CRLF = '\r\n'
```

### Příklady

#### CSV bez hlavičky

```text
John,Doe,senior manager,41
Mike,Newman,accountant,15
Sandra,Atkinson,visual designer,415
```

#### CSV s hlavičkou

```text
first name,last name,position,age
John,Doe,senior manager,41
Mike,Newman,accountant,15
Sandra,Atkinson,visual designer,415
```

#### CSV s hlavičkou a uvozovkami

```text
name,favourite quote
David,"""Doh!"" - Homer Simpson"
Mike,"""I like cookies!"" - Cookie monster"
```

#### CSV s novým řádkem ve sloupci

```text
id,query
1,"SELECT *
FROM users
WHERE id = 42"
2,"INSERT INTO users
(name,age)
VALUES
('John',23)"
3,SELECT * FROM users ORDER BY name
4,SELECT * FROM users ORDER BY id DESC
```

### Reference

- http://tools.ietf.org/html/rfc4180