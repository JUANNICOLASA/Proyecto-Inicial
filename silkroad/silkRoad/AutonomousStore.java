package silkRoad;

import shapes.*;
import java.util.Random;

/**
 * Una tienda que escoge su propia posición, ignorando la indicada.
 * Requisito del Ciclo 4.
 */
public class AutonomousStore extends Store {

    public AutonomousStore(int location, int tenges, String color, int x, int y) {
        super(location, tenges, color, x, y);
    }
    
}
