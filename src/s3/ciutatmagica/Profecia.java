package s3.ciutatmagica;

public class Profecia extends Thread {
    Clau clau;

    public Profecia(Clau clau) {
        this.clau = clau;
    }

    @Override
    public void run() {
        while (!clau.esCompleix()) {
            System.out.println("comprovant profecia...");
            clau.comprovarProfecia();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
