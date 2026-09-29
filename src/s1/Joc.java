package s1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Joc {
    public static void main(String[] args) {
        Jugador jugador = new Jugador("Juan");
        Jugador jugador2 = new Jugador("Pep");
        Jugador jugador3 = new Jugador("Sara");

        List<Jugador> jugadors = new ArrayList<>();
        jugadors.add(jugador);
        jugadors.add(jugador2);
        jugadors.add(jugador3);
        LlegirLlistaPuntsJugadors llj = new LlegirLlistaPuntsJugadors(jugadors);

        DonarPuntsJugador dpj1 = new DonarPuntsJugador(jugador);
        DonarPuntsJugador dpj2 = new DonarPuntsJugador(jugador2);
        DonarPuntsJugador dpj3 = new DonarPuntsJugador(jugador3);

        //LlegirPuntsJugador lpj1 = new  LlegirPuntsJugador(jugador);
        //LlegirPuntsJugador lpj2 = new  LlegirPuntsJugador(jugador2);
        //LlegirPuntsJugador lpj3 = new  LlegirPuntsJugador(jugador3);

        ScheduledExecutorService schExService = Executors.newScheduledThreadPool(3);
        schExService.scheduleWithFixedDelay(dpj1, 2, 5, TimeUnit.SECONDS);
        schExService.scheduleWithFixedDelay(dpj2, 4, 2, TimeUnit.SECONDS);
        schExService.scheduleWithFixedDelay(dpj3, 1, 4, TimeUnit.SECONDS);

        //schExService.scheduleWithFixedDelay(lpj1, 2, 2, TimeUnit.SECONDS);
        //schExService.scheduleWithFixedDelay(lpj2, 2, 4, TimeUnit.SECONDS);
        //schExService.scheduleWithFixedDelay(lpj3, 2, 5, TimeUnit.SECONDS);

        schExService.scheduleWithFixedDelay(llj, 2, 5, TimeUnit.SECONDS);


        try {
            schExService.awaitTermination(25, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        schExService.shutdownNow();


        Collections.sort(jugadors);

        jugadors.forEach(System.out::println);

    }
}
