package s3.salareunions;

public class SalaReunions {
    private final int capacitatMaxima;
    private int personesDins = 0;

    public SalaReunions(int capacitatMaxima) {
        this.capacitatMaxima = capacitatMaxima;
    }

    public synchronized void entrar() throws InterruptedException {
        while (personesDins >= capacitatMaxima) {
            wait();
        }
        personesDins++;
        notifyAll();
    }

    public synchronized void sortir() {
        personesDins--;
        notifyAll();
    }
}