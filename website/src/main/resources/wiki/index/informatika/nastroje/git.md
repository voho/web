## Git

> In many ways you can just see git as a filesystem — it's content-addressable, and it has a notion of versioning, but I really really designed it coming at the problem from the viewpoint of a filesystem person (hey, kernels is what I do), and I actually have absolutely zero interest in creating a traditional SCM system. *Linus Torvalds*

Git je decentralizovaný systém pro správu verzí (SCM), za kterým stojí Linux Torvalds. Používá se například při vývoji [Linuxového jádra](https://www.kernel.org/) a operačního systému Google Android. Samotný název "git" je britský slang, označující hloupou nebo nepříjemnou osobu. Torvalds často vtipkuje, že i tento software pojmenoval podle sebe (podobně jako Linux).

### Online tutorialy

Vynikající online tutoriály naleznete zde: 

- http://try.github.com/levels/1/challenges/1
- https://www.atlassian.com/git

### Konfigurace GIT

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
git config --list
```

### Práce s lokálním repositářem

#### Založení repositáře

Založení nového repositáře v aktuálním adresáři (adresář nemusí být prázdný):

```bash
git init
```

Klonování již existujícího repositáře:

```bash
git clone URL
```

Příklady URL:

```bash
git clone https://github.com/COMPANY/PROJECT.git
git clone git@github.com:COMPANY/PROJECT.git
```

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

![git merge](https://cms-assets.tutsplus.com/uploads/users/585/posts/23191/image/merge.png)

#### Rebase

!TODO!

![git rebase](https://cms-assets.tutsplus.com/uploads/users/585/posts/23191/image/rebase.png)

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

### Standardní pracovní procesy

#### Centralizované úložiště

Nejjednodušší proces. Všichni vývojáři neustále vyvíjejí nad hlavní větví.

#### Gitflow

Proces, ve kterém se každá nová funkce (feature) nebo oprava chyby vyvíjí v oddělené větvi. Po dokončení a otestování změny se tato větev zamerguje zpět do hlavní větve.

### Reference

- http://git-scm.com/
- http://github.com/
- http://bitbucket.org/
- https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow
- http://git-scm.com/book/en/v2/Getting-Started-Git-Basics
- http://www.sbf5.com/~cduan/technical/git/git-1.shtml
- http://eagain.net/articles/git-for-computer-scientists/
