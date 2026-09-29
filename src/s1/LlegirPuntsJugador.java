package s1;

public class LlegirPuntsJugador implements Runnable {
    private Jugador jugador;

    public LlegirPuntsJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    @Override
    public void run() {
        System.out.printf("%s porta %d punts%n",jugador.getNom(), jugador.getPunts());

    }
}
