package s2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class DivTask extends RecursiveTask<Long> {
    private int n;
    private int d;

    public DivTask(int n, int d) {
        this.n = n;
        this.d = d;
    }

    @Override
    protected Long compute() {
        if(n - d < 20) return divS();
        else return divR();
    }

    private long divR() {
        System.out.printf("req %d / %d \n", n,d );
        DivTask task = new DivTask(n-d, d);
        task.fork();
        return task.join() + 1;

    }

    private long divS() {
        System.out.printf("sequencial %d / %d \n", n,d );
        long c = 0;
        while( n >= d){
            n = n - d;
            c++;
        }
        return c;
    }
}

class Main {
    public static void main(String[] args) {
        DivTask div = new DivTask(120,4);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(div);
        long res = div.join();
        System.out.println(res);
    }

}
