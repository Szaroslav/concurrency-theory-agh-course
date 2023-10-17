package ex2;

public class B extends Thread {
    private static int state = 0;
    private final int n;

    public B(int n) {
        this.n = n;
    }

    public synchronized void increment() {
        B.state = B.state + 1;
    }

    public void run() {
        for (int i = 0; i < n; i++) {
            increment();
        }
        System.out.println(getId() + ": " + state);
    }

    public static void main(String[] args) {
        int N = 10000;
        B thread1 = new B(N);
        B thread2 = new B(N);
        thread1.start();
        thread2.start();
    }
}

