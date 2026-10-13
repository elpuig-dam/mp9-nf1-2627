# Exercici: El comandament a distància

## Enunciat

En una família hi ha diversos membres que conviuen a casa (per exemple el
pare, la mare, els fills i fins i tot el gat) i que, de tant en tant, volen
mirar la televisió. El problema és que només hi ha un **comandament a
distància**, i per poder-lo utilitzar i mirar la tele cal tenir-lo a les
mans.

Cada membre de la família es comporta de manera repetitiva i independent
(simular-ho amb un `Thread`): està una estona fent altres coses, després vol
mirar la tele, demana el comandament, el fa servir durant una estona i el
torna a deixar, per tornar a repetir el cicle indefinidament.

Es demana implementar aquesta simulació seguint l'estructura de **monitor**
vista a classe:

* Una classe `Comandament` que actuarà com a **monitor**, és a dir, com a
  recurs compartit que controla l'accés dels membres de la família. Ha
  d'oferir (com a mínim) els mètodes `agafar()` i `deixar()`, implementats
  amb `synchronized`, `wait()` i `notifyAll()`.
* Una classe `MembreFamilia` que estengui `Thread` i que representi cada
  membre de la família. Dins el seu mètode `run()`, en un bucle infinit, ha
  de: demanar el comandament, "mirar la tele" durant un temps aleatori,
  deixar el comandament i, finalment, esperar un altre temps aleatori abans
  de tornar a voler mirar la tele.
* Una classe `MainCom` amb el mètode `main` que creï un únic objecte
  `Comandament` i diversos objectes `MembreFamilia` (un per a cada membre de
  la família) que comparteixin aquest comandament, i els posi en marxa.

## Requisits a complir

La solució ha de garantir:

1. **Exclusió mútua / sincronització**: en cap moment dos membres de la
   família poden tenir el comandament alhora; només un membre el pot tenir
   agafat en cada instant.
2. **No interbloqueig (deadlock)**: el sistema no es pot quedar mai
   bloquejat de manera que cap membre pugui avançar.
3. **No inanició (starvation)**: cap membre de la família pot quedar-se
   esperant el comandament indefinidament mentre els altres el van agafant
   i deixant contínuament; a la llarga, tots els membres han de poder
   mirar la tele.

## Pistes

* Fixeu-vos en com es gestiona l'estat de "lliure/ocupat" del recurs
  compartit i en com es desperten els fils que esperen.
* Per mostrar que l'exercici funciona correctament, cal imprimir per
  pantalla, com a mínim, quan cada membre de la família comença i acaba de
  mirar la tele.
