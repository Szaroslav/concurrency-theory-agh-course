import java.util.concurrent.TimeUnit;

class Producer2 extends Thread {
    private final Buffer2 buffer = new Buffer2();

    public void run() {
        for (int i = 0; i < 128; ++i) {
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

class Consumer2 extends Thread {
    private final Buffer2 buffer = new Buffer2();
    private final int i;

    public Consumer2(int i) {
        this.i = i;
    }

    public void run() {
        synchronized (buffer) {
            if (buffer.peek() != i) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Pizza has been received: " + buffer.get());
        }
    }
}

class Buffer2 {
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

    public synchronized int peek() {
        if (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        notifyAll();
        return buffer[currentSize - 1];
    }
}

class UniqueID2 {
    private static int id = 0;

    public synchronized static int getId() {
        return id++;
    }
}

class Simulation2 {
    public static void main(String[] args) {
        Producer2 producer1 = new Producer2();
        producer1.start();

        for (int i = 0; i < 99; i++) {
            Consumer2 consumer = new Consumer2(i);
            consumer.start();
        }
    }
}
