package t4;

import common.AbstractDiningPhilosophers;
import common.AbstractPhilosopher;
import common.Fork;

import java.util.concurrent.ThreadLocalRandom;

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
        stats.startMeasurement();
        if (ThreadLocalRandom.current().nextBoolean()) {
            synchronized (rightFork) {
                log("Taken right fork.");
                synchronized (leftFork) {
                    stats.endMeasurement();
                    log("Taken left fork.");
                }
            }
        }
        else {
            synchronized (leftFork) {
                log("Taken left fork.");
                synchronized (rightFork) {
                    stats.endMeasurement();
                    log("Taken right fork.");
                    log("Eating…");
                }
            }
        }
    }
}

class DiningPhilosophers extends AbstractDiningPhilosophers {
    public DiningPhilosophers(final int philosophersNumber, final int iterations) {
        super("t4", philosophersNumber, iterations);
    }

    @Override
    protected AbstractPhilosopher createPhilosopher(int i) {
        return new Philosopher(
            i,
            forks[i],
            forks[(i + 1) % philosophersNumber],
            iterations
        );
    }
}

public class Main {
    public static void main(String[] args) {
        DiningPhilosophers simulation = new DiningPhilosophers(10, 128);
        simulation.simulate();
    }
}
