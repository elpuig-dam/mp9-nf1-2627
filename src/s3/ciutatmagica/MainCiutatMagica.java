package s3.ciutatmagica;

public class MainCiutatMagica {
    public static void main(String[] args) {
        Clau clau = new Clau();
        Guardia guardia = new Guardia("Gandalf",clau);
        Profecia profecia = new Profecia(clau);

        profecia.start();
        guardia.start();

    }
}