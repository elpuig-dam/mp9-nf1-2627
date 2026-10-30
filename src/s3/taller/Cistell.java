package s3.taller;

public class Cistell {
    private final int capacitat;
    private int qtat;
    private boolean lliure;

    public Cistell(int capacitat) {
        this.capacitat = capacitat;
        qtat = 0;
        lliure = true;
    }

    public synchronized int posar(int n) {
        try {
            while(!lliure || ((qtat+n) > capacitat)) wait();
            lliure = false;
            qtat += n;
            notifyAll();
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
        return qtat;
    }

    public synchronized int treure(int n) {
        try {
            while(!lliure || ((qtat-n) < 0)) wait();
            lliure = false;
            qtat -= n;

            notifyAll();
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
        return qtat;
    }

    public synchronized void deixarCistell() {
        lliure = true;
        notifyAll();
    }
}
