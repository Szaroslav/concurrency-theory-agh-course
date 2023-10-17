package ex1;

public class B extends Thread {
    private final int n;

    public B(int n) {
        this.n = n;
    }

    public void run() {
        for (int i = n; i > 0; i--) {
            System.out.println(i);
        }
        System.out.println("Pif! Paf!");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            B thread = new B(2137);
            thread.start();
        }
    }
}
