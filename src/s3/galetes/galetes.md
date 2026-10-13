# Exercici: El pot de galetes

## Enunciat

A casa hi ha un **pot de galetes** compartit per tota la família: el pare i
els fills. De tant en tant els fills tenen gana i volen agafar una galeta del
pot per menjar-se-la; de tant en tant, quan el pot es queda sense galetes, el
pare l'omple de nou. És el problema clàssic del **productor/consumidor**: el
pare és el productor (omple el pot) i els fills són els consumidors (en
treuen galetes).

Cada membre de la família es comporta de manera repetitiva i independent
(simular-ho amb un `Thread`):

* Cada **fill**, en un bucle infinit, agafa una galeta del pot, "se la menja"
  (esperar una estona aleatòria), deixa el pot lliure i descansa una altra
  estona aleatòria abans de tornar a tenir gana.
* El **pare**, en un bucle infinit, quan el pot es queda buit hi afegeix un
  grapat de galetes noves, deixa el pot lliure i descansa una estona
  aleatòria abans de tornar a comprovar si cal omplir-lo.

Es demana implementar aquesta simulació seguint l'estructura de **monitor**
vista a classe:

* Una classe `Pot` que actuarà com a **monitor**, és a dir, com a recurs
  compartit que controla tant l'accés exclusiu com les condicions d'espera.
  Ha d'oferir (com a mínim) els mètodes `agafaGaleta()`, `posarGaletes()` i
  `deixarPot()`, implementats amb `synchronized`, `wait()` i `notifyAll()`.
  El pot ha de recordar quantes galetes conté i si està lliure o ocupat en
  cada instant.
* Una classe `Fill` que estengui `Thread` i que representi cada fill de la
  família. Dins el seu mètode `run()`, en un bucle infinit, ha de: agafar una
  galeta del pot, "menjar-se-la" durant un temps aleatori, deixar el pot i,
  finalment, esperar un altre temps aleatori abans de tornar a tenir gana.
* Una classe `Pare` que estengui `Thread` i que representi el pare. Dins el
  seu mètode `run()`, en un bucle infinit, ha de: omplir el pot de galetes
  quan estigui buit, deixar-lo i esperar un temps aleatori abans de tornar a
  comprovar-lo.
* Una classe `MainPotGaletes` amb el mètode `main` que creï un únic objecte
  `Pot` i diversos objectes `Fill` i un objecte `Pare` que comparteixin
  aquest pot, i els posi en marxa.

## Requisits a complir

La solució ha de garantir:

1. **Exclusió mútua / sincronització**: en cap moment dos fils de la
   família (ni el pare alhora que un fill) poden accedir al pot
   simultàniament; només un membre el pot tenir "obert" en cada instant.
2. **Condició de consum**: un fill no pot agafar una galeta si el pot està
   buit; ha d'esperar que el pare l'ompli.
3. **Condició de producció**: el pare no hauria d'afegir galetes si encara
   en queden (o, segons el disseny triat, només quan el pot es buida del
   tot), per evitar que el pot creixi sense límit.
4. **No interbloqueig (deadlock)**: el sistema no es pot quedar mai
   bloquejat de manera que cap fill ni el pare puguin avançar.
5. **No inanició (starvation)**: cap fill pot quedar-se esperant
   indefinidament una galeta mentre el pare va omplint el pot
   contínuament; a la llarga, tots els fills han de poder menjar.

## Pistes

* Fixeu-vos en com es gestiona alhora l'estat de "lliure/ocupat" del pot
  (exclusió mútua, igual que al comandament) i el nombre de galetes que
  conté (condició de consum/producció); calen dues condicions diferents
  dins del mateix monitor.
* Per mostrar que l'exercici funciona correctament, cal imprimir per
  pantalla, com a mínim, quan cada fill agafa una galeta i quantes en
  queden, i quan el pare n'afegeix i quantes n'hi ha en total.
