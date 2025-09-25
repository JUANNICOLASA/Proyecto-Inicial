import java.awt.*;

/**
 * A rectangle that can be manipulated and that draws itself on a canvas.
 *
 * @author Michael Kolling and David J. Barnes (Modified)
 * @version 1.0 (15 July 2000)
 */
public class Rectangle {

    public static final int EDGES = 4;
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;

    /**
     * Create a new rectangle at default position with default color.
     */
    public Rectangle() {
        height = 30;
        width = 40;
        xPosition = 70;
        yPosition = 15;
        color = "magenta";
        isVisible = false;
    }

    /**
     * Create a new rectangle with a specific position and size.
     * @param x The x-coordinate of the top-left corner.
     * @param y The y-coordinate of the top-left corner.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     */
    public Rectangle(int x, int y, int width, int height) {
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
        this.color = "magenta";
        this.isVisible = false;
    }

    // --- MÉTODOS AÑADIDOS ---
    /**
     * Retorna la coordenada X actual del rectángulo.
     * @return la posición x.
     */
    public int getX() {
        return xPosition;
    }

    /**
     * Retorna la coordenada Y actual del rectángulo.
     * @return la posición y.
     */
    public int getY() {
        return yPosition;
    }
    // -------------------------

    /**
     * Make this rectangle visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    /**
     * Make this rectangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    /**
     * Move the rectangle a few pixels to the right.
     */
    public void moveRight() {
        moveHorizontal(20);
    }

    /**
     * Move the rectangle a few pixels to the left.
     */
    public void moveLeft() {
        moveHorizontal(-20);
    }

    /**
     * Move the rectangle a few pixels up.
     */
    public void moveUp() {
        moveVertical(-20);
    }

    /**
     * Move the rectangle a few pixels down.
     */
    public void moveDown() {
        moveVertical(20);
    }

    /**
     * Move the rectangle horizontally by a given distance.
     * @param distance The distance to move in pixels.
     */
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the rectangle vertically by a given distance.
     * @param distance The distance to move in pixels.
     */
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the rectangle horizontally by a given distance.
     * @param distance The distance to move in pixels.
     */
    public void slowMoveHorizontal(int distance) {
        int delta;
        if (distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }
        for (int i = 0; i < distance; i++) {
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the rectangle vertically by a given distance.
     * @param distance The distance to move in pixels.
     */
    public void slowMoveVertical(int distance) {
        int delta;
        if (distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }
        for (int i = 0; i < distance; i++) {
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the size to the new size.
     * @param newHeight The new height in pixels. Must be >= 0.
     * @param newWidth The new width in pixels. Must be >= 0.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    /**
     * Change the color.
     * @param newColor The new color. Valid colors are "red", "yellow", "blue", "green", "magenta" and "black".
     */
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    /*
     * Draw the rectangle with current specifications on screen.
     */
    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, width, height));
        }
    }

    /*
     * Erase the rectangle on screen.
     */
    private void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}