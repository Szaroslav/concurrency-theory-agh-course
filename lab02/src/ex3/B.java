package ex3;

public class B extends Thread {
    public static String state;
    private final String updateValue;

    public B(String updateValue) {
        this.updateValue = updateValue;
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            state = updateValue;
            if (!B.state.equals(updateValue)) {
                System.out.println("Proof!");
            }
        }
    }

    public static void main(String[] args) {
        B thread1 = new B("Ola");
        B thread2 = new B("Ala");
        thread1.start();
        thread2.start();
    }
}
