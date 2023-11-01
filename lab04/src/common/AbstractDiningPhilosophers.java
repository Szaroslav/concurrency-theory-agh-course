package common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        ExecutorService executorService = Executors.newCachedThreadPool();
        PhilosopherStats[] philosopherStats = new PhilosopherStats[philosophersNumber];
        for (int i = 0; i < philosophersNumber; i++) {
            AbstractPhilosopher philosopher = createPhilosopher(i);
            philosopherStats[i] = philosopher.stats();
            executorService.execute(philosopher);
        }
        executorService.shutdown();

        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
            for (int i = 0; i < philosophersNumber; i++) {
                System.out.println(philosopherStats[i].averageMeasurementTimeMs());
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
