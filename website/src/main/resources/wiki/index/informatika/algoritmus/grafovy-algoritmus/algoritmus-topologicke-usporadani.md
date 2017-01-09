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

```java
private static class Toposorter<E> {
    /**
     * množina všech uzlů
     */
    private final Set<E> nodes = new LinkedHashSet<E>();
    /**
     * hrany (mapování uzlu na jeho závislosti)
     */
    private final Map<E, Set<E>> edges = new LinkedHashMap<E, Set<E>>();
    /**
     * množina navštívených uzlů
     */
    private final Set<E> visitedNodes = new LinkedHashSet<E>();
    /**
     * množina zpracovaných uzlů
     */
    private final Set<E> expandedNodes = new LinkedHashSet<E>();
    /**
     * výsledné topologické uspořádání
     */
    private final List<E> result = new LinkedList<E>();
    
    public List<E> getTopologicalOrder() {
        return Collections.unmodifiableList(this.result);
    }
    
    public void addNode(final E node) {
        this.nodes.add(node);
    }
    
    public void compute() throws CycleFoundException {
        // smazat pomocné proměnné
        
        this.visitedNodes.clear();
        this.expandedNodes.clear();
        this.result.clear();
        
        // zpracovat všechny uzly
        
        for (final E node : this.nodes) {
            this.visit(node);
        }
    }
    
    private void visit(final E node) throws CycleFoundException {
        if (this.visitedNodes.contains(node)) {
            if (this.expandedNodes.contains(node)) {
                // tento uzel je již zpracován
                
                return;
            }
            
            // na tento uzel již narážíme podruhé a stále není zpracován = cyklus
            
            throw new CycleFoundException();
        }
        
        // označit uzel jako navštívený
        
        this.visitedNodes.add(node);
        
        // projít všechny závislosti (pokud nějaké jsou)
        
        if (this.edges.containsKey(node)) {
            for (final E next : this.edges.get(node)) {
                this.visit(next);
            }
        }
        
        // označit uzel jako zpracovaný
        
        this.expandedNodes.add(node);
        
        // přidat uzel na konec výsledné posloupnosti
        // (jeho závislosti jsou již splněny)
        
        this.result.add(node);
    }
    
    public void addDependency(final E before, final E after) {
        this.addNode(before);
        this.addNode(after);
        
        if (!this.edges.containsKey(after)) {
            this.edges.put(after, new LinkedHashSet<E>());
        }
        
        this.edges.get(after).add(before);
    }
}
```

#### Použití

```java
final Toposorter<String> sorter = new Toposorter<String>();
        
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