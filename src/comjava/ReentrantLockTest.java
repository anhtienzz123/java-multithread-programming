package comjava;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLockTest {

    private static int COUNTER = 0;
    private static Lock lock = new ReentrantLock();

    public static void main(String... args) throws Exception {

        final Runnable increaseCounterFunc =
                () -> IntStream.range(0, 100).forEach(ReentrantLockTest::increaseCounter);

        final var first = new Thread(increaseCounterFunc);
        final var second = new Thread(increaseCounterFunc);

        first.start();
        second.start();

        first.join();
        second.join();

        System.out.println(COUNTER);
    }

    private static void increaseCounter(int i) {
        lock.lock();
        ++COUNTER;
        lock.unlock();
    }
}
