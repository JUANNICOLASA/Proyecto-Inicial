import java.awt.*;
import java.awt.geom.*;

public class Circle {
    private int diameter;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;

    public Circle() {
        diameter = 30;
        xPosition = 20;
        yPosition = 15;
        color = "blue";
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
    
    public void changeSize(int newDiameter) {
        erase();
        diameter = newDiameter;
        draw();
    }

    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, new Ellipse2D.Double(xPosition, yPosition, diameter, diameter));
        }
    }

    private void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}