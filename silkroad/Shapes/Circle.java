package Shapes;

import java.awt.geom.Ellipse2D;

/**
 * Un círculo que hereda de la clase abstracta Shape.
 * Refactorización del Ciclo 4.
 */
public class Circle extends Shape {
    private int diameter;

    public Circle() {
        super();
        this.diameter = 30;
        this.xPosition = 20;
        this.yPosition = 15;
        this.color = "blue";
    }

    public void changeSize(int newDiameter) {
        erase();
        diameter = newDiameter;
        draw();
    }

    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, new Ellipse2D.Double(xPosition, yPosition, diameter, diameter));
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
