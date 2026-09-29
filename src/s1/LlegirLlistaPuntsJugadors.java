package s1;

import java.util.List;

public class LlegirLlistaPuntsJugadors implements Runnable{
    List<Jugador> jugadorList;

    public LlegirLlistaPuntsJugadors(List<Jugador> jugadorList) {
        this.jugadorList = jugadorList;
    }

    @Override
    public void run() {
        jugadorList.forEach(System.out::println);
    }
}
