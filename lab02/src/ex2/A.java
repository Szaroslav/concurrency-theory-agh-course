package ex2;

public class A extends Thread {
    private static int state = 0;
    private final int n;

    public A(int n) {
        this.n = n;
    }

    public void increment() {
        ++state;
    }

    public void run() {
        for (int i = 0; i < n; i++) {
            increment();
        }
        System.out.println(getId() + ": " + state);
    }

    public static void main(String[] args) {
        int N = 10000;
        A thread1 = new A(N);
        A thread2 = new A(N);
        thread1.start();
        thread2.start();
    }
}

