import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example.
 *
 * @author: Bruce Quig
 * @author: Michael Kolling (mik)
 * @version: 1.6 (shapes)
 */
public class Canvas {
    private static Canvas canvasSingleton;

    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("Silk Road Simulation", 800, 600, Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    // ----- instance part -----
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

    public void draw(Object referenceObject, String color, Shape shape) {
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
        else graphic.setColor(Color.black);
    }

    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            // ignoring exception at the moment
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
            g.drawImage(canvasImage, 0, 0, null);
        }
    }

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