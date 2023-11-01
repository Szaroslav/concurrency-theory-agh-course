package t5;

import common.AbstractDiningPhilosophers;
import common.AbstractPhilosopher;
import common.Fork;
import common.Waiter;

class Philosopher extends AbstractPhilosopher {
    private final Waiter waiter;

    public Philosopher(
        final int place,
        final Fork leftFork,
        final Fork rightFork,
        final int iterations,
        final Waiter waiter
    ) {
        super(place, leftFork, rightFork, iterations);
        this.waiter = waiter;
    }

    @Override
    protected void eat() {
        try {
            stats.startMeasurement();
            waiter.acquire();
            synchronized (leftFork) {
                log("Taken left fork.");
                synchronized (rightFork) {
                    stats.endMeasurement();
                    log("Taken right fork.");
                    log("Eating…");
                    waiter.release();
                }
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class DiningPhilosophers extends AbstractDiningPhilosophers {
    private final Waiter waiter;

    public DiningPhilosophers(final int philosophersNumber, final int iterations) {
        super("t5", philosophersNumber, iterations);
        this.waiter = new Waiter(philosophersNumber - 1, true);
    }

    @Override
    protected AbstractPhilosopher createPhilosopher(final int i) {
        return new Philosopher(
            i,
            forks[i],
            forks[(i + 1) % philosophersNumber],
            iterations,
            waiter
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
