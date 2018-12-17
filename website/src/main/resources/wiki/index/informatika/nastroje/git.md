## Git

> In many ways you can just see git as a filesystem — it's content-addressable, and it has a notion of versioning, but I really really designed it coming at the problem from the viewpoint of a filesystem person (hey, kernels is what I do), and I actually have absolutely zero interest in creating a traditional SCM system. *Linus Torvalds*

Git je decentralizovaný systém pro správu verzí (SCM), za kterým stojí Linux Torvalds. Používá se například při vývoji [Linuxového jádra](https://www.kernel.org/) a operačního systému Google Android. Samotný název "git" je britský slang, označující hloupou nebo nepříjemnou osobu. Torvalds často vtipkuje, že i tento software pojmenoval podle sebe (podobně jako Linux).

### Online tutorialy

Vynikající online tutoriály naleznete zde: 

- http://try.github.com/levels/1/challenges/1
- https://www.atlassian.com/git

### Konfigurace GIT

#### Generování SSH klíčů

Příkaz pro vygenerování výchozího SSH klíče s bitovou délkou 4096:

```bash
ssh-keygen -b 4096
```

Po spuštění vygeneruje privátní a veřejný klíč v adresáři *HOME/.ssh/*. Ve výchozím nastavení vzniknou tyto dva soubory:

- *id_rsa* = privátní klíč (ten nikdy nesmíte nikomu dát)
- *id_rsa.pub* = veřejný klíč (ten si můžete klidně umístit na web)

Váš veřejný klíč je **celý obsah** souboru *id_rsa.pub*.

#### Nastavení jména a hesla

```bash
git config --global user.name "John Doe"
git config --global user.email johndoe@example.com
```

#### Nastavení editorů

```bash
git config --global core.editor vim
git config --global merge.tool vimdiff
```

#### Výpis nastavení

```bash
git config --listbash
```

### Práce s lokálním repositářem

#### Vytvoření repositáře

Založení nového repositáře v aktuálním adresáři (adresář nemusí být prázdný):

```bash
git init
```

Klonování již existujícího repositáře:

```bash
git clone URL
```

Příklady URL:

- **HTTPS**: https://github.com/COMPANY/PROJECT.git
- **SSH**: git@github.com:COMPANY/PROJECT.git

#### Přidávání změn do commitů

Po změně lze soubory vybrat k zařazení do commitu:

```bash
git add *.java
git add .
```

Nebo je lze z commitu odebrat:

```bash
git rm *.txt
```

#### Potvrzení commitu

Jakmile je commit připraven, lze jej potvrdit:

```bash
git commit -m "commit message"
```

Všechny předchozí operace lze zjednodušit - přidání/odebrání všech změn a zároveň potvrzení commitu lze provést i jedním příkazem:

```bash
git commit -am "commit message"
```

#### Reset

Pokud se chcete vrátit v historii zpět, lze použít příkaz *reset*. Ten má několik základních variant:

- **soft** = pouze přesune ukazatel HEAD, ponechá index i soubory
- **mixed** (výchozí) = přesune ukazatel HEAD, změní index a ponechá soubory
- **hard** = přesune ukazatel HEAD, změní index i soubory

```bash
git reset REVISION
git reset --soft REVISION
git reset --hard REVISION
```

#### Merge

!TODO!

#### Rebase

!TODO!

Existuje i tzv. **interaktivní rebase**, který umožní pomocí textového editoru upravit pořadí commitů, jejich commit message, případně některé commity "splácnout" do jednoho většího. Spouští se parametrem *-i*.

### Práce se vzdáleným repositářem

Připojení vzdáleného repositáře na adrese *URL* a jeho uložení pod názvem *NAME*:

```bash
git remote add NAME URL
```

Odpojení vzdáleného repositáře s názvem *NAME*:

```bash
git remote remove NAME
```

#### Odeslání commitů

K odeslání lokálních změn do vzdáleného repositáře slouží příkaz *push*.

```bash
# odešle místní větev "master" do vzdáleného repositáře "origin" a stejnojmenné vzdálené větve "master"
git push origin master

# odešle místní větev "master" do vzdáleného repositáře "origin" a vzdálené větve "ticket154"
git push origin master:ticket154

# přepíše celou vzdálenou větev "master" lokální větví "master"
git push origin master -f

# odešle pouze jeden commit s hashem "hash123" do vzdálené větve "master"
git push origin hash123:master
```

### Jiné užitečné příkazy

```bash
# historie (krátký hash + zpráva z commitu)
git log --pretty=format:"%h %s" --graph

# zobrazení místní historie referencí (zde můžete najít např. hash "ztraceného" commitu)
git reflog
```

#### Tipy a triky pro commit message

- zpráva v commitu by měl být stručná zhruba v délce jedné věty
- zpráva v commitu může mít nějaký pevný formát, například obsahující číslo lístku nebo bugu, který commit řeší
- zprávy v commitu by měly všechny být v jednom jazyce (ideálně v angličtině)
- mezi příklady nepříliš vhodných zpráv patří příliš obecné termíny, jako jsou "změny", "refactoring", "čištění", "dolaďování", apod.
- do zprávy se nepíšou informace, které lze snadno zjistit z logu - například názvy změněných souborů, jméno člověka provádějícího změny, atd.
- někteří lidé preferují použití rozkazujícího způsobu

### Způsob práce

#### Centralizované úložiště

- detaily: https://www.atlassian.com/git/tutorials/comparing-workflows/centralized-workflow

#### Gitflow

- detaily: https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow

Připojení vzdáleného repositáře na adrese *URL* a jeho pojmenování jako **upstream**:

```bash
git remote add upstream URI
```

Přepnutí do větve **develop**:

```bash
# pokud větev neexistuje
git checkout -b develop

# pokud větev již existuje
git checkout develop
```

Načtení změn ze vzdáleného repositáře:

```bash
git fetch upstream
```

Provedení změn v lokálním repositáři:

```bash
git rebase upstream/develop
```

Propagace lokálních úpravy zpět do vzdáleného repositáře:

```bash
git push origin develop
```

Pokud byla změněna lokální historie commitů (např. interaktivní rebase), je nutné přidat parametr *-f* (ale pozor, tato operace může vést k velkým problémům, pokud nevíte, co děláte):

```bash
git push origin develop -f
```

Při release se požadované změny z větve **develop** zamergují do větve **master** a z větve **master** se provede release:

```bash
git checkout master
git merge develop
```

### Reference

- http://git-scm.com/
- http://github.com/
- http://bitbucket.org/
- https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow
- http://git-scm.com/book/en/v2/Getting-Started-Git-Basics
- http://www.sbf5.com/~cduan/technical/git/git-1.shtml
- http://eagain.net/articles/git-for-computer-scientists/