package silkRoad; 

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
    
    @Test
    public void accordingNAShouldPrioritizeHigherTengesStore() {
        int[][] marathonInput = {
            {1, 0},
            {2, 5, 10},  
            {2, 20, 100} 
        };

        int[] expectedProfits = {0, 10, 110};
        int[] actualProfits = contest.solve(marathonInput);
        
        assertArrayEquals(expectedProfits, actualProfits, "El robot debe elegir la tienda que maximiza la ganancia.");
    }
}
