package s1;

import java.util.concurrent.Callable;

public class Multiplicacio implements Callable<Integer> {
    private int operador1;
    private int operador2;
    public Multiplicacio(int operador1, int operador2) {
        this.operador1 = operador1;
        this.operador2 = operador2;
    }
    @Override
    public Integer call() throws Exception {
        try {
            Thread.sleep((int)Math.random()*2000 + 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return operador1 * operador2;
    }
}

