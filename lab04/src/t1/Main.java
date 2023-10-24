package t1;

class Philosopher extends Thread {
    private final int place;
    private final Object leftFork;
    private final Object rightFork;
    private final int iterations;

    public Philosopher(
            final int place,
            final Object leftFork,
            final Object rightFork,
            final int iterations
    ) {
        this.place      = place;
        this.leftFork   = leftFork;
        this.rightFork  = rightFork;
        this.iterations = iterations;
    }

    private void log(String message) {
        System.out.printf("[Philosopher %d] %s%n", place, message);
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            think();
            eat();
        }
    }

    private void think() {
        log("Ummmmmmmm…");
    }

    private void eat() {
        synchronized (leftFork) {
            log("Taken left fork.");
            synchronized (rightFork) {
                log("Taken right fork.");
            }
        }
    }
}

class DiningPhilosophers {
    private final int philosophersNumber;
    private final int iterations;
    private final Object[] forks;

    DiningPhilosophers(final int philosophersNumber, final int iterations) {
        this.philosophersNumber = philosophersNumber;
        this.iterations         = iterations;

        forks = new Object[philosophersNumber];
        for (int i = 0; i < philosophersNumber; i++) {
            forks[i] = new Object();
        }
    }

    public void simulate() {
        for (int i = 0; i < philosophersNumber; i++) {
            Philosopher philosopher = new Philosopher(i, forks[i], forks[(i + 1) % philosophersNumber], iterations);
            philosopher.start();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        DiningPhilosophers simulation = new DiningPhilosophers(10, 128);
        simulation.simulate();
    }
}
