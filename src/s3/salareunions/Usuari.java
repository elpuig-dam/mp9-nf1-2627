package s3.salareunions;


public class Usuari extends Thread {
    private final Impressora impressora;
    private final SalaReunions salaReunions;

    public Usuari(Impressora impressora, SalaReunions salaReunions) {
        this.impressora = impressora;
        this.salaReunions = salaReunions;
    }

    @Override
    public void run() {
        try {
            // Accedir a la sala de reunions
            salaReunions.entrar();
            System.out.println(getName() + " ha entrat a la sala de reunions.");
            Thread.sleep((int) (Math.random() * 1000));

            // Accedir a la impressora només després d'haver estat a la sala de reunions
            impressora.agafar();
            System.out.println(getName() + " està imprimint...");
            Thread.sleep((int) (Math.random() * 1000));
            impressora.deixar();
            System.out.println(getName() + " ha acabat d'imprimir.");
            Thread.sleep((int) (Math.random() * 1000));

            salaReunions.sortir();
            System.out.println(getName() + " ha sortit de la sala de reunions.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}