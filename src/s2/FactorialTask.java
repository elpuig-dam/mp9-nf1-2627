package s2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<Long> {

    private int n;
    public FactorialTask(int n) {
        this.n = n;
    }

    private long factorialR() {
        System.out.println("recursiu " + (n-1));
        FactorialTask f = new FactorialTask(n-1);
        f.fork();
        return f.join() * n;

    }

    private long factorialS() {
        System.out.println("seq: " + (n-1));
        long temp = 1;
        for (int i = 1; i <= n; i++) {
            temp *= i;
        }
        return temp;
    }

    @Override
    protected Long compute() {
        if(n < 10) {
            return factorialS();
        }else {
            return factorialR();
        }
    }

    public static void main(String[] args) {
        FactorialTask factorialTask = new FactorialTask(12);
        ForkJoinPool pool = new ForkJoinPool();
        Long resultat = pool.invoke(factorialTask);
        resultat = factorialTask.join();
        System.out.println(resultat);

    }
}
