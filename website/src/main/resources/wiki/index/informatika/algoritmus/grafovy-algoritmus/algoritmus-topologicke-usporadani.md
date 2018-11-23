## Topologické uspořádání

Topologické uspořádání uzlů orientovaného acyklického grafu je taková posloupnost jeho uzlů, ve které se každý uzel nachází až za všemi uzly, ze kterých do něho vede hrana (pokud takové existují). Formálně řečeno, je to taková posloupnost uzlů € (v_1 \ldots v_n) €, že kdykoliv vede orientovaná hrana z € v_i € do € v_j €, platí € i < j €. Podobně lze definovat i topologické uspořádání hran grafu - hrany do uzlu vcházející v posloupnosti předchází všem hranám z uzlu vycházejícím. 

Typicky řešeným problémem je například uspořádání činností v rámci nějakého projektu. Existuje množina činností a závislostí mezi nimi, které říkají, která činnost musí být provedena před jinou (například sestavení týmu musí předcházet prvnímu zadání úkolu pro daný tým). Činnosti zde budou reprezentovány uzly a závislosti mezi nimi orientovanými hranami mezi těmito uzly. Je-li činnost *A* závislá na výstupech činnosti *B*, povede orientovaná hrana mezi těmito uzly ve směru *B-A*. Pokud je projekt proveditelný, bude výsledný graf acyklický a tudíž nad ním lze spustit algoritmus pro výpočet topologického uspořádání. Výsledné topologické uspořádání potom udává pořadí uzlů - činností, které respektuje všechna omezení zadaná závislostmi mezi nimi.

### Příklad

Nejprve zadáme binární relaci určující, jak za sebou dvojice oblečení následují.

- *pásek* před *sakem*
- *kravata* před *sakem*
- *košile* před *páskem*
- *kalhoty* před *páskem*
- *kalhoty* před *botami*
- *košile* před *kravatou*
- *trenky* před *kalhotami*
- *trenky* před *botami*
- *ponožky* před *botami*

Poté již můžeme spustit algoritmus a dostat některý z možných výsledků, například:

- košile, trenky, kalhoty, pásek, kravata, sako, ponožky, boty
- ponožky, košile, trenky, kalhoty, pásek, boty, kravata, sako
- trenky, kalhoty, ponožky, boty, košile, pásek, kravata, sako
- košile, trenky, kalhoty, pásek, ponožky, boty, kravata, sako

### Implementace (Java)

Následující implementace algoritmu dokáže detekovat cykly. Třídy *LinkedHashSet* a *LinkedHashMap* jsou použité z důvodu předvídatelnosti a testovatelnosti, výstupy by byly samozřejmě správné i s jinými implementacemi map a množin.

#### Řazení

```include:java
Toposorter.java
```

#### Použití

```java
final Toposorter<String> sorter = new Toposorter<>();
        
sorter.addDependency("pásek", "sako");
sorter.addDependency("kravata", "sako");
sorter.addDependency("košile", "pásek");
sorter.addDependency("kalhoty", "pásek");
sorter.addDependency("kalhoty", "boty");
sorter.addDependency("košile", "kravata");
sorter.addDependency("trenky", "kalhoty");
sorter.addDependency("trenky", "boty");
sorter.addDependency("ponožky", "boty");

sorter.compute();

System.out.println(sorter.getTopologicalOrder());
```

### Složitost

Asymptotická složitost je lineárně závislá na počtu hran a uzlů, tedy € O(|V|+|E|) €.

### Reference

- http://homepages.ius.edu/rwisman/C455/html/notes/Chapter22/TopSort.htm
- http://homel.vsb.cz/~nav79/ga/alg3.htm
- http://en.wikipedia.org/wiki/Topological_sorting