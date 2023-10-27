package t2;

import java.util.concurrent.locks.ReentrantLock;

class Fork extends ReentrantLock { }

class Philosopher extends Thread {
    private final int place;
    private final Fork leftFork;
    private final Fork rightFork;
    private final int iterations;

    public Philosopher(
            final int place,
            final Fork leftFork,
            final Fork rightFork,
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
        if (!leftFork.tryLock()) {
            return;
        }
        if (!rightFork.tryLock()) {
            leftFork.unlock();
            return;
        }

        log("Taken right and left fork.");
        log("Eating…");
    }
}

class DiningPhilosophers {
    private final int philosophersNumber;
    private final int iterations;
    private final Fork[] forks;

    DiningPhilosophers(final int philosophersNumber, final int iterations) {
        this.philosophersNumber = philosophersNumber;
        this.iterations         = iterations;

        forks = new Fork[philosophersNumber];
        for (int i = 0; i < philosophersNumber; i++) {
            forks[i] = new Fork();
        }
    }

    public void simulate() {
        for (int i = 0; i < philosophersNumber; i++) {
            Philosopher philosopher = new Philosopher(
                i,
                forks[i],
                forks[(i + 1) % philosophersNumber],
                iterations
            );
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
