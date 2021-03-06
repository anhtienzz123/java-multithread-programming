package comjava;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RaceConditionTest1 {


    public static void main(String... args) throws InterruptedException {
        final var shoppers =
                IntStream.range(0, 6).mapToObj(Shopper::new).collect(Collectors.toList());
        // Chạy toàn bộ các thread
        shoppers.forEach(Thread::start);
        // Chờ tất cả thread hoàn thành
        for (var shopper : shoppers) {
            shopper.join();
        }
        System.out.println("Total packs: " + Shopper.MASK_PACK_COUNT);
    }
}


class Shopper extends Thread {

    static int MASK_PACK_COUNT = 1;

    Shopper(int i) {
        setName(i % 2 == 0 ? "Husband" : "Wife");
    }

    @Override
    public void run() {
        addMask(getName());
    }

    static synchronized void addMask(String threadName) {
        if ("Husband".equals(threadName)) {
            MASK_PACK_COUNT += 1;
            System.out.println("Husband adds 1 pack");
            return;
        }
        MASK_PACK_COUNT *= 3;
        System.out.println("Wife multiple 3 times");
    }
}
