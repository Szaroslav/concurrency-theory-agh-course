package common;

import java.util.HashMap;
import java.util.Map;

public class Waiter {
    private final int philosopherNumber;
    private int currentlyEatingPhilosophers = 0;
    private AbstractPhilosopher waitingPhilosopher = null;
    private final Map<Fork, AbstractPhilosopher> forkToPhilosopherMap = new HashMap<>();

    public Waiter(final int philosopherNumber) {
        this.philosopherNumber = philosopherNumber;
    }

    public boolean tryToGiveFork(AbstractPhilosopher philosopher, Fork fork) {
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
