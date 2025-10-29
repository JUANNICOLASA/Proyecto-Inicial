package Shapes; 

import java.awt.geom.Rectangle2D; 

/**
 * Un rectángulo que hereda de la clase abstracta Shape.
 * Refactorización del Ciclo 4.
 */
public class Rectangle extends Shape {
    private int height;
    private int width;

    public Rectangle() {
        super();
        this.height = 30;
        this.width = 40;
        this.xPosition = 70;
        this.yPosition = 15;
        this.color = "magenta";
    }

    public Rectangle(int x, int y, int newWidth, int newHeight) {
        super();
        this.height = newHeight;
        this.width = newWidth;
        this.xPosition = x;
        this.yPosition = y;
        this.color = "magenta";
    }

    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, new Rectangle2D.Double(xPosition, yPosition, width, height));
        }
    }

    @Override
    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
