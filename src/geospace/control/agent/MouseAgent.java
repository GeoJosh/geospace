package geospace.control.agent;

import geospace.control.CurrentGameState;
import geospace.control.ShipInformation;
import geospace.entity.Constants;
import geospace.entity.Point;
import geospace.states.PlayingState;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author jpenton
 */
public class MouseAgent extends AbstractInputAgent implements MouseListener {

    private Point lastPosition;
    private Point waypoint;
    private Vector2f velocityVector;
    private Vector2f waypointVelocityVector;
    private static final float TWOPI = 2 * (float) Math.PI;
    private static final float HEADING_CLOSE_ENOUGH = Constants.SHIP_TURN_VELOCITY * 2;
    
    private static final float DISTANCE_COEFFICIENT = (float) ((2 * Constants.SHIP_ACCELERATION * Constants.DRAG_DECELERATION) / (Constants.SHIP_ACCELERATION + Constants.DRAG_DECELERATION));
    private static final float VELOCITY_COEFFICIENT = (float) (Constants.DRAG_DECELERATION / (Constants.SHIP_ACCELERATION + Constants.DRAG_DECELERATION)); 

    public MouseAgent() {
        super();

        this.agentName = "Mouse Agent";
        this.agentDescription = "A test agent that uses input from the mouse to control the player.";

        this.lastPosition = null;
        this.velocityVector = new Vector2f(0, 0);

        this.waypoint = new Point(0, 0);
        this.waypointVelocityVector = new Vector2f(0, 0);
    }

    @Override
    public void informGameState(CurrentGameState cgs) {
        ShipInformation shipInformation = cgs.getShipInformation(agentId);
        if (this.lastPosition == null) {
            this.lastPosition = new Point(0, 0);
        } else if (shipInformation != null && this.velocityVector != null) {
            this.velocityVector.x = shipInformation.getCenterX() - this.lastPosition.getX();
            this.velocityVector.y = shipInformation.getCenterY() - this.lastPosition.getY();
        }
        
        this.lastPosition.setXY(shipInformation.getCenterX(), shipInformation.getCenterY());
        this.waypointVelocityVector.x = calculateWaypointTargetVelocity(this.waypoint.getX() - shipInformation.getCenterX(), this.velocityVector.x);
        this.waypointVelocityVector.y = calculateWaypointTargetVelocity(this.waypoint.getY() - shipInformation.getCenterY(), this.velocityVector.y);

        double targetHeading = this.waypointVelocityVector.getTheta();
//        System.out.println(this.velocityVector.length());
        double currentHeading = this.velocityVector.length() > 0 ? this.velocityVector.getTheta() : ((shipInformation.getHeading() * 360) / TWOPI) % 360;
        double headingDiff = currentHeading - targetHeading;
        
        headingDiff = headingDiff > 180 ? headingDiff - 360 : headingDiff < -180 ? headingDiff + 360 : headingDiff;
//        System.out.println(currentHeading + " " + targetHeading + " " + headingDiff);

        this.agentController.setTurningPort(headingDiff > 0 && headingDiff > Constants.SHIP_TURN_VELOCITY);
        this.agentController.setTurningStarboard(headingDiff < 0 && headingDiff < -Constants.SHIP_TURN_VELOCITY);

        if(Math.abs(headingDiff) < HEADING_CLOSE_ENOUGH) {
            this.agentController.setThrusting(true);
        }
        else {
            this.agentController.setThrusting(false);
        }
    }

    @Override
    public void setInput(Input input) {
        super.setInput(input);
        input.addMouseListener(this);
    }

    private float calculateWaypointTargetVelocity(float dt, float v0) {
        double quadraticValue = Math.sqrt((DISTANCE_COEFFICIENT * Math.abs(dt)) + (VELOCITY_COEFFICIENT * v0 * v0));
        
        if(dt > 0) {
            return -v0 + (float)quadraticValue;
        }
        else {
            return -v0 - (float)quadraticValue;
        }
    }
    
    public void mouseWheelMoved(int change) {
    }

    public void mouseClicked(int button, int x, int y, int clickCount) {
    }

    public void mousePressed(int button, int x, int y) {
        switch (button) {
            case Input.MOUSE_LEFT_BUTTON:
                this.agentController.setFiring(true);
                break;
            case Input.MOUSE_RIGHT_BUTTON:
                this.agentController.setShielding(true);
                break;
        }
    }

    public void mouseReleased(int button, int x, int y) {
        switch (button) {
            case Input.MOUSE_LEFT_BUTTON:
                this.agentController.setFiring(false);
                break;
            case Input.MOUSE_RIGHT_BUTTON:
                this.agentController.setShielding(false);
                break;
        }
    }

    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        this.waypoint.setXY(newx - PlayingState.BORDER_SIZE, newy - PlayingState.BORDER_SIZE - PlayingState.TITLE_HEIGHT);
    }

    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
    }

    public boolean isAcceptingInput() {
        return true;
    }

    public void inputEnded() {
    }

    public void inputStarted() {
    }
}
