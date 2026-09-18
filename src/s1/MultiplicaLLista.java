package s1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class MultiplicaLLista {
    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        List<Multiplicacio> llistaTasques= new ArrayList<Multiplicacio>();
        for (int i = 0; i < 10; i++) {
            Multiplicacio calcula = new Multiplicacio((int)(Math.random()*10), (int)(Math.random()*10));
            llistaTasques.add(calcula);
        }
        List <Future<Integer>> llistaResultats;

        Long ti = System.currentTimeMillis();
        llistaResultats = executor.invokeAll(llistaTasques);
        executor.shutdown();
        Long tf = System.currentTimeMillis();

        for (int i = 0; i < llistaResultats.size(); i++) {
            Future<Integer> resultat = llistaResultats.get(i);
            try {
                System.out.println("Resultat tasca "+i+ " és:" + resultat.get());
            } catch (InterruptedException | ExecutionException e) {
            }
        }
        System.out.println("ha trigat " + (tf-ti) + " milisegons");


    }
}
