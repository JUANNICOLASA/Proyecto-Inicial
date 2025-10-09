import java.awt.*;

public class Rectangle {
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;

    public Rectangle() {
        height = 30;
        width = 40;
        xPosition = 70;
        yPosition = 15;
        color = "magenta";
        isVisible = false;
    }
    
    public Rectangle(int x, int y, int newWidth, int newHeight) {
        height = newHeight;
        width = newWidth;
        xPosition = x;
        yPosition = y;
        color = "magenta";
        isVisible = false;
    }

    public int getX() { return xPosition; }
    public int getY() { return yPosition; }

    public void makeVisible() {
        isVisible = true;
        draw();
    }

    public void makeInvisible() {
        erase();
        isVisible = false;
    }
    
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    public void slowMoveHorizontal(int distance) {
        int delta = (distance < 0) ? -1 : 1;
        for (int i = 0; i < Math.abs(distance); i++) {
            xPosition += delta;
            draw();
        }
    }

    public void slowMoveVertical(int distance) {
        int delta = (distance < 0) ? -1 : 1;
        for (int i = 0; i < Math.abs(distance); i++) {
            yPosition += delta;
            draw();
        }
    }
    
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, new java.awt.Rectangle(xPosition, yPosition, width, height));
        }
    }

    private void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}