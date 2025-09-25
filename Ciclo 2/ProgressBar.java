/**
 * Representa una barra de progreso visual en el canvas.
 * Se utiliza para mostrar la ganancia actual vs la máxima posible.
 * @version 1.0
 */
public class ProgressBar {
    private Rectangle backgroundBar;
    private Rectangle foregroundBar;
    private int maxWidth = 780; 

    public ProgressBar() {
        backgroundBar = new Rectangle(10, 10, maxWidth, 20);
        backgroundBar.changeColor("black");
        foregroundBar = new Rectangle(12, 12, 0, 16);
        foregroundBar.changeColor("green");
    }

    /**
     * Actualiza el ancho de la barra de progreso.
     * @param currentValue El valor actual (ej. ganancia actual).
     * @param maxValue El valor máximo posible (ej. ganancia máxima).
     */
    public void update(int currentValue, int maxValue) {
        int newWidth;
        if (maxValue == 0) {
            newWidth = 0;
        } else {
            newWidth = (int) (((double) currentValue / maxValue) * (maxWidth - 4));
        }
        foregroundBar.changeSize(16, newWidth);
    }
    
    public void makeVisible(){
        backgroundBar.makeVisible();
        foregroundBar.makeVisible();
    }
    
    public void makeInvisible(){
        backgroundBar.makeInvisible();
        foregroundBar.makeInvisible();
    }
}