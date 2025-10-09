import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * La clase Canvas proporciona una ventana simple en la que se pueden dibujar
 * objetos. Utiliza un patrón Singleton para asegurar que solo haya una instancia
 * de la ventana.
 */
public class Canvas {
    private static Canvas canvasSingleton;

    /**
     * Método de fábrica para obtener la única instancia del canvas.
     * Si no existe, la crea y la hace visible.
     */
    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("Silk Road Simulation", 800, 600, Color.white);
            canvasSingleton.setVisible(true);
        }
        return canvasSingleton;
    }

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;

    /**
     * Constructor privado para forzar el uso del método getCanvas() (Singleton).
     */
    private Canvas(String title, int width, int height, Color bgColour) {
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList<Object>();
        shapes = new HashMap<Object, ShapeDescription>();
    }

    /**
     * Establece la visibilidad de la ventana.
     */
    public void setVisible(boolean visible) {
        if (graphic == null) {
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D) canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Dibuja una forma en el canvas.
     */
    public void draw(Object referenceObject, String color, Shape shape) {
        objects.remove(referenceObject);
        objects.add(referenceObject);
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }

    /**
     * Borra una forma específica del canvas.
     */
    public void erase(Object referenceObject) {
        objects.remove(referenceObject);
        shapes.remove(referenceObject);
        redraw();
    }
    
    /**
     * Borra todas las formas del canvas.
     */
    public void erase() {
        objects.clear();
        shapes.clear();
        redraw();
    }

    /**
     * Establece el color de primer plano para los dibujos.
     */
    public void setForegroundColor(String colorString) {
        if (colorString.equals("red")) graphic.setColor(Color.red);
        else if (colorString.equals("black")) graphic.setColor(Color.black);
        else if (colorString.equals("blue")) graphic.setColor(Color.blue);
        else if (colorString.equals("yellow")) graphic.setColor(Color.yellow);
        else if (colorString.equals("green")) graphic.setColor(Color.green);
        else if (colorString.equals("magenta")) graphic.setColor(Color.magenta);
        else if (colorString.equals("white")) graphic.setColor(Color.white);
        else if (colorString.equals("orange")) graphic.setColor(Color.orange);
        else if (colorString.equals("cyan")) graphic.setColor(Color.cyan);
        else if (colorString.equals("pink")) graphic.setColor(Color.pink);
        else if (colorString.equals("gray")) graphic.setColor(Color.GRAY);
        else graphic.setColor(Color.black);
    }

    /**
     * Espera un número específico de milisegundos antes de continuar.
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            
        }
    }

    /**
     * Redibuja todas las formas almacenadas en el canvas.
     */
    private void redraw() {
        erase(canvas);
        for (Object shape : objects) {
            shapes.get(shape).draw(graphic);
        }
        canvas.repaint();
    }

    /**
     * Borra el área de un componente específico.
     */
    private void erase(JComponent component) {
        Dimension size = component.getSize();
        if (graphic != null) {
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
        }
    }

    /************************************************************************
     * Clase interna CanvasPane - el componente donde realmente se dibuja.
     */
    private class CanvasPane extends JComponent {
        public void paint(Graphics g) {
            if (g != null) {
                g.drawImage(canvasImage, 0, 0, null);
            }
        }
    }

    /************************************************************************
     * Clase interna ShapeDescription - para almacenar información sobre cada forma.
     */
    private class ShapeDescription {
        private Shape shape;
        private String colorString;

        public ShapeDescription(Shape shape, String color) {
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic) {
            setForegroundColor(colorString);
            graphic.fill(shape);
        }
    }
}