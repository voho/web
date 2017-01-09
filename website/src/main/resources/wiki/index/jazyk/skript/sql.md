## Jazyk SQL

Jazyk SQL (Structured Query Language) je jazyk určený pro práci s relačními databázovými systémy (RDBMS). Na začátku 70. let 20. století jej začala vyvíjet společnost **IBM** (konkrétně pánové Raymond F. Boyce, Donald C. Messerly a Andrew Richardson). O několik let později na trh vstoupila společnost **Oracle** (tehdy ještě **Relational Software, Inc.**), která své produkty úspěšně dodávala i americkým vládním organizacím (CIA, U.S. Navy a další).

Příkazy jazyka SQL by se daly rozdělit do těchto skupin:

- *DDL* (data definition language) - příkazy patřící do této skupiny vytvářejí či upravují strukturu databáze (např. tabulky).
 Příklady: **CREATE, ALTER, DROP**...
- *DML* (data manipulation language) - příkazy, které slouží k získávání, ukládání a mazání dat v databázi.
 Příklady: **SELECT, INSERT, UPDATE, DELETE**...
- *DCL* (data control language) - příkazy pro správu uživatelských rolí a práv.
 Příklady: **GRANT, REVOKE**...
- *TCL* (transactional control language) - příkazy pro správu databázových transakcí.
 Příklady: **BEGIN, COMMIT, ROLLBACK**...

### Syntaxe pomocí příkladů

#### DDL (data definition language)

Vytvoření tabulky zaměstnanců:

```sql
CREATE TABLE zamestnanci
(
    cislo UNSIGNED INT NOT NULL PRIMARY KEY,
    jmeno VARCHAR(100) NOT NULL,
    prijmeni VARCHAR(100) NOT NULL,
    mesto VARCHAR(100) NOT NULL
)
```

Smazání tabulky zaměstnanců:

```sql
DROP TABLE zamestnanci
```

Přidání sloupce s datem narození:

```sql
ALTER TABLE zamestnanci ADD narozen DATETIME NOT NULL
```

Odebrání sloupce s datem narození:

```sql
ALTER TABLE zamestnanci DROP narozen
```

Vytvoření cizí klíče ukazujícího směrem od zaměstnanců k oddělení.

```sql
ALTER TABLE zamestnanci ADD CONSTRAINT fk_kde_pracuje FOREIGN KEY (fk_id_oddeleni) REFERENCES oddeleni(id);
```

Smazání cizího klíče.

```sql
ALTER TABLE zamestnanci DROP CONSTRAINT fk_kde_pracuje;
```

#### DML (data manipulation language)

Vybrat zaměstnance z Prahy:

```sql
SELECT jmeno,prijmeni
FROM zamestnanci
WHERE (mesto='Praha')
ORDER BY prijmeni,jmeno
```

Vybrat 10 zaměstnanců s nejvyšším platem:

```sql
SELECT jmeno,prijmeni
FROM zamestnanci
ORDER BY plat DESC,prijmeni,jmeno
LIMIT 10
```

Vypočítat průměrné platy v jednotlivých městech:

```sql
SELECT mesto,AVG(plat)
FROM zamestnanci
GROUP BY mesto
ORDER BY mesto
```

Vypočítat celkový plat všech managerů:

```sql
SELECT SUM(plat)
FROM zamestnanci
WHERE pozice='manager'
```

Získat počet uklízeček v Ostravě:

```sql
SELECT COUNT(*)
FROM zamestnanci
WHERE (pozice='uklízečka' AND mesto='Ostrava')
```

Vybrat stránky včetně názvu kategorie (vnitřní spojení):

```sql
SELECT s.titulek,s.text,k.nazev
FROM stranky s
INNER JOIN kategorie k ON (k.id=s.idKategorie)
ORDER BY s.titulek
```

Vybrat stránky (i nezařazené) včetně názvu kategorie (levé vnější spojení):

```sql
SELECT s.titulek,s.text,k.nazev
FROM stranky s
LEFT OUTER JOIN kategorie k ON (k.id=s.idKategorie)
ORDER BY s.titulek
```

Započítat návštěvu stránky:

```sql
UPDATE stranky
SET pocitadlo=pocitadlo+1,posledniNavsteva=NOW()
WHERE url='produkty'
```

Vložit rezervaci v kině:

```sql
INSERT INTO rezervace
(film,misto,jmeno)
VALUES
('Terminátor','A44','Sarah Connor')
```

Smazat všechny horrory z databáze filmů:

```sql
DELETE
FROM filmy
WHERE (zanr='horror')
```

#### DCL (data control language)

Udělení práv pro čtení tabulky zaměstnanců uživateli "guest":

```sql
GRANT SELECT
ON zamestnanci
TO guest
```

Zrušení práva číst tabulku zaměstnanců uživateli "enemy":

```sql
REVOKE SELECT
ON zamestnanci
FROM enemy
```

Vytvoření databáze a udělení všech práv novému uživatel (v konzoli *psql*):

```sql
CREATE USER jmeno WITH PASSWORD 'heslo';
CREATE DATABASE databaze;
GRANT ALL PRIVILEGES ON DATABASE databaze TO jmeno;
```

#### TCL (transactional control language)

Transakce jsou posloupnosti příkazů, splňující tzv. podmínky *ACID*, což jsou:

- *A* (**atomicity**) - Všechny příkazy v jedné transakci musí proběhnout atomicky, tedy buď všechny, nebo žádný. Selže-li jeden z příkazů, selže celá transakce a všechny změny způsobené částečně provedenou transakcí se musí zrušit.
- *C* (**consistency**) - Transakce musí po svém dokončení ponechat data v konzistentním stavu (např. integritní omezení, cizí klíče, atd.).
- *I* (**independence**) - Dvě transakce spuštěné ve stejný čas musí ponechat databázi ve stejném stavu, jako kdyby byly spuštěny po jedné za sebou. Tato podmínka se asi nejčastěji do nějaké míry porušuje, a to kvůli výkonnosti.
- *D* (**durability**) - Po úspěšném dokončení transakce musí být všechny změny trvale uloženy, aby například nebyly ztraceny při výpadku napájení, atd.

Převod peněz z účtu na účet:

```sql
UPDATE ucty SET zustatek=zustatek-200 WHERE cislo=123142;
UPDATE ucty SET zustatek=zustatek+200 WHERE cislo=552331;
COMMIT;
-- nebo ROLLBACK;
```

Pozor na automatické potvrzování (auto-commit), které je na některých databázích a klientech ve výchozím nastavení zapnuto. Toto nastavení způsobí, že za každým příkazem následuje jeho potvrzení (commit), což tvorbu transakcí znemožňuje.

### Standard SQL-92

- http://www.contrib.andrew.cmu.edu/~shadow/sql/sql1992.txt
- datové typy: CHARACTER, CHARACTER VARYING, BIT, BIT VARYING, NUMERIC, DECIMAL, INTEGER, SMALLINT, FLOAT, REAL, DOUBLE PRECISION, DATE, TIME, TIMESTAMP, INTERVAL

### Reference

- předmět X36DBS na FEL ČVUT
- předmět X36DB2 na FEL ČVUT
- předmět X36SQL na FEL ČVUT
- materiály pro přípravu ke státnicím na MFF UK
- http://blog.sqlauthority.com/2008/01/15/sql-server-what-is-dml-ddl-dcl-and-tcl-introduction-and-examples/