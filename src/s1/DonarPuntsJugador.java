package s1;

public class DonarPuntsJugador implements Runnable{
    private Jugador jugador;

    public DonarPuntsJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    @Override
    public void run() {
        int n = (int)(Math.random() * 10);
        jugador.addPunts(n);
    }
}
