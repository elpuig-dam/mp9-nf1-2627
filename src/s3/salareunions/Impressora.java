package s3.salareunions;

public class Impressora {
    private boolean ocupada = false;

    public synchronized void agafar() throws InterruptedException {
        while (ocupada) {
            wait();
        }
        ocupada = true;
        notifyAll();
    }

    public synchronized void deixar() {
        ocupada = false;
        notifyAll();
    }
}