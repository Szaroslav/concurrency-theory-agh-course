package t1;

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
        stats.startMeasurement();
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

class DiningPhilosophers extends AbstractDiningPhilosophers {
    public DiningPhilosophers(final int philosophersNumber, final int iterations) {
        super("t1", philosophersNumber, iterations);
    }

    @Override
    protected AbstractPhilosopher createPhilosopher(final int i) {
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
        int[] philosopherNumberArray = { 2, 3, 4, 5, 8, 10, 16, 32, 64, 128 };
        for (int philosophersNumber : philosopherNumberArray) {
            DiningPhilosophers simulation = new DiningPhilosophers(philosophersNumber, 1024);
            simulation.simulate();
        }
    }
}
