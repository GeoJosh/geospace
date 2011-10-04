package geospace.states.runners;

public class PauseRunner implements Runnable {

    private int delay;
    
    public PauseRunner(int delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        
        try {
            backgroundExecution();
            Thread.sleep(this.delay);
        } catch (InterruptedException ex) {
        }
    }
    
    protected void backgroundExecution() {
    }
}
