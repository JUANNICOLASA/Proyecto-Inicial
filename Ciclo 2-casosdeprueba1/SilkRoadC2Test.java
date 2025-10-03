import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

/**
 * The test class SilkRoadC2Test.
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
}