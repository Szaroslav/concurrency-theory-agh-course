package ex1;

public class A extends Thread {
    public void run() {
        for (int i = 5; i > 0; i--) {
            System.out.println(i);
        }
        System.out.println("Pif! Paf!");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            A thread = new A();
            thread.start();
        }
    }
}
