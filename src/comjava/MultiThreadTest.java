package comjava;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultiThreadTest {

    static long COUNTER = 0;

    public static void main(String... args) throws InterruptedException {
        var threads = IntStream.range(0, 100).mapToObj(ignore -> new Thread(() -> {
            synchronized (MultiThreadTest.class) {
                for (int i = 0; i < 1_000_000; i++) {
                    ++COUNTER;
                }
            }
        })).collect(Collectors.toList());
        long start = System.currentTimeMillis();
        threads.forEach(Thread::start);
        for (var thread : threads) {
            thread.join();
        }
        long end = System.currentTimeMillis();
        System.out.println("Executed in: " + (end - start));
        System.out.println(COUNTER);
    }
}
