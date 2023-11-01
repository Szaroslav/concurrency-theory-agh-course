package common;

public class PhilosopherStats {
    private int sampleNumber            = 0;
    private long totalMeasurementTimeMs = 0;
    private long startTimeMs;
    private long endTimeMs;

    public void startMeasurement() {
        startTimeMs = System.currentTimeMillis();
    }

    public void endMeasurement() {
        endTimeMs = System.currentTimeMillis();
        sampleNumber++;
        long sampleMeasurementTimeMs = endTimeMs - startTimeMs;
        totalMeasurementTimeMs += sampleMeasurementTimeMs;
    }

    public long averageMeasurementTimeMs() {
        return totalMeasurementTimeMs / sampleNumber;
    }
}
