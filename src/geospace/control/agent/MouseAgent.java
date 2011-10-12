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

    private Point waypoint;
    private Vector2f velocityVector;
    private Vector2f waypointVelocityVector;
    
    private static final float DISTANCE_COEFFICIENT = (float) ((2 * Constants.SHIP_ACCELERATION * Constants.DRAG_DECELERATION) / (Constants.SHIP_ACCELERATION + Constants.DRAG_DECELERATION));
    private static final float VELOCITY_COEFFICIENT = (float) (Constants.DRAG_DECELERATION / (Constants.SHIP_ACCELERATION + Constants.DRAG_DECELERATION)); 
    
    private static final float SHIP_TURN_VELOCITY_DEGREE = (float)Math.toDegrees(Constants.SHIP_TURN_VELOCITY);
    private static final float SHIP_TURN_VELOCITY_TURBO_DEGREE = (float)Math.toDegrees(Constants.SHIP_TURN_VELOCITY_TURBO);

    public MouseAgent() {
        super();

        this.agentName = "Mouse Agent";
        this.agentDescription = "A test agent that uses input from the mouse to control the player.";

        this.waypoint = new Point(0, 0);
        this.waypointVelocityVector = new Vector2f(0, 0);
    }

    @Override
    public void informGameState(CurrentGameState cgs) {
        ShipInformation shipInformation = cgs.getShipInformation(agentId);
        
        if(shipInformation != null) {
            this.velocityVector = new Vector2f(shipInformation.getVelocityX(), shipInformation.getVelocityY());

            this.waypointVelocityVector.x = this.waypoint.getX() - shipInformation.getCenterX();
            this.waypointVelocityVector.y = this.waypoint.getY() - shipInformation.getCenterY();

//            this.waypointVelocityVector.x = calculateWaypointTargetVelocity(this.waypoint.getX() - shipInformation.getCenterX(), this.velocityVector.x);
//            this.waypointVelocityVector.y = calculateWaypointTargetVelocity(this.waypoint.getY() - shipInformation.getCenterY(), this.velocityVector.y);

            double targetHeading = this.waypointVelocityVector.getTheta();
            double currentHeading = Constants.radiansToDegrees(shipInformation.getHeading());
            double headingDiff = targetHeading - currentHeading;

            headingDiff = headingDiff > 180 ? headingDiff - 360 : headingDiff < -180 ? headingDiff + 360 : headingDiff;
            System.out.println(targetHeading + " " + currentHeading + " " + headingDiff);

            if(headingDiff < 0) {
                this.agentController.setTurningStarboard(false);

                if(headingDiff < -SHIP_TURN_VELOCITY_TURBO_DEGREE) {
                    this.agentController.setTurbo(true);
                    this.agentController.setTurningPort(true);
                }
                else if(headingDiff < -SHIP_TURN_VELOCITY_DEGREE) {
                    this.agentController.setTurbo(false);
                    this.agentController.setTurningPort(true);
                }
                else {
                    this.agentController.setTurningPort(false);
                }
            }
            else if(headingDiff > 0) {
                this.agentController.setTurningPort(false);
                
                if(headingDiff > 2 * SHIP_TURN_VELOCITY_TURBO_DEGREE) {
                    this.agentController.setTurbo(true);
                    this.agentController.setTurningStarboard(true);
                }
                else if(headingDiff > SHIP_TURN_VELOCITY_DEGREE) {
                    this.agentController.setTurbo(false);
                    this.agentController.setTurningStarboard(true);
                } 
                else {
                    this.agentController.setTurningStarboard(false);
                }
            }
            
            System.out.println(this.agentController.isTurningPort() + " " + this.agentController.isTurningStarboard()+ " " + this.agentController.isTurbo());

            if(!this.agentController.isTurningPort() && !this.agentController.isTurningStarboard()) {
                this.agentController.setThrusting(true);
            }
            else {
                this.agentController.setThrusting(false);
            }
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
