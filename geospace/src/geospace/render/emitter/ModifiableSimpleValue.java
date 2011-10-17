package geospace.render.emitter;

import org.newdawn.slick.particles.ConfigurableEmitter.Value;

public class ModifiableSimpleValue implements Value {

    /** The value configured */
    private float value;
    /** The next value */
    private float next;

    public ModifiableSimpleValue(float value) {
        this.value = value;
    }

    /**
     * Get the currently configured value
     *
     * @return The currently configured value
     */
    public float getValue(float time) {
        return value;
    }

    /**
     * Set the configured value
     *
     * @param value
     *            The configured value
     */
    public void setValue(float value) {
        this.value = value;
    }
}
