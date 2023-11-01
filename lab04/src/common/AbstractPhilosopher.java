package common;

public abstract class AbstractPhilosopher extends Thread {
    protected final int place;
    protected final Fork leftFork;
    protected final Fork rightFork;
    protected final int iterations;
    protected final PhilosopherStats stats = new PhilosopherStats();

    public AbstractPhilosopher(
            final int place,
            final Fork leftFork,
            final Fork rightFork,
            final int iterations
    ) {
        this.place      = place;
        this.leftFork   = leftFork;
        this.rightFork  = rightFork;
        this.iterations = iterations;
    }

    protected void log(String message) {
        System.out.printf("[Philosopher %d] %s%n", place, message);
    }

    public int place() {
        return place;
    }

    public PhilosopherStats stats() {
        return stats;
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

    protected abstract void eat();
}
