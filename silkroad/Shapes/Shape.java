package Shapes;

import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Clase abstracta base para todas las figuras geométricas.
 * Contiene la funcionalidad común de posición, color y visibilidad.
 * Refactorización del Ciclo 4.
 */
public abstract class Shape {
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;

    public Shape() {
        this.isVisible = false;
        this.color = "black";
    }

    /**
     * Dibuja la figura en el canvas. (Implementado por las clases hijas)
     */
    protected abstract void draw();

    /**
     * Borra la figura del canvas. (Implementado por las clases hijas)
     */
    protected abstract void erase();

    /**
     * Hace que la figura sea visible.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    /**
     * Hace que la figura sea invisible.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    public int getX() {
        return xPosition;
    }

    public int getY() {
        return yPosition;
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

    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }
}
