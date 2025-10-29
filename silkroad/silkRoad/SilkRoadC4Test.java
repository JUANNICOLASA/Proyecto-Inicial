package silkRoad; 

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

/**
 * Pruebas de unidad para los nuevos requisitos del Ciclo 4.
 */
public class SilkRoadC4Test {
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
    public void shouldTenderRobotCollectHalfTenges() {
        silkRoad.placeRobot("tender", 0);
        silkRoad.placeStore("normal", 10, 100);
        
        silkRoad.moveRobot(0, 10, false); 
        
        assertEquals(50, silkRoad.profit(), "TenderRobot solo debe recolectar la mitad (100 / 2 = 50)");
        assertEquals(50, silkRoad.robots()[0][1], "El robot debe tener 50 tenges.");
        assertEquals(0, silkRoad.stores()[0][1], "La tienda debe quedar vacía.");
    }
    
    @Test
    public void shouldTenderRobotCollectHalfTengesOddNumber() {
        silkRoad.placeRobot("tender", 0);
        silkRoad.placeStore("normal", 10, 101);
        
        silkRoad.moveRobot(0, 10, false);
        
        assertEquals(51, silkRoad.profit(), "TenderRobot debe redondear hacia arriba (101 / 2 = 50.5 -> 51)");
        assertEquals(51, silkRoad.robots()[0][1]);
    }

    @Test
    public void shouldNeverbackRobotNotMoveBackwards() {
        silkRoad.placeStore("normal", 10, 100); 
        silkRoad.placeStore("normal", 1, 100); 
        silkRoad.placeRobot("neverback", 5);
        
        silkRoad.moveRobots(); 
        
        assertEquals(100, silkRoad.profit(), "El robot debe moverse a la tienda 10.");
        assertEquals(10, silkRoad.robots()[0][0], "El robot debe estar en la locación 10.");
        
        silkRoad.moveRobots(); 
        
        assertEquals(100, silkRoad.profit(), "El profit no debe cambiar.");
        assertEquals(10, silkRoad.robots()[0][0], "El robot no debe moverse hacia atrás (a 1).");
        assertFalse(silkRoad.ok(), "Debe fallar porque no hay movimientos válidos.");
    }
    
    @Test
    public void shouldThiefRobotStealFromAnotherRobot() {
        silkRoad.placeRobot("normal", 5); 
        silkRoad.placeStore("normal", 10, 100);
        
        silkRoad.moveRobot(5, 5, false);
        assertEquals(100, silkRoad.robots()[0][1], "La víctima debe tener 100 tenges.");
        
        silkRoad.placeRobot("thief", 0); 
        
        silkRoad.moveRobot(0, 10, false); 
        
        assertEquals(100, silkRoad.profit(), "El profit total no cambia, solo se transfiere.");
        assertEquals(75, silkRoad.robots()[0][1], "La víctima debe tener 100 - 25 = 75 tenges.");
        assertEquals(25, silkRoad.robots()[1][1], "El ladrón debe tener 0 + 25 = 25 tenges.");
    }
    

    @Test
    public void shouldFighterStoreDenyPoorRobot() {
        silkRoad.placeRobot("normal", 0); 
        silkRoad.placeStore("fighter", 10, 100); 
        
        silkRoad.moveRobot(0, 10, false);
        
        assertEquals(0, silkRoad.profit(), "FighterStore debe denegar al robot (0 < 100)");
        assertEquals(0, silkRoad.robots()[0][1], "El robot no debe ganar tenges.");
        assertEquals(100, silkRoad.stores()[0][1], "La tienda debe conservar sus tenges.");
    }
    
    @Test
    public void shouldFighterStoreAllowRichRobot() {
        silkRoad.placeRobot("normal", 0);
        silkRoad.placeStore("normal", 5, 200); 
        silkRoad.placeStore("fighter", 10, 100); 
        
        silkRoad.moveRobot(0, 5, false); 
        assertEquals(200, silkRoad.profit(), "El robot debe tener 200 tenges primero.");
        
        silkRoad.moveRobot(5, 5, false); 
        
        assertEquals(300, silkRoad.profit(), "FighterStore debe permitir al robot (200 > 100)");
        assertEquals(300, silkRoad.robots()[0][1], "El robot debe tener 300 tenges.");
    }
    
    @Test
    public void shouldAutonomousStorePlaceAtRandomLocation() {
        boolean changedLocation = false;
        for (int i = 0; i < 10; i++) {
            silkRoad.finish(); 
            silkRoad = new SilkRoad(100);
            
            int requestedLocation = 1;
            silkRoad.placeStore("autonomous", requestedLocation, 100);
            
            if (silkRoad.stores()[0][0] != requestedLocation) {
                changedLocation = true;
                break;
            }
        }
        
        assertTrue(changedLocation, "AutonomousStore debería (muy probablemente) haber elegido una ubicación diferente a 1.");
    }
    
    @Test
    public void shouldNotPlaceAutonomousStoreOnExisting() {
        silkRoad.placeStore("normal", 50, 100);
                
        for (int i = 1; i <= 100; i++) {
            if (i != 50) { 
                silkRoad.placeStore("normal", i, 1);
            }
        }
        
        silkRoad.placeStore("normal", 20, 100);

        silkRoad.placeStore("autonomous", 10, 100);
        int autoLocation = silkRoad.stores()[0][0];
        
        silkRoad.placeStore("normal", autoLocation, 50);
        assertFalse(silkRoad.ok(), "No se debe poder colocar una tienda sobre la autónoma.");
    }
}
