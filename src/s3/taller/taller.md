# Exercici: El taller de costura

## Enunciat

En un petit taller de confecció es fabriquen jerseis de llana. El procés de
fabricació es reparteix entre diverses persones que treballen alhora i de
manera independent:

* Una **cosidora de mànigues** cus mànigues, una rere l'altra, i cada vegada
  que n'acaba una la diposita en un **cistell** compartit destinat a
  mànigues. Aquest cistell té una capacitat màxima (per exemple, 8
  mànigues); si està ple, la cosidora no pot deixar-hi cap més mentre algú
  no en tregui.
* Una **cosidora de cossos** fa el mateix, però cosint cossos de jersei i
  deixant-los en un altre cistell, també de capacitat limitada (per
  exemple, 5 cossos).
* Un **encadellador** (la persona que uneix les peces) va agafant peces
  dels dos cistells per muntar jerseis complets: cada jersei necessita
  **2 mànigues** i **1 cos**. Si no hi ha prou peces d'algun tipus al seu
  cistell corresponent, ha d'esperar fins que n'hi hagi; un cop les té
  totes, se les emporta, dedica una estona a encadellar-les i anuncia que
  ha acabat un jersei nou.

Cada persona del taller treballa en un bucle infinit i de manera repetitiva:
cus (o encadella) durant un temps aleatori, interacciona amb el cistell o
cistells corresponents, i torna a començar.

Es demana implementar aquesta simulació. **No se us diu explícitament quines
classes han de ser fils (`Thread`) i quines han de fer de monitor**: haureu
de decidir-ho vosaltres mateixos a partir de l'enunciat, igual que heu fet 
amb els altres problemes de sincronització vistos a
classe (el comandament, el pot de galetes, el sopar dels filòsofs).
Pregunteu-vos:

* Quines "entitats" del problema actuen de manera autònoma i repetitiva,
  fent coses pel seu compte sense que ningú els ho demani cada vegada?
* Quin element és simplement un **recurs compartit** pel qual diverses
  entitats es disputen l'accés, i que per tant ha de controlar ell mateix
  qui hi pot accedir i quan?
* Quants recursos compartits diferents calen, i quina capacitat té
  cadascun?
* Quina informació ha de recordar cada recurs compartit (per exemple,
  quantes peces conté en cada moment, o si està lliure o ocupat)?

A més de les classes que considereu necessàries, caldrà una classe amb el
mètode `main` que creï els objectes necessaris (cistells, cosidores,
encadellador) i els posi en marxa.

## Requisits a complir

La solució ha de garantir:

1. **Exclusió mútua / sincronització**: en cap moment dues persones del
   taller poden modificar el mateix cistell alhora de manera
   descontrolada.
2. **Respecte de la capacitat**: cap cistell pot superar mai la seva
   capacitat màxima ni quedar amb un nombre negatiu de peces.
3. **No interbloqueig (deadlock)**: el sistema no es pot quedar mai
   bloquejat de manera que ningú pugui avançar.
4. **No inanició (starvation)**: ni les cosidores ni l'encadellador poden
   quedar-se esperant indefinidament; a la llarga, el taller ha d'anar
   produint jerseis de manera contínua.

## Pistes

* Fixeu-vos en problemes semblants ja treballats a classe: hi havia un
  recurs compartit amb una capacitat limitada i uns "productors" que hi
  afegien coses i uns "consumidors" que en treien. Aquí hi ha dos
  recursos d'aquest tipus, i una mateixa persona (l'encadellador) actua
  de consumidor dels dos alhora.
* Penseu què ha de passar si l'encadellador necessita peces de dos
  cistells diferents per poder continuar: en quin ordre les hauria de
  demanar?
* Per comprovar que l'exercici funciona correctament, cal imprimir per
  pantalla, com a mínim, quan cada cosidora afegeix una peça (i quantes
  n'hi ha en total al seu cistell) i quan l'encadellador completa un
  jersei nou.
