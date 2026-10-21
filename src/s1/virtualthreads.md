# Virtual Threads vs Platform Threads: exemple `MultiplicaLLista`

Aquest document compara l'execució de 10.000 tasques `Multiplicacio` (cadascuna amb
un `Thread.sleep` aleatori entre 1000 i 3000 ms, simulant una espera d'E/S) segons
el tipus i la quantitat de fils utilitzats.

## El codi

```java
// Multiplicacio.call()
Thread.sleep((int)(Math.random()*2000) + 1000); // espera entre 1000 i 3000 ms
```

## Per què els sleeps bloquejants i el nombre de fils importen

Amb un `ExecutorService` de fils de plataforma (`newFixedThreadPool(n)`), cada fil
és un fil real del sistema operatiu. Quan una tasca fa `Thread.sleep(...)`, el fil
que l'executa queda **completament bloquejat** fins que acaba l'espera — no pot
atendre cap altra tasca mentrestant.

Això fa que, amb `n` fils i `T` tasques, calgui processar-les per tandes:

```
tandes ≈ T / n
temps total ≈ tandes × mitjana_sleep
```

On la mitjana del sleep (entre 1000 i 3000 ms) és **2000 ms**.

## Cas 1: `newFixedThreadPool(3)` — 3 fils de plataforma

```
tandes  = 10.000 / 3 ≈ 3.334
temps   ≈ 3.334 × 2000 ms ≈ 6.668.000 ms ≈ 6.668 s ≈ 1 h 51 min
```

Amb només 3 fils reals, la majoria de les 10.000 tasques han d'esperar que es
buidi un fil abans de començar. El cost és enorme perquè els fils estan parats
"sense fer res" durant l'espera.

## Cas 2: `newFixedThreadPool(300)` — 300 fils de plataforma

```
tandes  = 10.000 / 300 ≈ 33,3
temps   ≈ 33,3 × 2000 ms ≈ 66.667 ms ≈ 67 s
```

Millora molt respecte a 3 fils, però:
- Cada fil de plataforma reserva memòria de pila real (per defecte ~1 MB).
- 300 fils simultanis ja comencen a ser un recurs car de gestionar pel SO.
- Encara seguim limitats: per arribar al rendiment dels virtual threads caldrien
  milers de fils de plataforma, cosa inviable (consum de memòria i canvis de
  context excessius).

## Cas 3: `Executors.newVirtualThreadPerTaskExecutor()` — Virtual Threads

```
Resultat real d'execució:
Executades 10000 tasques amb virtual threads
ha trigat 3037 milisegons
```

Amb virtual threads, **cada tasca té el seu propi fil virtual** (es poden crear
desenes de milers sense problema, perquè són lleugers i gestionats per la JVM,
no pel SO). Quan un virtual thread fa `sleep()`, la JVM el "desmunta" del fil de
plataforma subjacent (carrier thread) i allibera aquest fil real per atendre
altres virtual threads mentre dura l'espera.

Resultat: totes les 10.000 tasques esperen **en paral·lel**, de manera que el
temps total no és la suma ni depèn del nombre de tandes, sinó aproximadament el
**sleep més llarg** de totes les tasques (el màxim, proper als 3000 ms), més una
mica d'overhead de scheduling.

```
temps ≈ max(sleeps individuals) ≈ ~3000 ms  →  observat: 3037 ms
```

## Resum comparatiu

| Configuració                          | Fils reals | Temps total aproximat |
|---------------------------------------|-----------:|-----------------------:|
| `newFixedThreadPool(3)`                | 3          | ~6.668.000 ms (~1h51m) |
| `newFixedThreadPool(300)`              | 300        | ~66.667 ms (~67 s)     |
| `newVirtualThreadPerTaskExecutor()`    | ~1 per tasca (virtual) | ~3.037 ms |

## Conclusió

Quan les tasques passen la major part del temps **esperant** (E/S, xarxa, sleep)
i no fent càlcul intensiu de CPU, augmentar el nombre de fils de plataforma
millora el rendiment però amb un límit pràctic marcat pels recursos del SO
(memòria, canvis de context). Els virtual threads eliminen aquesta limitació:
permeten crear tants "fils" com tasques calguin, de manera que el temps
d'execució s'aproxima al de la tasca individual més lenta, independentment del
nombre total de tasques.
