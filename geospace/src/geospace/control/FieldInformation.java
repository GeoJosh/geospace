package geospace.control;

import geospace.entity.Field;

/**
 *
 * @author jpenton
 */
public class FieldInformation {
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;

    public FieldInformation() {
    }

    public FieldInformation(Field field) {
        this.maxX = field.getFieldWidth();
        this.maxY = field.getFieldHeight();
    }

    public float getMaxX() {
        return maxX;
    }

    public void setMaxX(float maxX) {
        // Method disabled
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        // Method disabled
    }

    public float getMinX() {
        return minX;
    }

    public void setMinX(float minX) {
        // Method disabled
    }

    public float getMinY() {
        return minY;
    }

    public void setMinY(float minY) {
        // Method disabled
    }    
}
