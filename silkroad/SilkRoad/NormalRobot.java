package SilkRoad;

import Shapes.*;
import java.util.List;
import java.util.Comparator;

/**
 * Un robot "normal" que implementa la lógica de movimiento del Ciclo 2.
 * Hereda de Robot (Ciclo 4).
 */
public class NormalRobot extends Robot {

    public NormalRobot(int location, String color, int x, int y) {
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
}
