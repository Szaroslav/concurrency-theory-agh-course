package common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class AbstractDiningPhilosophers {
    protected final String name;
    protected final int philosophersNumber;
    protected final int iterations;
    protected final Fork[] forks;

    public AbstractDiningPhilosophers(final String name, final int philosophersNumber, final int iterations) {
        this.name               = name;
        this.philosophersNumber = philosophersNumber;
        this.iterations         = iterations;

        forks = new Fork[philosophersNumber];
        for (int i = 0; i < philosophersNumber; i++) {
            forks[i] = new Fork();
        }
    }

    protected abstract AbstractPhilosopher createPhilosopher(int i);

    public void simulate() {
        for (int j = 0; j < 32; j++) {
            AbstractPhilosopher[] philosophers = new AbstractPhilosopher[philosophersNumber];
            for (int i = 0; i < philosophersNumber; i++) {
                AbstractPhilosopher philosopher = createPhilosopher(i);
                philosophers[i] = philosopher;
                philosopher.start();
            }

            try {
                BufferedWriter writer = new BufferedWriter(
                    new FileWriter(String.format("log/%s-%d.log", name, philosophersNumber), true)
                );

                for (AbstractPhilosopher philosopher : philosophers) {
                    philosopher.join();
                    writer.append(
                        String.format(
                            "%d,%d%n",
                            philosopher.place(),
                            philosopher.stats().averageMeasurementTimeMcs()
                        )
                    );
                }

                writer.close();
            }
            catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
