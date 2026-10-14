package s3.comandament;

public class Comandament {
    private boolean lliure;

    public Comandament() {
        lliure = true;
    }

    public synchronized void agafar() {
        try {
            while(!lliure) wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lliure = false;
        notifyAll();
    }

    public synchronized void deixar() {
        lliure = true;
        notifyAll();
    }
}
