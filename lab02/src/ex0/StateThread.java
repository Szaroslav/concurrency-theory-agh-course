package ex0;

public class StateThread extends Thread {
    private ex0.State state;
    private int n;

    public StateThread(ex0.State state, int n) {
        this.state = state;
        this.n     = n;
    }

    public void run() {
        int temp = 0;
        for (int i = 0; i < n; i++) {
            temp = state.update();
            if (temp != 0) break;
        }

        if (temp != 0) {
            System.out.println(Thread.currentThread().getId() + " " + temp);
        }
    }
}
