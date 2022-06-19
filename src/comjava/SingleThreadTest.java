package comjava;

public class SingleThreadTest {

    static long COUNTER = 0;

    public static void main(String... args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100_000_000; i++) {
            ++COUNTER;
        }
        long end = System.currentTimeMillis();
        System.out.println("Executed in: " + (end - start));
        System.out.println(COUNTER);
    }
}
