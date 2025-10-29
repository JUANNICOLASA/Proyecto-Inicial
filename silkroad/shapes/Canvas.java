package shapes;

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

    public void draw(Object referenceObject, String color, java.awt.Shape shape) {
        objects.remove(referenceObject);
        objects.add(referenceObject);
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }

    public void erase(Object referenceObject) {
        objects.remove(referenceObject);
        shapes.remove(referenceObject);
        redraw();
    }
    
    public void erase() {
        objects.clear();
        shapes.clear();
        redraw();
    }

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
        else if (colorString.equals("darkGray")) graphic.setColor(Color.DARK_GRAY);
        else graphic.setColor(Color.black);
    }

    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
        }
    }

    private void redraw() {
        erase(canvas);
        for (Object shape : objects) {
            shapes.get(shape).draw(graphic);
        }
        canvas.repaint();
    }

    private void erase(JComponent component) {
        Dimension size = component.getSize();
        if (graphic != null) {
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
        }
    }

    private class CanvasPane extends JComponent {
        public void paint(Graphics g) {
            if (g != null) {
                g.drawImage(canvasImage, 0, 0, null);
            }
        }
    }

    private class ShapeDescription {
        private java.awt.Shape shape;
        private String colorString;

        public ShapeDescription(java.awt.Shape shape, String color) {
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic) {
            setForegroundColor(colorString);
            graphic.fill(shape);
        }
    }
}
