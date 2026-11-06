package s3.ciutatmagica;

public class Guardia extends Thread {
    Clau clau;
    public Guardia(String nom, Clau clau) {
        super(nom);
        this.clau = clau;
    }

    @Override
    public void run() {
        clau.obrir();
        System.out.printf("%s entra a la torre, la ciutat estarà segura", getName());

    }
}