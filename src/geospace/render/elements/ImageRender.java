package geospace.render.elements;

import org.newdawn.slick.Image;

public class ImageRender {
    private Image image;
    private float x;
    private float y;

    public ImageRender(Image image, float x, float y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
