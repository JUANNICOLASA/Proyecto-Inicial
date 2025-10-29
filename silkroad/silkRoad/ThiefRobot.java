package silkRoad;

import shapes.*;
import java.util.List;
import java.util.Comparator;

/**
 * Un robot "Ladrón" (Propio).
 * Si llega a la misma ubicación que otro robot, le roba un porcentaje (ej. 25%) de sus tenges.
 */
public class ThiefRobot extends Robot {

    private static final double STEAL_PERCENTAGE = 0.25; 

    public ThiefRobot(int location, String color, int x, int y) {
        super(location, color, x, y);
    }

    /**
     * Decide el próximo movimiento basándose en la tienda más cercana con tenges.
     */
    @Override
    public int decideNextMove(List<Store> stores) {
        Store targetStore = null;
        int minDistance = Integer.MAX_VALUE;
        for (Store store : stores) {
            if (store.getTenges() > 0) {
                int distance = Math.abs(store.getLocation() - this.location);
                if (distance > 0 && distance < minDistance) {
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

    /**
     * Intenta robar a otro robot en la misma ubicación.
     * @param otherRobot El robot al que intentar robar.
     * @return La cantidad de tenges robados.
     */
    public int stealFrom(Robot otherRobot) {
        if (otherRobot != null && otherRobot != this && otherRobot.getTenges() > 0) {
            int stolenAmount = (int) Math.round(otherRobot.getTenges() * STEAL_PERCENTAGE);
            if (stolenAmount > 0) {
                otherRobot.addTenges(-stolenAmount); 
                this.addTenges(stolenAmount);
                System.out.println("ThiefRobot en " + this.location + " robó " + stolenAmount + " tenges!");
                return stolenAmount;
            }
        }
        return 0;
    }
}
