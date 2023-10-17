package ex3;

public class A extends Thread {
    public static double state;
    private final double updateValue;

    public A(double updateValue) {
        this.updateValue = updateValue;
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            state = updateValue;
            if (Math.abs(A.state - updateValue) > 0.0001) {
                System.out.println("Proof!");
            }
        }
    }

    public static void main(String[] args) {
        A thread1 = new A(0);
        A thread2 = new A(Double.MAX_VALUE);
        thread1.start();
        thread2.start();
    }
}
