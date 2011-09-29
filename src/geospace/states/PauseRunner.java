package geospace.states;

public class PauseRunner implements Runnable {

    private int delay;
    
    public PauseRunner(int delay) {
        this.delay = delay;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(this.delay);
        } catch (InterruptedException ex) {
        }
    }
}
