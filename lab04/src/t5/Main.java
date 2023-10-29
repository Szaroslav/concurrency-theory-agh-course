package t5;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

class Fork extends ReentrantLock { }

class Waiter {
    private final int philosopherNumber;
    private int currentlyEatingPhilosophers = 0;
    private Philosopher waitingPhilosopher = null;
    private final Map<Fork, Philosopher> forkToPhilosopherMap = new HashMap<>();

    public Waiter(final int philosopherNumber) {
        this.philosopherNumber = philosopherNumber;
    }

    public boolean tryToGiveFork(Philosopher philosopher, Fork fork) {
        if (philosopher == waitingPhilosopher) {
            return false;
        }

        boolean locked;
        if (!(locked = fork.tryLock()) && forkToPhilosopherMap.get(fork) != philosopher) {
            if (currentlyEatingPhilosophers >= philosopherNumber - 1) {
                waitingPhilosopher = philosopher;
            }
            return false;
        }
        if (locked) {
            currentlyEatingPhilosophers++;
        }

        forkToPhilosopherMap.put(fork, philosopher);
        return true;
    }

    public void takeAwayDishes(Fork[] forks) {
        currentlyEatingPhilosophers--;
        for (Fork fork : forks) {
            forkToPhilosopherMap.remove(fork);
            fork.unlock();
        }
        waitingPhilosopher = null;
    }
}

class Philosopher extends Thread {
    private final int place;
    private final Fork leftFork;
    private final Fork rightFork;
    private final int iterations;
    private final Waiter waiter;

    public Philosopher(
            final int place,
            final Fork leftFork,
            final Fork rightFork,
            final int iterations,
            final Waiter waiter
    ) {
        this.place      = place;
        this.leftFork   = leftFork;
        this.rightFork  = rightFork;
        this.iterations = iterations;
        this.waiter     = waiter;
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
        if (!waiter.tryToGiveFork(this, leftFork))  return;
        log("Taken left fork.");
        leftFork.lock();
        if (!waiter.tryToGiveFork(this, rightFork)) return;
        log("Taken right fork.");
        log("Eating…");
        waiter.takeAwayDishes(new Fork[] { leftFork, rightFork });
    }
}

class DiningPhilosophers {
    private final int philosophersNumber;
    private final int iterations;
    private final Fork[] forks;
    private final Waiter waiter;

    DiningPhilosophers(final int philosophersNumber, final int iterations) {
        this.philosophersNumber = philosophersNumber;
        this.iterations         = iterations;

        this.waiter = new Waiter(philosophersNumber);

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
