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

    public abstract void simulate();
}
