package s1;

import java.util.concurrent.Callable;

public class Alumne implements Callable<Integer> {
    private String nom;
    public Alumne(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public int examinar() {
        return (int)(Math.random() * 10);
    }

    @Override
    public Integer call() throws Exception {
        return examinar();
    }
}
