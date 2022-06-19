package comjava;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Deadlock {

    private static final Lock wKey = new ReentrantLock();
    private static final Lock eKey = new ReentrantLock();

    private static int openCaseCount = 0;

    public static void main(String... args) {
        var xThread = new Thread(() -> {
            IntStream.range(0, 100).forEach(i -> {
                wKey.lock();
                eKey.lock();
                openCase();
                eKey.unlock();
                wKey.unlock();
            });
        });
        var yThread = new Thread(() -> {
            IntStream.range(0, 100).forEach(i -> {
                // eKey.lock();
                // wKey.lock();
                // openCase();
                // wKey.unlock();
                // eKey.unlock();

                wKey.lock();
                eKey.lock();
                openCase();
                eKey.unlock();
                wKey.unlock();
            });
        });
        xThread.start();
        yThread.start();
    }

    private static void openCase() {
        ++openCaseCount;
        System.out.println("Opened case! Count: " + openCaseCount);
    }

}
