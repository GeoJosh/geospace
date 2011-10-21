package geospace.control.agent;

import geospace.control.CurrentGameState;
import geospace.control.ShipInformation;
import geospace.entity.Constants;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author jpenton
 */
public class TargetingAgent extends AbstractAgent {

    private String opponentId;
    private static final float SHIP_TURN_VELOCITY_DEGREE = (float) Math.toDegrees(Constants.SHIP_TURN_VELOCITY);
    private static final float SHIP_TURN_VELOCITY_TURBO_DEGREE = (float) Math.toDegrees(Constants.SHIP_TURN_VELOCITY_TURBO);
    
    public TargetingAgent() {
        this.agentName = "Targeting Agent";
        this.agentDescription = "Agent focused on targeting the opponent.";
        
        this.opponentId = "";
    }

    @Override
    public void informGameState(CurrentGameState cgs) {
        if(this.opponentId.isEmpty() || cgs.getShipInformation(this.opponentId) == null) {
            for(ShipInformation info : cgs.getShips()) {
                if(!info.getId().equals(this.agentId)) {
                    this.opponentId = info.getId();
                }
            }
        }
        
        ShipInformation shipInformation = cgs.getShipInformation(this.agentId);
        ShipInformation opponentInformation = cgs.getShipInformation(this.opponentId);

        if(opponentInformation != null && shipInformation != null) {
            Vector2f targetVector = new Vector2f(opponentInformation.getCenterX() - shipInformation.getCenterX(), opponentInformation.getCenterY() - shipInformation.getCenterY());
            adjustHeading(targetVector.getTheta() - Constants.radiansToDegrees(shipInformation.getHeading()));
        }
        
        this.agentController.setFiring(true);
    }
    
    private void adjustHeading(double headingDiff) {
        headingDiff = headingDiff > 180 ? headingDiff - 360 : headingDiff < -180 ? headingDiff + 360 : headingDiff;
        
        if (headingDiff < 0) {
            this.agentController.setTurningStarboard(false);

            if (headingDiff < -SHIP_TURN_VELOCITY_TURBO_DEGREE) {
                this.agentController.setTurbo(true);
                this.agentController.setTurningPort(true);
            } else if (headingDiff < -SHIP_TURN_VELOCITY_DEGREE) {
                this.agentController.setTurbo(false);
                this.agentController.setTurningPort(true);
            } else {
                this.agentController.setTurningPort(false);
                this.agentController.setTurbo(true);
            }
        } else if (headingDiff > 0) {
            this.agentController.setTurningPort(false);

            if (headingDiff > 2 * SHIP_TURN_VELOCITY_TURBO_DEGREE) {
                this.agentController.setTurbo(true);
                this.agentController.setTurningStarboard(true);
            } else if (headingDiff > SHIP_TURN_VELOCITY_DEGREE) {
                this.agentController.setTurbo(false);
                this.agentController.setTurningStarboard(true);
            } else {
                this.agentController.setTurningStarboard(false);
                this.agentController.setTurbo(true);
            }
        }
    }    
}
