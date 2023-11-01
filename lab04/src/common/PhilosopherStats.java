package common;

public class PhilosopherStats {
    private int sampleNumber             = 0;
    private long totalMeasurementTimeMcs = 0;
    private long startTimeMs;
    private long endTimeMs;

    public void startMeasurement() {
        startTimeMs = System.currentTimeMillis();
    }

    public void endMeasurement() {
        endTimeMs = System.currentTimeMillis();
        sampleNumber++;
        long sampleMeasurementTimeMcs = 1000 * (endTimeMs - startTimeMs);
        totalMeasurementTimeMcs += sampleMeasurementTimeMcs;
    }

    public long averageMeasurementTimeMcs() {
        if (sampleNumber == 0)
            return 0;
        return totalMeasurementTimeMcs / sampleNumber;
    }
}
