package s3.intro;

public class Utils {

    public static void main(String[] args) {


        Thread th = new Thread(() -> System.out.println("sóc el thread"));

        Runnable r = () -> System.out.println("sóc el runnable");
        Thread th2 = new Thread(r);

        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Acabat!");
    }

}
