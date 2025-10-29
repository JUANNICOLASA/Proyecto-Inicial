package Shapes;

import java.awt.Polygon;

/**
 * Un triángulo que hereda de la clase abstracta Shape.
 * Refactorización del Ciclo 4.
 */
public class Triangle extends Shape {
    public static int VERTICES = 3;
    private int height;
    private int width;

    public Triangle() {
        super();
        this.height = 30;
        this.width = 40;
        this.xPosition = 140;
        this.yPosition = 15;
        this.color = "green";
    }
    
    public Triangle(int x, int y, int width, int height) {
        super();
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
        this.color = "green";
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
            int[] xpoints = { xPosition, xPosition + (width / 2), xPosition - (width / 2) };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
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
