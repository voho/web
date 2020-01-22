## Fork-Join pool

Fork-Join pool je konstrukt vhodný pro řešení problémů, které se rozpadají na různé podúlohy, jejichž dílčí řešení jsou opět spojovány.
Rozpadnutí označujeme jako **fork**, spojení jako **join**.

Pool si ukážeme na jednoduchém příkladu, ve kterém je naším cílem sečíst seznam čísel.

```include:java
forkjoin/LargeSumProblem.java
```
