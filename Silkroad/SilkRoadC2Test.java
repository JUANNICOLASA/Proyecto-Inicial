import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

/**
 * The test class SilkRoadC2Test.
 * Estas pruebas verifican la funcionalidad básica de simulación de la clase SilkRoad.
 */
public class SilkRoadC2Test {
    private SilkRoad silkRoad;

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp() {
        silkRoad = new SilkRoad(100); 
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown() {
        silkRoad.finish();
        silkRoad = null;
    }

    /**
     * Verifica que el profit total se calcula correctamente después de varios movimientos.
     */
    @Test
    public void ShowRobotProfitsLRShouldDisplayCorrectProfits() {
        silkRoad.placeRobot(0);
        silkRoad.placeStore(1, 50);
        silkRoad.placeStore(3, 100);

        silkRoad.moveRobot(0, 1); 
        silkRoad.moveRobot(1, 2); 
        
        assertEquals(150, silkRoad.profit());
    }

    /**
     * Verifica que el método 'create' y 'profit' funcionan correctamente con una entrada compleja.
     */
    @Test
    public void profitLRShouldCalculateTotalTengesFromAllRobots() {
        int[] input = {2, 1, 100, 5, 200, 0, 6};
        silkRoad.create(input); 
        
        silkRoad.moveRobots(); 

        assertEquals(300, silkRoad.profit(), "El profit total debería ser la suma de todas las tiendas recolectadas.");
        assertTrue(silkRoad.ok());
    }

    /**
     * Verifica que la operación falle si no hay un movimiento beneficioso para un robot.
     */
    @Test
    public void shouldNotMoveRobotWhenNoGoodMoveAvailable() {
        silkRoad.placeStore(3, 0); 
        silkRoad.placeRobot(0);
        
        silkRoad.moveRobots(); 
        
        assertFalse(silkRoad.ok(), "La operación debería fallar si no hay movimientos válidos.");
        assertEquals("No hay movimiento beneficioso disponible para el robot", silkRoad.getLastError());
    }
    
    /**
     * Verifica que profitPerMove rastrea las ganancias y devuelve la ubicación ACTUAL del robot.
     */
    @Test 
    public void shouldTrackRobotProfitsPerMove() {
        silkRoad.placeStore(10, 30);
        silkRoad.placeStore(20, 40);
        silkRoad.placeRobot(5);

        silkRoad.moveRobots();
        silkRoad.moveRobots();
        
        int[][] profits = silkRoad.profitPerMove();
        
        assertEquals(1, profits.length);
        
        assertEquals(20, profits[0][0], "La primera columna debe ser la ubicación ACTUAL del robot."); 
        
        assertEquals(30, profits[0][1], "La ganancia del primer movimiento debe ser 30.");
        assertEquals(40, profits[0][2], "La ganancia del segundo movimiento debe ser 40.");
    }
    
    /**
     * Prueba que el sistema no permite colocar una tienda en una ubicación ya ocupada.
     */
    @Test
    public void accordingNAShouldNotAllowPlacingStoreOnExistingLocation() {
        silkRoad.placeStore(50, 100);
        assertTrue(silkRoad.ok(), "Colocar la primera tienda debería ser exitoso.");
        
        silkRoad.placeStore(50, 200); 
        assertFalse(silkRoad.ok(), "Colocar una tienda en una ubicación existente debería fallar.");
    }

    /**
     * Prueba que la función reboot reinicia correctamente el estado de la simulación.
     */
    @Test
    public void accordingNAShouldResetSimulationStateOnReboot() {
        silkRoad.placeStore(10, 100);
        silkRoad.placeRobot(0);
        silkRoad.moveRobots(); // El robot se mueve a 10, profit es 100
        
        assertEquals(100, silkRoad.profit());
        assertEquals(10, silkRoad.robots()[0][0]); // El robot está en la ubicación 10
        
        silkRoad.reboot();
        
        assertEquals(0, silkRoad.profit(), "El profit debería ser 0 después de reboot.");
        assertEquals(0, silkRoad.robots()[0][0], "El robot debería volver a su ubicación inicial después de reboot.");
        assertEquals(100, silkRoad.stores()[0][1], "La tienda debería reabastecerse después de reboot.");
    }
}

