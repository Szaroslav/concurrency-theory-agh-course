package ex1;

public class C extends Thread {
    public static boolean continueExecuting = true;
    private final int n;

    public C(int n) {
        this.n = n;
    }

    public void run() {
        for (int i = n; i >= 0; i--) {
            if (!C.continueExecuting)
                return;

            System.out.println(i);
            if (i == 0) {
                System.out.println("Pif! Paf!");
                C.continueExecuting = false;
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            C thread = new C(2);
            thread.start();
        }
    }
}
