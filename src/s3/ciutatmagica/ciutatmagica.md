# Exercici: La ciutat màgica

## Enunciat

En una ciutat màgica hi ha una torre encantada que només es pot obrir amb
una **clau**: aquesta clau és el recurs compartit, i només es torna
disponible quan es compleix una profecia.

Es comporten de manera repetitiva i independent (simular-ho amb un
`Thread`):

* La **profecia**, que comprova periòdicament si s'ha complert: això passa
  quan la suma de les xifres del dia, el mes i l'any actual coincideix amb
  la suma de les xifres de l'hora, el minut i el segon del moment (es pot
  simular aquesta comprovació amb un nombre aleatori). Mentre no es
  compleixi, torna a comprovar-ho al cap d'una estona.
* El **guardià**, que espera que la clau estigui disponible; un cop ho
  està, l'agafa i entra a la torre.

Es demana implementar aquesta simulació seguint l'estructura de
**monitor** vista a classe:

* Una classe `Clau` que actuï com a **monitor**, amb (com a mínim) un
  mètode `comprovarProfecia()` que calculi un nombre aleatori i, si la
  profecia es compleix, marqui l'estat intern i avisi amb `notifyAll()`, i
  un mètode `obrir()` que bloquegi el guardià fins que la profecia s'hagi
  complert. Cap d'aquests mètodes ha de retornar cap valor.
* Una classe `Profecia` que estengui `Thread` i que, en un bucle, vagi
  cridant `comprovarProfecia()` fins que es compleixi.
* Una classe `Guardia` que estengui `Thread` i que esperi la clau per
  entrar a la torre.
* Una classe `MainCiutatMagica` amb el mètode `main` que creï la `Clau`,
  la `Profecia` i el `Guardia`, i els posi en marxa.

## Requisits a complir

1. **Exclusió mútua / sincronització**: l'accés a l'estat de la clau ha
   d'estar controlat amb `synchronized`.
2. **Condició d'espera**: el guardià no pot entrar a la torre fins que la
   profecia no s'hagi complert.
3. **No interbloqueig (deadlock)**: el sistema no es pot quedar mai
   bloquejat sense que el guardià pugui avançar.