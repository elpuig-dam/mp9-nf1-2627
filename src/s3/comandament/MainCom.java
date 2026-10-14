package s3.comandament;

public class MainCom {
    public static void main(String[] args) {
        Comandament c = new Comandament();
        MembreFamilia pare = new MembreFamilia("Joan", c);
        MembreFamilia mare = new MembreFamilia("Joanna", c);
        MembreFamilia fill = new MembreFamilia("Joanet", c);
        MembreFamilia filla = new MembreFamilia("Joaneta", c);
        MembreFamilia gat = new MembreFamilia("Joanic", c);

        pare.start();
        mare.start();
        fill.start();
        filla.start();
        gat.start();

    }
}
