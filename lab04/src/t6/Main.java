package t6;

import common.AbstractDiningPhilosophers;
import common.AbstractPhilosopher;
import common.Fork;
import common.Waiter;

import java.util.concurrent.TimeUnit;

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
            if (!waiter.tryAcquire(0, TimeUnit.SECONDS)) {
                synchronized (rightFork) {
                    log("Taken right fork.");
                    synchronized (leftFork) {
                        stats.endMeasurement();
                        log("Taken right fork.");
                        log("Eating…");
                        waiter.release();
                    }
                }
            }
            else {
                synchronized (leftFork) {
                    log("Taken left fork.");
                    synchronized (leftFork) {
                        stats.endMeasurement();
                        log("Taken right fork.");
                        log("Eating…");
                        waiter.release();
                    }
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
        super("t6", philosophersNumber, iterations);
        this.waiter = new Waiter(philosophersNumber - 1, true);
    }

    @Override
    protected AbstractPhilosopher createPhilosopher(int i) {
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
        DiningPhilosophers simulation = new DiningPhilosophers(10, 128);
        simulation.simulate();
    }
}
