package s3.comandament;

public class MembreFamilia extends Thread {
    final private Comandament com;

    public MembreFamilia(String nom, Comandament com) {
        super(nom);
        this.com = com;
    }

    @Override
    public void run() {

        for(;;) {
            com.agafar();
            //mirar la tele
            System.out.println(getName() + " mirant la tele");
            try {
                Thread.sleep((long) (Math.random() * 200) + 50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            com.deixar();

            try {
                Thread.sleep((long) (Math.random() * 250) + 50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
