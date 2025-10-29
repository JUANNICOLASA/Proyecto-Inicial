package silkRoad;

import shapes.*;

/**
 * Clase abstracta base para todos los tipos de tiendas.
 * Refactorización del Ciclo 4.
 */
public abstract class Store {
    protected int location;
    protected int tenges;
    protected final int initialTenges; 
    protected final String originalColor;
    protected Rectangle visualRepresentation;
    protected int timesEmptied;

    public Store(int location, int tenges, String color, int x, int y) {
        this.location = location;
        this.tenges = tenges;
        this.initialTenges = tenges;
        this.originalColor = color;
        this.timesEmptied = 0;
        
        this.visualRepresentation = new Rectangle(x + 5, y + 5, 20, 20);
        this.visualRepresentation.changeColor(originalColor);
    }

    public void resupply() {
        this.tenges = this.initialTenges;
        this.visualRepresentation.changeColor(originalColor);
    }

    /**
     * Intenta recolectar tenges de la tienda.
     * @param collector El robot que recolecta (para lógica de FighterStore).
     * @return La cantidad de tenges recolectados.
     */
    public int collectTenges(Robot collector) {
        int collected = this.tenges;
        if (collected > 0) {
            this.tenges = 0;
            this.timesEmptied++;
            this.visualRepresentation.changeColor("gray");
        }
        return collected;
    }
    
    public int getLocation() { return this.location; }
    public int getTenges() { return this.tenges; }
    public int getInitialTenges() { return this.initialTenges; }
    public String getColor(){ return this.originalColor; }
    public int getTimesEmptied() { return this.timesEmptied; }
    
    public void makeVisible() { visualRepresentation.makeVisible(); }
    public void makeInvisible() { visualRepresentation.makeInvisible(); }
    public void erase(){ makeInvisible(); }
    
    /**
     * Permite a SilkRoad ajustar la apariencia de tiendas especiales.
     */
    public void setVisualRepresentation(String color, int width, int height) {
        this.visualRepresentation.changeColor(color);
        this.visualRepresentation.changeSize(height, width);
    }
}
