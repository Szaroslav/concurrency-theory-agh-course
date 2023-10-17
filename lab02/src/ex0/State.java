package ex0;

public class State {
    private int state = 0;

    public int update() {
        state++;
        state--;
        return state;
    }
}
