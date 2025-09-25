public class ProgressBar {
    private Rectangle backgroundBar;
    private Rectangle foregroundBar;
    private int maxWidth = 780; 

    public ProgressBar() {
        backgroundBar = new Rectangle(10, 10, maxWidth, 20);
        foregroundBar = new Rectangle(12, 12, 0, 16);
    }

    public void update(int currentValue, int maxValue) {
        int newWidth;
        if (maxValue <= 0) {
            newWidth = 0;
        } else {
            newWidth = (int) (((double) currentValue / maxValue) * (maxWidth - 4));
        }
        foregroundBar.changeSize(16, newWidth);
    }
    
    public void makeVisible(){
        backgroundBar.changeColor("black");
        foregroundBar.changeColor("green");
        backgroundBar.makeVisible();
        foregroundBar.makeVisible();
    }
    
    public void makeInvisible(){
        backgroundBar.makeInvisible();
        foregroundBar.makeInvisible();
    }
}