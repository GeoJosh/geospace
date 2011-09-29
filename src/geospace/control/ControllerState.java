package geospace.control;

public class ControllerState {
    private boolean thrusting;
    private boolean turningPort;
    private boolean turningStarboard;
    private boolean shielding;
    private boolean firing;

    public ControllerState() {
        this.thrusting = false;
        this.turningPort = false;
        this.turningStarboard = false;
        this.shielding = false;
        this.firing = false;
    }

    public ControllerState(boolean isThrusting, boolean isTurningPort, boolean isTurningStarboard, boolean isShielding, boolean isFiring) {
        this.thrusting = isThrusting;
        this.turningPort = isTurningPort;
        this.turningStarboard = isTurningStarboard;
        this.shielding = isShielding;
        this.firing = isFiring;
    }

    public boolean isFiring() {
        return firing;
    }

    public void setFiring(boolean firing) {
        this.firing = firing;
    }

    public boolean isShielding() {
        return shielding;
    }

    public void setShielding(boolean shielding) {
        this.shielding = shielding;
    }

    public boolean isThrusting() {
        return thrusting;
    }

    public void setThrusting(boolean thrusting) {
        this.thrusting = thrusting;
    }

    public boolean isTurningPort() {
        return turningPort;
    }

    public void setTurningPort(boolean turningPort) {
        this.turningPort = turningPort;
    }

    public boolean isTurningStarboard() {
        return turningStarboard;
    }

    public void setTurningStarboard(boolean turningStarboard) {
        this.turningStarboard = turningStarboard;
    }
}
