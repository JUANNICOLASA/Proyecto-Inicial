public class Store {
    private int location;
    private int tenges;
    private final int initialTenges; 
    private final String originalColor;
    private Rectangle visualRepresentation;
    private int timesEmptied;

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

    public int collectTenges() {
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
}