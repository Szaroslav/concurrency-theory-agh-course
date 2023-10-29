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
        this.waiter     = waiter;
    }

    @Override
    protected void eat() {
        if (!waiter.tryToGiveFork(this, leftFork))  return;
        log("Taken left fork.");
        leftFork.lock();
        if (!waiter.tryToGiveFork(this, rightFork)) return;
        log("Taken right fork.");
        log("Eating…");
        waiter.takeAwayDishes(new Fork[] { leftFork, rightFork });
    }
}

class DiningPhilosophers extends AbstractDiningPhilosophers {
    private final Waiter waiter;

    public DiningPhilosophers(final int philosophersNumber, final int iterations) {
        super(philosophersNumber, iterations);
        this.waiter = new Waiter(philosophersNumber);
    }

    @Override
    public void simulate() {
        for (int i = 0; i < philosophersNumber; i++) {
            Philosopher philosopher = new Philosopher(
                i,
                forks[i],
                forks[(i + 1) % philosophersNumber],
                iterations,
                waiter
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
