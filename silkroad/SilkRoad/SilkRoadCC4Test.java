package SilkRoad; 

import Shapes.*; 
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas de unidad "Comunes" (CC) para el Ciclo 4.
 * Prueban combinaciones de las nuevas clases.
 *
 */
public class SilkRoadCC4Test {
    
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

    /**
     * Prueba de Nomenclatura Común (accordingDAShould...).
     * Verifica la interacción entre un TenderRobot y una FighterStore.
     *
     */
    @Test
    public void accordingXYZShouldTestTenderVsFighter() {
        silkRoad.placeStore("fighter", 10, 100); 
        silkRoad.placeRobot("tender", 0);

        silkRoad.moveRobot(0, 10, false);
        
        assertEquals(0, silkRoad.profit(), "TenderRobot (pobre) no debe poder recoger de FighterStore.");
        assertEquals(10, silkRoad.robots()[0][0]);
        assertEquals(0, silkRoad.robots()[0][1]);
        
        silkRoad.placeStore("normal", 20, 500);
        silkRoad.moveRobot(10, 10, false); 
        
        assertEquals(250, silkRoad.profit(), "TenderRobot debe recoger 250 de la tienda normal.");
        assertEquals(250, silkRoad.robots()[0][1]);
        
        silkRoad.moveRobot(20, -10, false); 
        
        assertEquals(250 + 50, silkRoad.profit(), "TenderRobot (rico) debe recoger la MITAD de la FighterStore.");
        assertEquals(250 + 50, silkRoad.robots()[0][1], "Robot debe tener 300 tenges.");
        assertEquals(0, silkRoad.stores()[0][1], "FighterStore debe quedar vacía.");
    }

    /**
     * Prueba de Nomenclatura Común.
     * Verifica que un ThiefRobot robe a un NeverbackRobot y que este último
     * no pueda moverse hacia atrás a una tienda.
     *
     */
    @Test
    public void accordingXYZShouldTestThiefVsNeverback() {
        silkRoad.placeStore("normal", 20, 100);
        silkRoad.placeRobot("neverback", 10); 

        silkRoad.moveRobot(10, 10, false);
        assertEquals(100, silkRoad.robots()[0][1], "Neverback debe tener 100 tenges.");
        
        silkRoad.placeRobot("thief", 0); 
        
        silkRoad.moveRobot(0, 20, false);
        
        assertEquals(75, silkRoad.robots()[0][1], "Neverback (víctima) debe tener 75.");
        assertEquals(25, silkRoad.robots()[1][1], "Thief (ladrón) debe tener 25.");
        
        silkRoad.placeStore("normal", 5, 1000);
        
        silkRoad.moveRobots(); 
        
        int thiefLocation = -1;
        if(silkRoad.robots()[0][0] == 5) thiefLocation = 0;
        if(silkRoad.robots()[1][0] == 5) thiefLocation = 1;
        
        assertTrue(thiefLocation != -1, "El Ladrón debió moverse a la locación 5.");
        assertEquals(20, silkRoad.robots()[1 - thiefLocation][0], "El Neverback NO debió moverse de la locación 20.");
    }
}