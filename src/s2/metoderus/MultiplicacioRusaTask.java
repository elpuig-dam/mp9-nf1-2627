package s2.metoderus;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MultiplicacioRusaTask extends RecursiveTask<Long> {

    private long a, b;

    public MultiplicacioRusaTask(long a, long b) {
        this.a = a;
        this.b = b;
    }

    public long multiplicacioRusa() {
        long resultat = 0;
        System.out.println("Interativa de " + a + " * " + b);
        for(int i = 1; i <= a; i++) {
            resultat += b;
        }
        return resultat;
    }

    public long multiplicacioRusaRecursiva() {
        MultiplicacioRusaTask task1;
        MultiplicacioRusaTask task2;
        System.out.println("Recursiva de " + a + " * " + b);

        if (a % 2 != 0) {
            task1 = new MultiplicacioRusaTask(a / 2, b + b);
            task1.fork();
            return b + task1.join();
        } else {
            task2 = new MultiplicacioRusaTask(a / 2, b + b);
            task2.fork();
            return task2.join();
        }
    }

    @Override
    protected Long compute() {
        if(a < 1000) return multiplicacioRusa();
        else return multiplicacioRusaRecursiva();
    }

    static void main() {
        MultiplicacioRusaTask task = new MultiplicacioRusaTask(2500, 15);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        long res = task.join();
        System.out.println(res);
    }
}
