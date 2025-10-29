package silkRoad; 

import shapes.Canvas; 
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JOptionPane;

/**
 * Pruebas de Aceptación (Atest) para el Ciclo 4.
 * Estas pruebas son visuales, usan pausas y preguntan al usuario.
 */
public class SilkRoadAtest {

    private SilkRoad silkRoad;
    private final int PAUSA = 2000; 

    @BeforeEach
    public void setUp() {
        silkRoad = new SilkRoad(100);
        silkRoad.makeVisible(); 
    }

    @AfterEach
    public void tearDown() {
        silkRoad.finish();
        silkRoad = null;
    }

    /**
     * Prueba de Aceptación 1
     * Evidencia la diferencia visual del ThiefRobot y la lógica de robo.
     */
    @Test
    public void acceptanceTestTheGreatHeist() {
        silkRoad.placeStore("normal", 20, 100);
        silkRoad.placeRobot("normal", 10); 
        
        Canvas.getCanvas().wait(PAUSA);
        
        silkRoad.moveRobot(10, 10, true); 
        assertEquals(100, silkRoad.robots()[0][1]);
        
        Canvas.getCanvas().wait(PAUSA);

        silkRoad.placeRobot("thief", 0); 
        
        Canvas.getCanvas().wait(PAUSA);
        
        silkRoad.moveRobot(0, 20, true); 

        silkRoad.blinkTopRobot(); 
        
        silkRoad.placeStore("normal", 1, 1000);
        silkRoad.moveRobot(20, -19, false); 
        silkRoad.blinkTopRobot(); 

        int r = JOptionPane.showConfirmDialog(null, 
            "¿Vio al robot gris oscuro (Ladrón) moverse?\n" +
            "¿Vio que el ladrón (gris) parpadeó al final por ser el más rico?\n\n¿Acepta la prueba?", 
            "Prueba de Aceptación: Ladrón", 
            JOptionPane.YES_NO_OPTION);
            
        assertEquals(JOptionPane.YES_OPTION, r, "El usuario no aceptó la prueba visual.");
    }
    
    /**
     * Prueba de Aceptación 2
     * Evidencia la diferencia visual y lógica de FighterStore y AutonomousStore.
     */
    @Test
    public void acceptanceTestSpecialStores() {
        silkRoad.placeStore("fighter", 10, 100);
        
        silkRoad.placeStore("autonomous", 1, 100);
        
        Canvas.getCanvas().wait(PAUSA);
        
        silkRoad.placeRobot("tender", 0); 
        Canvas.getCanvas().wait(PAUSA);

        silkRoad.moveRobot(0, 10, true); 
        Canvas.getCanvas().wait(PAUSA);
        
        int[][] currentStores = silkRoad.stores();
        int autoLocation;
        
        if (currentStores[0][0] == 10) {
            autoLocation = currentStores[1][0]; 
        } else {
            autoLocation = currentStores[0][0]; 
        }

        silkRoad.moveRobot(10, autoLocation - 10, true);
        
        assertEquals(50, silkRoad.robots()[0][1], "El robot tierno debió recoger 50 (la mitad de 100).");
        
        Canvas.getCanvas().wait(PAUSA);
        
        silkRoad.moveRobot(autoLocation, 10 - autoLocation, true);
        
        assertEquals(50, silkRoad.robots()[0][1], "El robot (con 50) no debió recoger de la tienda (con 100).");

        int r = JOptionPane.showConfirmDialog(null, 
            "¿Vio la tienda Roja (Luchadora) y la Amarilla (Autónoma)?\n" +
            "¿Vio que la Amarilla NO apareció en la locación 1?\n" +
            "¿Vio que el robot Rosa (Tierno) fue RECHAZADO 2 VECES por la tienda Roja?\n\n¿Acepta la prueba?", 
            "Prueba de Aceptación: Tiendas", 
            JOptionPane.YES_NO_OPTION);
            
        assertEquals(JOptionPane.YES_OPTION, r, "El usuario no aceptó la prueba visual.");
    }
}