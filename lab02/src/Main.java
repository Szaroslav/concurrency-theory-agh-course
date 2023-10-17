import ex0.State;
import ex0.StateThread;

public class Main {
    public static void main(String[] args) {
        State state = new State();
        for (int i = 0; i < 100; i++) {
            StateThread thread = new StateThread(state, 100);
            thread.start();
        }
    }
}