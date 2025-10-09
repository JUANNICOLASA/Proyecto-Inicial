import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

/**
 * Pruebas de unidad para la clase SilkRoadContest.
 */
public class SilkRoadContestTest {
    private SilkRoadContest contest;

    @BeforeEach
    public void setUp() {
        contest = new SilkRoadContest();
    }

    @AfterEach
    public void tearDown() {
        contest = null;
    }

    /**
     * Prueba el método solve con la entrada de ejemplo del problema de la maratón.
     * NOTA: El resultado esperado [0, 15, 65, 65, 85, 115] se basa en un algoritmo voraz
     * que prioriza las tiendas con más tenges, asignándolas al robot disponible más cercano.
     * Este es un enfoque válido para "maximizar la ganancia".
     */
    @Test
    public void accordingNAShouldSolveMarathonProblem() {
        int[][] marathonInput = {
            {1, 20},
            {2, 15, 15},
            {2, 40, 50},
            {1, 50},
            {2, 80, 20},
            {2, 70, 30}
        };

        int[] expectedProfits = {0, 15, 65, 65, 85, 115};
        int[] actualProfits = contest.solve(marathonInput);

        assertArrayEquals(expectedProfits, actualProfits, "Las ganancias diarias deben coincidir con la solución del algoritmo implementado.");
    }
    
    /**
     * Prueba un escenario simple donde un robot debe elegir la tienda con más tenges,
     * no necesariamente la más cercana.
     */
    @Test
    public void accordingNAShouldPrioritizeHigherTengesStore() {
        // Día 1: Robot en 0
        // Día 2: Tienda cercana con pocos tenges
        // Día 3: Tienda lejana con muchos tenges
        int[][] marathonInput = {
            {1, 0},
            {2, 5, 10},  // Tienda A: loc 5, tenges 10
            {2, 20, 100} // Tienda B: loc 20, tenges 100
        };

        // Día 1: Profit 0
        // Día 2: Robot se mueve a la tienda A (la única). Profit = 10.
        // Día 3: Se añade la tienda B. El robot (ahora en 5) se mueve a la B. Profit = 10 + 100 = 110.
        int[] expectedProfits = {0, 10, 110};
        int[] actualProfits = contest.solve(marathonInput);
        
        assertArrayEquals(expectedProfits, actualProfits, "El robot debe elegir la tienda que maximiza la ganancia.");
    }
}