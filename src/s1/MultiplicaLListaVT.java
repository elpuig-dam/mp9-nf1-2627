package s1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Mateix exercici que MultiplicaLLista, però amb Virtual Threads.
 * Cada Multiplicacio "bloqueja" (Thread.sleep) simulant una espera d'E/S,
 * que és exactament el cas en què els virtual threads brillen: es pot
 * llançar una tasca per fil virtual (milers d'ells) sense exhaurir
 * els fils de plataforma del sistema operatiu.
 */
public class MultiplicaLListaVT {
    public static void main(String[] args) throws InterruptedException {

        // Un fil virtual per tasca, en lloc d'un pool fix de 3 fils de plataforma.
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            List<Callable<Integer>> llistaTasques = new ArrayList<>();
            for (int i = 0; i < 10_000; i++) {
                Multiplicacio calcula = new Multiplicacio((int) (Math.random() * 10), (int) (Math.random() * 10));
                llistaTasques.add(calcula);
            }

            long ti = System.currentTimeMillis();
            List<Future<Integer>> llistaResultats = executor.invokeAll(llistaTasques);
            long tf = System.currentTimeMillis();

            long sumaOk = 0;
            for (Future<Integer> resultat : llistaResultats) {
                try {
                    sumaOk += resultat.get();
                } catch (InterruptedException | ExecutionException e) {
                }
            }

            System.out.println("Executades " + llistaResultats.size() + " tasques amb virtual threads");
            System.out.println("ha trigat " + (tf - ti) + " milisegons");
        }
        // try-with-resources tanca automàticament l'executor (shutdown implícit)
    }
}
