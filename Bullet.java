import java.awt.*;

public class Bullet extends Entity{
    private int radius = 1;
    //private int arrayIndex;

    public Bullet(int x, int y, int angle ) {
        super(x, y, angle);
        setSpeed(8);
        //this.arrayIndex = arrayIndex;
    }



    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(getColor());

        setShape(new Rectangle(getX()-radius, getY()-radius, radius*2, radius*2 ));

        g2d.draw(getShape());

    }

}
