package s2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FibonacciTask extends RecursiveTask<Long> {
    private int n;
    private static int LLINDAR = 20;


    public FibonacciTask(int n) {
        this.n = n;
    }

    public Long FibonacciR() {
        FibonacciTask f1 = new FibonacciTask(n-1);
        FibonacciTask f2 = new FibonacciTask(n-2);
        invokeAll(f1,f2);
        return f1.join() + f2.join();
    }

    public Long FibonacciS() {
        long prev1=0, prev2=1;
        for(int i=0; i<n; i++) {
            long savePrev1 = prev1;
            prev1 = prev2;
            prev2 = savePrev1 + prev2;
        }
        return prev1;
    }

    @Override
    protected Long compute() {
        if(n < LLINDAR) return FibonacciS();
        else return FibonacciR();
    }
}

class MainFib {

    public static void main(String[] args) {
        int n = 25;
        FibonacciTask task = new FibonacciTask(n);
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(task);
        Long result = task.join();
        System.out.printf("El fibonacci de %d és %d%n", n, result);


    }
}
