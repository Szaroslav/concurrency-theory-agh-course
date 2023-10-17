import java.util.concurrent.TimeUnit;

class Producer extends Thread {
    private final Buffer2 buffer = new Buffer2();

    public void run() {
        for (int i = 0; i < 100; ++i) {
            int id = UniqueID2.getId();
            System.out.println("Pizza has been made: " + id);
            buffer.put(id);

            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Consumer extends Thread {
    private final Buffer2 buffer = new Buffer2();

    public void run() {
        for (int i = 0; i < 100; ++i) {
            System.out.println("Pizza has been received: " + buffer.get());

            try {
                TimeUnit.MILLISECONDS.sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Buffer {
    private static final int[] buffer = new int[128];
    private static int currentSize = 0;

    public synchronized boolean isEmpty() {
        return currentSize <= 0;
    }

    public synchronized boolean isFull() {
        return currentSize >= 128;
    }

    public synchronized void put(int i) {
        if (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        buffer[currentSize++] = i;
        notifyAll();
    }

    public synchronized int get() {
        if (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        notifyAll();
        return buffer[--currentSize];
    }
}

class UniqueID {
    private static int id = 0;

    public synchronized static int getId() {
        return id++;
    }
}

class Simulation {
    public static void main(String[] args) {
        Producer producer1 = new Producer();
        producer1.start();
        Producer producer2 = new Producer();
        producer2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Consumer consumer1 = new Consumer();
        consumer1.start();
        Consumer consumer2 = new Consumer();
        consumer2.start();
        Consumer consumer3 = new Consumer();
        consumer3.start();
    }
}
