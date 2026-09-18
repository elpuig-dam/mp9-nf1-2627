package s1;


public class MultiplicacioS {
    private int operador1;
    private int operador2;

    public MultiplicacioS(int operador1, int operador2) {
        this.operador1 = operador1;
        this.operador2 = operador2;
    }

    public Integer operar(){
        try {
            Thread.sleep((int)Math.random()*2000 + 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return operador1 * operador2;
    }
}

