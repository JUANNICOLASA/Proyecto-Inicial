package silkRoad; 

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

/**
 * Pruebas de unidad para la clase SilkRoad.
 * Actualizada para Ciclo 4 para usar los nuevos métodos.
 */
public class SilkRoadC2Test {
    private SilkRoad silkRoad;

    @BeforeEach
    public void setUp() {
        silkRoad = new SilkRoad(100); 
    }

    @AfterEach
    public void tearDown() {
        silkRoad.finish();
        silkRoad = null;
    }

    @Test
    public void ShowRobotProfitsLRShouldDisplayCorrectProfits() {
        silkRoad.placeRobot("normal", 0);
        silkRoad.placeStore("normal", 1, 50);
        silkRoad.placeStore("normal", 3, 100);

        silkRoad.moveRobot(0, 1, false); 
        silkRoad.moveRobot(1, 2, false); 
        
        assertEquals(150, silkRoad.profit());
    }

    @Test
    public void profitLRShouldCalculateTotalTengesFromAllRobots() {
        int[] input = {2, 1, 100, 5, 200, 0, 6};
        silkRoad.create(input); 
        
        silkRoad.moveRobots(); 

        assertEquals(300, silkRoad.profit(), "El profit total debería ser la suma de todas las tiendas recolectadas.");
        assertTrue(silkRoad.ok());
    }

    @Test
    public void shouldNotMoveRobotWhenNoGoodMoveAvailable() {
        silkRoad.placeStore("normal", 3, 0); 
        silkRoad.placeRobot("normal", 0);
        
        silkRoad.moveRobots(); 
        
        assertFalse(silkRoad.ok(), "La operación debería fallar si no hay movimientos válidos.");
        assertEquals("No hay movimiento beneficioso disponible para el robot", silkRoad.getLastError());
    }
    
    @Test 
    public void shouldTrackRobotProfitsPerMove() {
        silkRoad.placeStore("normal", 10, 30);
        silkRoad.placeStore("normal", 20, 40);
        silkRoad.placeRobot("normal", 5);

        silkRoad.moveRobots();
        silkRoad.moveRobots();
        
        int[][] profits = silkRoad.profitPerMove();
        
        assertEquals(1, profits.length);
        assertEquals(20, profits[0][0], "La primera columna debe ser la ubicación ACTUAL del robot."); 
        assertEquals(30, profits[0][1], "La ganancia del primer movimiento debe ser 30.");
        assertEquals(40, profits[0][2], "La ganancia del segundo movimiento debe ser 40.");
    }
    
    @Test
    public void accordingNAShouldNotAllowPlacingStoreOnExistingLocation() {
        silkRoad.placeStore("normal", 50, 100);
        assertTrue(silkRoad.ok(), "Colocar la primera tienda debería ser exitoso.");
        
        silkRoad.placeStore("normal", 50, 200); 
        assertFalse(silkRoad.ok(), "Colocar una tienda en una ubicación existente debería fallar.");
    }

    @Test
    public void accordingNAShouldResetSimulationStateOnReboot() {
        silkRoad.placeStore("normal", 10, 100);
        silkRoad.placeRobot("normal", 0);
        silkRoad.moveRobots(); 
        
        assertEquals(100, silkRoad.profit());
        assertEquals(10, silkRoad.robots()[0][0]);
        
        silkRoad.reboot();
        
        assertEquals(0, silkRoad.profit(), "El profit debería ser 0 después de reboot.");
        assertEquals(0, silkRoad.robots()[0][0], "El robot debería volver a su ubicación inicial después de reboot.");
        assertEquals(100, silkRoad.stores()[0][1], "La tienda debería reabastecerse después de reboot.");
    }
}
