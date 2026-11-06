package s3.ciutatmagica;

public class Clau {

    private boolean certa = false;

    public synchronized void comprovarProfecia() {
        double r = Math.random();
        if (r > 0.85) {
            certa = true;
        }
        System.out.println("% de profecia: " + r);
        notifyAll();
    }

    public synchronized boolean esCompleix() {
        return certa;
    }

    public synchronized void obrir() {
        while (!certa) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}