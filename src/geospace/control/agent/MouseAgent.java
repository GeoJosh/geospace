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
    private float waypointTargetVelocity;
    private Vector2f velocityVector;
    private static final float TWOPI = 2 * (float) Math.PI;
    private static final float HEADING_CLOSE_ENOUGH = Constants.SHIP_TURN_VELOCITY * 2;
    private static final float VELOCITY_COEFFICIENT = (float) (Constants.SHIP_ACCELERATION * Math.sqrt((2 * Constants.DRAG_DECELERATION) / ((Constants.SHIP_ACCELERATION * Constants.SHIP_ACCELERATION) + (Constants.SHIP_ACCELERATION * Constants.DRAG_DECELERATION))));
    private static final float DISTANCE_COEFFICIENT = (float) ((Constants.DRAG_DECELERATION) / (Constants.SHIP_ACCELERATION + Constants.DRAG_DECELERATION));

    public MouseAgent() {
        super();

        this.agentName = "Mouse Agent";
        this.agentDescription = "A test agent that uses input from the mouse to control the player.";

        this.lastPosition = null;
        this.velocityVector = new Vector2f(0, 0);

        this.waypoint = new Point(0, 0);
        this.waypointTargetVelocity = 0;
        System.out.println(VELOCITY_COEFFICIENT);
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

        double targetHeading = new Vector2f(this.waypoint.getX() - shipInformation.getCenterX(), shipInformation.getCenterY() - this.waypoint.getY()).getTheta();
        double currentHeading = (((TWOPI - shipInformation.getHeading()) / TWOPI) * 360) % 360;

        double headingDiff = currentHeading - targetHeading;
        headingDiff = headingDiff > 180 ? headingDiff - 360 : headingDiff < -180 ? headingDiff + 360 : headingDiff;

        this.agentController.setTurningStarboard(headingDiff > 0 && headingDiff > Constants.SHIP_TURN_VELOCITY);
        this.agentController.setTurningPort(headingDiff < 0 && headingDiff < -Constants.SHIP_TURN_VELOCITY);

        // TODO: Fix this
        this.waypointTargetVelocity = Constants.SHIP_ACCELERATION * VELOCITY_COEFFICIENT * (float) Math.sqrt(Point.distance(this.lastPosition, this.waypoint));
        if (this.waypointTargetVelocity > 0 && this.waypointTargetVelocity > this.velocityVector.length() + Constants.SHIP_ACCELERATION) {
            this.agentController.setThrusting(true);
        } else {
            this.agentController.setThrusting(false);
        }

//        if(this.waypointTargetVelocity != 0 && Math.abs(headingDiff) < HEADING_CLOSE_ENOUGH) {
//            this.waypointTargetVelocity = Constants.SHIP_ACCELERATION * VELOCITY_COEFFICIENT * (float)Math.sqrt(Point.distance(this.lastPosition, this.waypoint));
//
//            if(this.velocityVector.length() < this.waypointTargetVelocity + Constants.SHIP_ACCELERATION) {
//                this.agentController.setThrusting(true);
//            } else {
//                this.agentController.setThrusting(false);
//                this.waypointTargetVelocity = 0;
//            }
//        }
    }

    @Override
    public void setInput(Input input) {
        super.setInput(input);
        input.addMouseListener(this);
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

        if (this.lastPosition != null) {
            this.waypointTargetVelocity = Constants.SHIP_ACCELERATION * VELOCITY_COEFFICIENT * (float) Math.sqrt(Point.distance(this.lastPosition, this.waypoint));
        }
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
