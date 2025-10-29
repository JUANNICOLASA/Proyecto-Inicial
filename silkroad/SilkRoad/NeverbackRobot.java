package SilkRoad;

import Shapes.*;
import java.util.List;
import java.util.Comparator;

/**
 * Un robot que nunca se devuelve (solo se mueve en dirección positiva).
 * Requisito del Ciclo 4.
 */
public class NeverbackRobot extends Robot {

    public NeverbackRobot(int location, String color, int x, int y) {
        super(location, color, x, y);
    }

    /**
     * Decide el próximo movimiento, pero solo si es hacia adelante (positivo).
     */
    @Override
    public int decideNextMove(List<Store> stores) {
        Store targetStore = null;
        int minDistance = Integer.MAX_VALUE;
        for (Store store : stores) {
            if (store.getTenges() > 0) {
                int metersToMove = store.getLocation() - this.location;
                int distance = Math.abs(metersToMove);
                
                if (metersToMove > 0 && distance < minDistance) {
                    minDistance = distance;
                    targetStore = store;
                }
            }
        }
        if (targetStore != null) {
            return targetStore.getLocation() - this.location;
        }
        return 0; 
    }
}
