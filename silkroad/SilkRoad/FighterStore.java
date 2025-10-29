package SilkRoad;

import Shapes.*;

/**
 * Una tienda que solo entrega tenges a robots
 * que tengan más tenges que ella.
 * Requisito del Ciclo 4.
 */
public class FighterStore extends Store {

    public FighterStore(int location, int tenges, String color, int x, int y) {
        super(location, tenges, color, x, y);
    }

    /**
     * Solo entrega tenges si el robot recolector tiene más tenges que esta tienda.
     */
    @Override
    public int collectTenges(Robot collector) {
        if (collector.getTenges() > this.getTenges()) {
            return super.collectTenges(collector); 
        } else {
            return 0; 
        }
    }
}
