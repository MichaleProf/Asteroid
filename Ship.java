import java.awt.*;
import java.awt.geom.AffineTransform;

public class Ship extends Entity {
    private double acceleration = 0.5;
    private int maxSpeed = 4;
    Game game;




    public Ship(int x, int y, int angle, Game game) {
        super(x, y, angle);
        this.game = game;
    }

    @Override
    public void move() {

        double tempAcceleration = acceleration;
        if(!game.isUp())
            tempAcceleration /= -10;
        setSpeed(getSpeed() + tempAcceleration);
        if(getSpeed() > maxSpeed)
            setSpeed((double)maxSpeed);
        if(getSpeed() < 0)
            setSpeed(0);

        super.move();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(getColor());

        //ship coordinates for drawing, not adjusted for the rotation or location of ship
        int[] xpoints = {-6, 0, 6, 0};
        int[] ypoints = {12, -12, 12, 6};

        Polygon ship = new Polygon(xpoints, ypoints, 4);

        //rotating the drawing of the ship
        AffineTransform rotater = new AffineTransform();
        rotater.rotate(Math.toRadians(getAngle()));
        Shape newShip = rotater.createTransformedShape(ship);

        //moving the drawing of the ship to its location
        AffineTransform translater = new AffineTransform();
        translater.translate(getX(), getY());
        setShape(translater.createTransformedShape(newShip));

        g2d.draw(getShape());
    }


}
