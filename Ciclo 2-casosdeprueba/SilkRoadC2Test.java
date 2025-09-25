import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Casos de prueba de unidad para la clase SilkRoad (Ciclo 2).
 * Las pruebas se ejecutan en modo invisible para verificar la lógica interna del simulador.
 * Se incluyen pruebas de lo que el sistema "debería hacer" y "no debería hacer".
 */
public class SilkRoadC2Test {

    private SilkRoad sr;

    // Este método se ejecuta antes de cada @Test, asegurando que cada prueba
    // comience con una instancia nueva y limpia de SilkRoad.
    @BeforeEach
    public void setUp() {
        sr = new SilkRoad(200);
    }

    // Pruebas de "Qué debería hacer" (Casos de éxito)
    // ----------------------------------------------------

    @Test
    public void shouldPlaceStoreAndRobotSuccessfully() {
        // Arrange: El 'sr' ya está creado en setUp.
        // Act
        sr.placeStore(50, 100);
        sr.placeRobot(20);
        // Assert
        assertTrue(sr.ok(), "La operación debería ser exitosa.");
        assertEquals(1, sr.stores().length, "Debería haber una tienda en la ruta.");
        assertEquals(1, sr.robots().length, "Debería haber un robot en la ruta.");
    }

    @Test
    public void shouldUpdateProfitWhenRobotReachesStore() {
        // Arrange
        sr.placeStore(50, 1200);
        sr.placeRobot(30);
        // Act
        sr.moveRobot(30, 20); // Mueve el robot de la pos 30 a la 50.
        // Assert
        assertEquals(1200, sr.profit(), "La ganancia debe ser igual al valor de la tienda.");
    }
    
    @Test
    public void shouldIncrementEmptiedCounterAfterCollection() {
        // Arrange
        sr.placeStore(80, 500);
        sr.placeRobot(10);
        // Act
        sr.moveRobot(10, 70); // Robot se mueve a la tienda y la vacía.
        // Assert
        int[][] emptiedData = sr.emptiedStores();
        assertEquals(1, emptiedData[0][1], "El contador de la tienda vaciada debe ser 1.");
    }
    
    @Test
    public void shouldRecordProfitHistoryForEachMove() {
        // Arrange
        sr.placeStore(20, 100);
        sr.placeStore(50, 300);
        sr.placeRobot(10);
        // Act
        sr.moveRobot(10, 10);  // Se mueve a la tienda en 20, gana 100
        sr.moveRobot(20, 15);  // Se mueve a 35, no gana nada
        sr.moveRobot(35, 15);  // Se mueve a la tienda en 50, gana 300
        // Assert
        int[][] profitHistory = sr.profitPerMove();
        assertEquals(100, profitHistory[0][1], "El primer movimiento debería registrar 100 de ganancia.");
        assertEquals(0, profitHistory[0][2], "El segundo movimiento debería registrar 0 de ganancia.");
        assertEquals(300, profitHistory[0][3], "El tercer movimiento debería registrar 300 de ganancia.");
    }

    @Test
    public void shouldRebootToInitialState() {
        // Arrange
        sr.placeStore(100, 1000);
        sr.placeRobot(10);
        sr.moveRobot(10, 90); // El robot recoge el dinero
        // Act
        sr.reboot();
        // Assert
        assertEquals(0, sr.profit(), "La ganancia debería ser 0 después de reiniciar.");
        assertEquals(1000, sr.stores()[0][1], "La tienda debería reabastecerse.");
        assertEquals(10, sr.robots()[0][0], "El robot debería volver a su posición inicial.");
    }
    
    // Pruebas de "Qué no debería hacer" (Casos de error y borde)
    // ---------------------------------------------------------

    @Test
    public void shouldNotPlaceStoreOnAnOccupiedLocation() {
        // Arrange
        sr.placeStore(50, 100);
        // Act
        sr.placeStore(50, 200); // Intenta colocar otra tienda en el mismo lugar.
        // Assert
        assertFalse(sr.ok(), "La operación debe fallar al colocar una tienda en una ubicación ocupada.");
        assertEquals(1, sr.stores().length, "No se debería haber añadido la segunda tienda.");
    }
    
    @Test
    public void shouldNotMoveARobotThatDoesNotExist() {
        // Arrange
        sr.placeRobot(10);
        // Act
        sr.moveRobot(99, 10); // Intenta mover un robot desde una ubicación incorrecta (99).
        // Assert
        assertFalse(sr.ok(), "La operación debe fallar si el robot no existe en la ubicación de origen.");
    }
    
    @Test
    public void shouldHandleMovementToEmptyLocationGracefully() {
        // Arrange
        sr.placeRobot(10);
        // Act
        sr.moveRobot(10, 20); // Mueve el robot a la ubicación 30, donde no hay nada.
        // Assert
        assertTrue(sr.ok(), "La operación de moverse a un lugar vacío debe ser exitosa.");
        assertEquals(0, sr.profit(), "La ganancia debe permanecer en 0.");
        assertEquals(30, sr.robots()[0][0], "El robot debería estar en su nueva ubicación.");
    }
    
    @Test
    public void shouldHandleCreationFromEmptyMarathonInput() {
        // Arrange
        int[][] emptyData = new int[0][0];
        // Act
        SilkRoad emptySr = new SilkRoad(emptyData);
        // Assert
        assertNotNull(emptySr, "El objeto SilkRoad no debería ser nulo.");
        assertEquals(0, emptySr.stores().length, "No debería haber tiendas.");
    }
}