# Exercicis - Fork/Join amb RecursiveTask

Tots els exercicis d'aquest package s'han de resoldre utilitzant l'estructura
`RecursiveTask<T>` del framework **Fork/Join** de Java. Cada classe ha de
combinar dos mètodes de càlcul:

- Una versió **recursiva** (`fork()` / `invokeAll()` + `join()`), que divideix
  el problema en subtasques.
- Una versió **seqüencial/iterativa**, que resol el problema directament quan
  la mida ja és prou petita.

El mètode `compute()` ha de decidir, segons un **llindar** (threshold), quina
de les dues versions utilitzar, per tal d'**optimitzar al màxim** el nombre de
crides recursives i evitar crear tasques innecessàries quan el cost de
gestionar-les és més gran que el benefici de paral·lelitzar.

---

## 1. Factorial (`FactorialTask`)

Implementa el càlcul del **factorial d'un nombre `n`** mitjançant una classe
`FactorialTask` que estengui `RecursiveTask<Long>`.

- Si `n` és menor que un cert llindar, calcula el factorial de forma
  **iterativa** (seqüencial).
- Si `n` és igual o més gran que el llindar, calcula'l de forma **recursiva**,
  creant una nova tasca (`fork`/`join`) per calcular `(n-1)!` i multiplicant
  el resultat per `n`.
- Prova el programa amb diversos valors de `n` i de llindar, i observa a la
  consola quan s'executa la versió seqüencial i quan la recursiva.

**Fórmula recursiva:**

```
factorial(n) = 1                          si n = 0
factorial(n) = n * factorial(n-1)         si n > 0
```

---

## 2. Fibonacci (`FibonacciTask`)

Implementa el càlcul del **terme n-èsim de la successió de Fibonacci**
mitjançant una classe `FibonacciTask` que estengui `RecursiveTask<Long>`.

- Defineix un `LLINDAR`: si `n` és menor que aquest valor, calcula el resultat
  de forma **iterativa** (sense recursivitat ni fork/join), recorrent la
  successió amb un bucle.
- Si `n` és igual o més gran que el llindar, crea **dues subtasques**
  (`Fibonacci(n-1)` i `Fibonacci(n-2)`), executa-les amb `invokeAll` i suma
  els resultats amb `join()`.
- Raona per què calcular Fibonacci de forma purament recursiva (sense
  llindar) seria molt ineficient, i comprova com millora el rendiment en
  afegir la versió iterativa per als valors petits de `n`.

**Fórmula recursiva:**

```
fib(n) = 0                            si n = 0
fib(n) = 1                            si n = 1
fib(n) = fib(n-1) + fib(n-2)          si n > 1
```

---

## 3. Divisió per restes successives (`DivTask`)

Implementa el càlcul del **quocient d'una divisió entera `n / d`** fent
servir només **restes successives** (sense l'operador `/`), mitjançant una
classe `DivTask` que estengui `RecursiveTask<Long>`.

- La versió **seqüencial** ha de restar `d` de `n` repetidament dins d'un
  bucle fins que `n` sigui menor que `d`, comptant el nombre de restes
  (aquest comptador és el quocient).
- La versió **recursiva** ha de restar `d` una sola vegada, crear una nova
  tasca `DivTask(n-d, d)`, fer `fork()`/`join()` i sumar 1 al resultat
  obtingut.
- Decideix un criteri de llindar (per exemple, en funció de la diferència
  `n - d`) que determini quan val la pena seguir dividint el treball en
  subtasques i quan és millor resoldre-ho de cop amb el bucle.

**Fórmula recursiva:**

```
div(n, d) = 0                        si n < d
div(n, d) = 1 + div(n-d, d)          si n >= d
```

---

## Com passar d'una fórmula recursiva a un `RecursiveTask`

Un cop tens la fórmula matemàtica del cas recursiu, convertir-la al mètode
`compute()` d'una classe `RecursiveTask` segueix sempre el mateix patró:

1. **Cada crida recursiva de la fórmula = una instància nova de la classe.**
   Si a la fórmula hi apareix `funcio(x-1)`, al codi hi haurà d'haver
   `new LaMevaTask(x-1)`.

2. **Si la fórmula té una sola crida recursiva → una sola subtasca.**
   N'hi ha prou amb `fork()` + `join()` (o directament `compute()` si no es
   vol paral·lelitzar), i aplicar l'operació final sobre el resultat.

   ```
   formula:  f(n) = n * f(n-1)

   compute():
       Task t = new Task(n-1);
       t.fork();
       return n * t.join();
   ```

3. **Si la fórmula té dues (o més) crides recursives → dues (o més)
   subtasques.** Es creen totes les instàncies necessàries i s'executen
   plegades amb `invokeAll(t1, t2, ...)`, que llança totes les tasques i
   espera que acabin; després es recullen els resultats amb `join()` de
   cadascuna i es combinen segons la fórmula.

   ```
   formula:  f(n) = f(n-1) + f(n-2)

   compute():
       Task t1 = new Task(n-1);
       Task t2 = new Task(n-2);
       invokeAll(t1, t2);
       return t1.join() + t2.join();
   ```

4. **El cas base de la fórmula no es tracta mai amb un `if` dins del
   `compute()`: el "cobreix" la versió seqüencial/iterativa.** En aquesta
   estructura amb llindar, `compute()` no arriba mai a comprovar el cas base
   real (per exemple `n = 0`), perquè abans d'arribar-hi ja s'ha activat la
   condició del llindar i s'ha cridat al mètode seqüencial. Aquest mètode
   seqüencial és el que, internament (amb un bucle), recorre tots els casos
   fins arribar al cas base i en calcula el resultat directament, sense
   crear cap subtasca.

5. **El llindar substitueix el cas base dins del `compute()`.** La fórmula
   et diu matemàticament *quan* para la recursivitat (el cas base real), però
   al codi aquella condició (`n == 0`, `b == 0`...) no s'escriu mai tal qual:
   es reemplaça per la condició del llindar (`n < LLINDAR`), que decideix
   *quan deixar de crear tasques noves* perquè, per mides petites, el cost de
   gestionar fork/join és més gran que el benefici de paral·lelitzar. Per
   això, en lloc d'un `if` que retorna el cas base, hi ha un `if` que crida
   a la versió iterativa equivalent.

**Resum pràctic:** compta quantes crides recursives diferents apareixen al
costat dret de la fórmula → aquest és el nombre d'instàncies de la classe
`RecursiveTask` que has de crear dins de `compute()` abans de combinar els
seus resultats amb `join()`.
