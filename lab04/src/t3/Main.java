package t3;

import common.AbstractDiningPhilosophers;
import common.AbstractPhilosopher;
import common.Fork;

class Philosopher extends AbstractPhilosopher {
    public Philosopher(
            final int place,
            final Fork leftFork,
            final Fork rightFork,
            final int iterations
    ) {
        super(place, leftFork, rightFork, iterations);
    }

    @Override
    protected void eat() {
        if (place % 2 == 1) {
            synchronized (rightFork) {
                log("Taken right fork.");
                synchronized (leftFork) {
                    log("Taken left fork.");
                }
            }
        }
        else {
            synchronized (leftFork) {
                log("Taken left fork.");
                synchronized (rightFork) {
                    log("Taken right fork.");
                }
            }
        }
    }
}

class DiningPhilosophers extends AbstractDiningPhilosophers {
    public DiningPhilosophers(final int philosophersNumber, final int iterations) {
        super(philosophersNumber, iterations);
    }

    @Override
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
