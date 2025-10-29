package SilkRoad;

import Shapes.*; 
import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta base para todos los tipos de robots.
 * Refactorización del Ciclo 4.
 */
public abstract class Robot {
    protected int location;
    protected final int initialLocation; 
    protected int tenges;
    protected String color;
    protected Rectangle cabeza;
    protected Circle ojo1;
    protected Circle ojo2;
    protected boolean isVisible;
    protected List<Integer> profitHistory;

    public Robot(int location, String color, int x, int y) {
        this.location = location;
        this.initialLocation = location;
        this.tenges = 0;
        this.color = color;
        this.isVisible = false;
        this.profitHistory = new ArrayList<>();
        
        cabeza = new Rectangle(x - 12, y - 12, 25, 25);
        cabeza.changeColor(color);
        
        ojo1 = new Circle();
        ojo1.changeSize(5);
        ojo1.changeColor("white");

        ojo2 = new Circle();
        ojo2.changeSize(5);
        ojo2.changeColor("white");
        
        updateEyesPosition(x, y);
    }
    
    /**
     * Lógica de decisión de movimiento del robot. (Abstracto)
     */
    public abstract int decideNextMove(List<Store> stores);

    
    public void blink() {
        if (isVisible) {
            for (int i = 0; i < 3; i++) {
                makeInvisible();
                Canvas.getCanvas().wait(500); 
                makeVisible();
                Canvas.getCanvas().wait(500); 
            }
        }
    }
    
    public void recordMove(int profit) {
        profitHistory.add(profit);
    }
    
    /**
     * Mueve el robot visualmente.
     */
    public void moveTo(int newLocation, int newX, int newY, boolean slow) {
        if (isVisible) {
            int currentX = cabeza.getX() + 12;
            int currentY = cabeza.getY() + 12;
            int deltaX = newX - currentX;
            int deltaY = newY - currentY;
            
            if (slow) {
                cabeza.slowMoveHorizontal(deltaX);
                cabeza.slowMoveVertical(deltaY); 
            } else {
                cabeza.moveHorizontal(deltaX);
                cabeza.moveVertical(deltaY);
            }
            updateEyesPosition(newX, newY);
        }
        this.location = newLocation;
    }
    
    public void move(int newLocation) {
        this.location = newLocation;
    }

    public void returnToStart(int startX, int startY, boolean slow){
        moveTo(this.initialLocation, startX, startY, slow);
    }
    
    public void returnToStart() {
        this.location = initialLocation;
    }

    private void updateEyesPosition(int robotX, int robotY) {
        int ojo1X = robotX - 7;
        int ojo1Y = robotY - 7;
        int ojo2X = robotX + 3;
        int ojo2Y = robotY - 7;
        ojo1.moveHorizontal(ojo1X - ojo1.getX());
        ojo1.moveVertical(ojo1Y - ojo1.getY());
        ojo2.moveHorizontal(ojo2X - ojo2.getX());
        ojo2.moveVertical(ojo2Y - ojo2.getY());
    }
    
    public void makeVisible() { isVisible = true; cabeza.makeVisible(); ojo1.makeVisible(); ojo2.makeVisible(); }
    public void makeInvisible() { isVisible = false; cabeza.makeInvisible(); ojo1.makeInvisible(); ojo2.makeInvisible(); }
    public void erase(){ makeInvisible(); }
    
    public int getLocation() { return this.location; }
    public int getInitialLocation() { return this.initialLocation; }
    public int getTenges() { return this.tenges; }
    public void addTenges(int amount) { this.tenges += amount; }
    public void resetTenges() { this.tenges = 0; this.profitHistory.clear(); }
    public String getColor(){ return this.color; }
    public void setColor(String newColor) {
        this.color = newColor;
        this.cabeza.changeColor(newColor);
    }
    public List<Integer> getProfitHistory() { return this.profitHistory; }
}
