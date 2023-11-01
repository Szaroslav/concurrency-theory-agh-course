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
