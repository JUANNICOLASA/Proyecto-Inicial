package silkRoad;

import shapes.*;
import java.util.List;
import java.util.Comparator;

/**
 * Un robot que solo toma la mitad del dinero de las tiendas.
 * Requisito del Ciclo 4.
 */
public class TenderRobot extends Robot {

    public TenderRobot(int location, String color, int x, int y) {
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
