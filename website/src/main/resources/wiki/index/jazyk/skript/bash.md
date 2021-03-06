## BASH

Bash (Bourne Again SHell) je široce rozšířený interpretr jazyka příkazové řádky kompatibilní se standardem SH (IEEE 1003.1). Nabízí však nějaké funkce navíc a je natolik populární, že pro většinu lidí je *bash* to samé jako *sh*.

### Základní příkazy

Zobrazení dokumentace k příkazu *COMMAND* (ukončí se stisknutím *q*):

```bash
man COMMAND
```

Výpis umístění aktuálního adresáře:

```bash
pwd
```

Přechod do zadaného adresáře:

```bash
cd Documents
cd /home/voho/Documents
cd ..
```

Výpis obsahu aktuálního adresáře:

```bash
ls
ls -l
ls -lha
```

Zobrazení obsahu souboru (ukončení písmenem *q*):

```bash
less FILE
```

V tomto programu můžete stisknout *Shift+F* pro automatické skrolování (vhodné např. pro sledování logů). Režim se ukončuje stisknutím *Ctrl+C*.

### Správa souborů

Vytvoření nového souboru:

```bash
touch FILE
```

Vytvoření nového adresáře:

```bash
mkdir PATH
# verbose + create all missing parents
mkdir -pv PATH
```

Přesun souboru:

```bash
mv SOURCE TARGET
```

Kopírování souboru:

```bash
cp SOURCE TARGET
```

Mazání souboru:

```bash
rm -f FILE
```

Mazání prázdného adresáře:

```bash
rmdir FILE
```

Mazání prázdného či neprázdného adresáře:

```bash
rm -r FILE
```

Hledání určitého textu v souborech pomocí regulárního výrazu:

```bash
grep 'result: OK' *.log
# alternative using pipes
cat something.log | grep 'result: OK' 
```

Opakování příkazu každých 5 sekund:

```bash
while sleep 5; do echo 'Hello!'; done
```

### Správa systému

Výpis procesů:

```bash
ps
ps -u USER
top
htop
```

Ukončení procesu podle PID:

```bash
kill PID
kill PID -9
```

Výpis programu poslouchajícího na určitém portu:

```bash
lsof -i :8080
nc -vz 127.0.0.1 8080
```

Vypnutí počítače:

```bash
shutdown -s -t 1000
```

### Oprávnění souborů

Změna vlastníka souboru *FILE* na uživatele *USER*:

```bash
chown USER FILE
```

Změna práv k souboru *FILE* na *777* (všechna práva pro všechny):

```bash
chmod 777 FILE
```

Přidání execution práva k souboru *FILE*:

```bash
chmod +x FILE
```

### Nástroje

#### Screen

Vytvoření screenu:

```bash
screen
screen COMMAND
```

Opuštění (detach) screenu: 

*Ctrl+A* a posléze *D*

Smazání screenu:

*Ctrl+A* a posléze *Q*

Listování aktivních screenů (spoulu se jejich identifikátory):

```bash
screen -ls
```

Vstoupení (re-attach) do screenu:

```bash
screen -r SESSION
```

### Tvorba složitějších skriptů

Pokud se nějaké úlohy často opakují nebo jsou složité, je lepší je uložit do samostatného souboru, který lze kdykoliv spustit. Soubory s bash skripty mají zpravidla koncovku *.sh*.

První řádek souboru může obsahovat tzv. **hashbang**, což je sekvence znaků, která operačnímu systému sděluje, v jakém interpretru má zadaný soubor spustit. Sekvence znaků začíná symboly *!#*, za kterými následuje absolutní **cesta k interpretu**. Pokud je tato sekvence přítomná, operační systém spustí zadaný interpretr a předá mu cestu ke zdrojovému souboru jako první argument. Přidávání tohoto řádku se doporučuje, aby bylo jasně vidět, pro který interpretr je skript určen. V našem případě to bude *bash*, takže první řádek bude vypadat takto:

```bash
#!/bin/bash
```

#### Proměnné

Proměnné v jazyce BASH nemají žádný datový typ. Deklarují se takto:

```bash
URL='http://voho.cz/'
NAME=42
```

Pozor zejména na to, že mezi názvem proměnné a hodnotou **nesmí být mezera**.

Proměnné lze použít například pro deklaraci dalších proměnných, jako součást řetězce nebo jako parametry příkazů:

```bash
PORT=8080
SERVER='localhost'
URL="http://$SERVER:$PORT/some/path"
```

#### Podmínky

!TODO!

#### Cykly

!TODO!

#### Podprogramy

K argumentům se podprogram dostane pomocí speciálních proměnných *$1* (první argument), *$2* (druhý argument), apod.
Pokud chceme pole všech argumentů, lze použít *$@*.

```bash
print_message() {
    NAME="$1"
    echo "Hello, $NAME!"
}

print_message "world"
```

### Spouštění skriptů

Skripty lze spustit tímto příkazem:

```bash
bash skript.sh
```

Pokud chceme během provádění skriptu vypisovat všechny provedené příkazy, stačí přidat parametr *x*:

```bash
bash -x skript.sh
```

Pokud je skript spustitelný, stačí pro jeho spuštění napsat:

```bash
./skript.sh
```

Pokud se při vykonávání skriptu narazí na chybu, nepovedený příkaz se přeskočí a skript pokračuje dál. To není vždy požadované chování. Pokud chceme, aby skript v případě první chyby skončil celý, je nutné na jeho začátek vložit tento příkaz:

```bash
set -e
```

Deaktivace se provede plusem (podobně i pro všechny následující příznaky):

```bash
set +x
```

Další doporučená nastavení pro zvýšení robustnosti skriptu:

```bash
# causes the shell to treat unset variables as an error and exit immediatelly
set -u
# causes the shell to look at exit codes of all commands in a pipeline, not just the last
set -o pipefail
# print every command
set -x
```

Pokud takto chceme kontrolovat pouze některé příkazy, lze použít následující konstrukci, která využivá toho, že výsledek provedení posledního příkazu je vždy uložen v systémové proměnné *$?*:

```bash
command

if [ $? -eq 0 ]
then
    echo "OK"
else
    echo "ERROR"
fi
```

### Příklady

#### Čtení ze souboru (řádek po řádku)

```bash
while read line
do
  echo  $line
done < file.csv
```

#### Generování CSV

```bash
X=10
Y=50

echo "x,y"
for IX in $(seq 1 $X); do
  for IY in $(seq 1 $Y); do
    echo "$X,$Y"
  done
done
```

#### Seznam otevřených portů pro java

```bash
sudo netstat -plnt | grep 'java'
```

#### Parametry určitého procesu

```bash
ps -fp 125788
```

```bash
cat /proc/125788/cmdline
```

#### Simulace selhání externího skriptu

```bash
sh -c 'exit 1'
```

#### Spuštění příkazu v určitém adresáři

```bash
(cd /some/dir && script)
```

#### Přesměrování standardních výstupů

```bash
script >> stdout.txt 2>> stderr.txt
```

### Reference

- http://linux.die.net/man/1/bash
- https://vaneyckt.io/posts/safer_bash_scripts_with_set_euxo_pipefail/
- http://stackoverflow.com/questions/5725296/difference-between-sh-and-bash
