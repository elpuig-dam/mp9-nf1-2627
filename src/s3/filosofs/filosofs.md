# Exercici: El sopar dels filòsofs

## Enunciat

Un grup de filòsofs sopen junts asseguts al voltant d'una taula rodona. Entre
cada dos filòsofs hi ha un únic **cobert**, de manera que cada
filòsof té un cobert a la seva esquerra i un altre a la seva dreta, però
aquests coberts són **compartits** amb els filòsofs del costat. Per poder
menjar, un filòsof necessita agafar els **dos coberts** (l'esquerre i el
dret) alhora; sinó, no pot menjar.

Cada filòsof es comporta de manera repetitiva i independent (simular-ho amb
un `Thread`): alterna entre **pensar** (una estona) i **menjar** (una altra
estona). Per menjar, ha d'agafar primer un cobert, després l'altre, menjar
durant un temps aleatori i, finalment, deixar els dos coberts lliures abans
de tornar a pensar.

Es demana implementar aquesta simulació seguint l'estructura de **monitor**
vista a classe:

* Una classe `Cobert` que actuarà com a **monitor**, és a dir, com a recurs
  compartit que controla l'accés exclusiu. Ha d'oferir (com a mínim) els
  mètodes `agafar()` i `deixar()`, implementats amb `synchronized`, `wait()`
  i `notifyAll()`, i ha de recordar si està lliure o ocupat.
* Una classe `Filosof` que estengui `Thread` i que representi cada
  filòsof. Dins el seu mètode `run()`, en un bucle infinit, ha de: agafar el
  seu primer cobert, agafar el seu segon cobert, "menjar" durant un temps
  aleatori, deixar els dos coberts i, finalment, "pensar" durant un altre
  temps aleatori.
* Una classe `SoparFilosofs` amb el mètode `main` que creï tants objectes
  `Cobert` com filòsofs hi hagi a la taula (cada cobert compartit entre dos
  filòsofs veïns) i diversos objectes `Filosof`, cadascun amb el seu
  cobert esquerre i dret assignat, i els posi en marxa.

## Advertència: perill de deadlock

Aquest problema és l'exemple clàssic d'**interbloqueig (deadlock)**: si tots
els filòsofs agafen alhora el seu primer cobert (per exemple, tots agafen
primer el de la seva esquerra) es pot donar el cas que cadascun es quedi
esperant indefinidament el segon cobert, que està en mans del seu veí, que
al seu torn espera el seu. Ningú no pot avançar i el sistema queda
bloquejat per sempre.

**Es recomana, abans de corregir-ho, implementar primer una versió
"ingènua"** en què cada filòsof simplement agafa el seu primer cobert i,
tot seguit, el segon (sense cap mecanisme addicional de protecció), i
comprovar **experimentalment** (executant-ho diverses vegades, amb
`Thread.sleep()` en punts estratègics si cal per forçar la coincidència)
que efectivament el programa es pot quedar penjat (cap filòsof imprimeix
més missatges per pantalla). Un cop vist el problema, cal **corregir-ho**
afegint algun mecanisme que trenqui la condició circular d'espera (per
exemple, limitant quants filòsofs poden intentar agafar coberts
simultàniament, o fent que algun filòsof agafi els coberts en ordre
invers), i tornar a comprovar que, ara sí, el sopar avança sense quedar-se
mai bloquejat.

## Requisits a complir

La solució ha de garantir:

1. **Exclusió mútua / sincronització**: en cap moment dos filòsofs poden
   tenir el mateix cobert agafat alhora.
2. **No interbloqueig (deadlock)**: el sistema no es pot quedar mai
   bloquejat de manera que cap filòsof pugui avançar (vegeu l'advertència
   anterior).
3. **No inanició (starvation)**: cap filòsof pot quedar-se esperant
   indefinidament els seus coberts mentre els altres mengen contínuament;
   a la llarga, tots els filòsofs han de poder menjar.

## Pistes

* Fixeu-vos en com es gestiona l'estat de "lliure/ocupat" de cada cobert
  (igual que al comandament o al pot de galetes) i en com s'assignen els
  coberts esquerre i dret a cada filòsof segons la seva posició a la taula.
* Per mostrar que l'exercici funciona correctament (i per detectar un
  possible deadlock), cal imprimir per pantalla, com a mínim, quan cada
  filòsof agafa el primer cobert, quan agafa el segon, quan comença a
  menjar i quan comença a pensar. 

