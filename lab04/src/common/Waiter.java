package common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Waiter extends Semaphore {
    public Waiter(int permits, boolean fair) {
        super(permits, fair);
    }
}
