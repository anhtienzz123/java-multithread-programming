package comjava;

import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CountDownLatchTest {

    public static void main(String... args) throws InterruptedException {
        final var shoppers =
                IntStream.range(0, 6).mapToObj(Shopper2::new).collect(Collectors.toList());

        // Chạy toàn bộ các thread
        shoppers.forEach(Thread::start);
        // Chờ tất cả thread hoàn thành
        for (var shopper : shoppers) {
            shopper.join();
        }
        System.out.println("Total packs: " + Shopper2.MASK_PACK_COUNT);
    }
}


class Shopper2 extends Thread {

    static int MASK_PACK_COUNT = 1;
    static CountDownLatch CDL = new CountDownLatch(3);

    Shopper2(int i) {
        setName(i % 2 == 0 ? "Husband" : "Wife");
    }

    @Override
    public void run() {
        addMask(getName());
    }

    static void addMask(String threadName) {
        if ("husband".equalsIgnoreCase(threadName)) {
            synchronized (Shopper2.class) {
                MASK_PACK_COUNT += 1;
                System.out.println("Husband adds 1 pack");
            }
            CDL.countDown();
            return;
        }
        waitAtBarrier();
        synchronized (Shopper2.class) {
            MASK_PACK_COUNT *= 3;
            System.out.println("Wife multiple 3 times");
        }
    }

    static void waitAtBarrier() {
        try {
            CDL.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
