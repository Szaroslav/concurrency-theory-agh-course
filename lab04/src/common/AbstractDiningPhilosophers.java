package common;

public abstract class AbstractDiningPhilosophers {
    protected final int philosophersNumber;
    protected final int iterations;
    protected final Fork[] forks;

    public AbstractDiningPhilosophers(final int philosophersNumber, final int iterations) {
        this.philosophersNumber = philosophersNumber;
        this.iterations         = iterations;

        forks = new Fork[philosophersNumber];
        for (int i = 0; i < philosophersNumber; i++) {
            forks[i] = new Fork();
        }
    }

    protected abstract AbstractPhilosopher createPhilosopher(int i);

    public void simulate() {
        AbstractPhilosopher[] philosophers = new AbstractPhilosopher[philosophersNumber];
        for (int i = 0; i < philosophersNumber; i++) {
            AbstractPhilosopher philosopher = createPhilosopher(i);
            philosophers[i] = philosopher;
            philosopher.start();
        }

        try {
            for (AbstractPhilosopher philosopher : philosophers) {
                philosopher.join();
                System.out.printf("%d,%d%n", philosopher.place(), philosopher.stats().averageMeasurementTimeMcs());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
