package geospace.control;

/**
 *
 * @author jpenton
 */
public class GameEvent {
    public enum GameEventType {
        DIED,
        SPAWNED,
        GAME_START,
        GAME_END
    }
    
    private GameEventType event;
    private String entityId;

    public GameEvent(GameEventType event, String entityId) {
        this.event = event;
        this.entityId = entityId;
    }

    public GameEvent() {
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public GameEventType getEvent() {
        return event;
    }

    public void setEvent(GameEventType event) {
        this.event = event;
    }
}
