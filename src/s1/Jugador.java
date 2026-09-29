package s1;

public class Jugador implements Comparable<Jugador>{
    @Override
    public int compareTo(Jugador o) {
        if(this.getPunts() < o.getPunts()) return 1;
        else  if(this.getPunts() > o.getPunts()) return -1;
        else return 0;
    }

    private String nom;
    private int punts;

    public Jugador(String nom) {
        this.nom = nom;
        punts = 0;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPunts() {
        return punts;
    }

    public void addPunts(int punts) {
        this.punts += punts;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nom='" + nom + '\'' +
                ", punts=" + punts +
                '}';
    }


}
