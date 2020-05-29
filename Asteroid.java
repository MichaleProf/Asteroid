import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;

public class Asteroid extends Entity {

    private int[] subFinalXPoints, subFinalYPoints;
    private int radius;

    public Asteroid(int size) {
        super();
        setSize(size);
        radius = size * 40;

        setSpeed(1);
        setX(setCoordinate());
        setY(setCoordinate());
        setAngle((int)(Math.random()*360));

        makeShape();
    }

    public Asteroid(int x, int y, int size){
        super();
        setSize(size);
        radius = size * 40;

        setSpeed(1 + ((3-size) * 1));
        setX(x);
        setY(y);
        setAngle((int)(Math.random()*360));

        makeShape();
    }

    private int setCoordinate(){
        int temp = (int)(Math.random() * 900);
        if(temp > 450)
            temp+=100;
        return temp;
    }

    private void makeShape(){
        //System.out.println("hey");
        ArrayList<Integer> xpoints = new ArrayList<>();
        ArrayList<Integer> ypoints = new ArrayList<>();

        int minRadius = 20 * getSize();
        int maxRadius = 40 * getSize();
        int minVary = 30;
        int maxVary = 40;
        for(double angle = 0; angle < 360; angle += minVary + Math.random() * (maxVary - minVary)){
            int length = minRadius + (int)(Math.random() * (maxRadius - minRadius));
            //System.out.println(length);
            xpoints.add((int)(length * Math.cos(Math.toRadians(angle))));
            ypoints.add((int)(length * Math.sin(Math.toRadians(angle))));
        }

        subFinalXPoints = new int[xpoints.size()];
        subFinalYPoints = new int[ypoints.size()];

        for(int i = 0; i < xpoints.size(); i++){
            subFinalXPoints[i] = xpoints.get(i);
            subFinalYPoints[i] = ypoints.get(i);
            //System.out.println("(" + subFinalXPoints[i] + "," + subFinalYPoints[i] + ")");
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(getColor());

        int[] finalXPoints = new int[subFinalXPoints.length];
        int[] finalYPoints = new int[subFinalYPoints.length];

        for(int i = 0; i < finalXPoints.length; i++){
            finalXPoints[i] = subFinalXPoints[i] + getX();
            finalYPoints[i] = subFinalYPoints[i] + getY();
        }

        setShape(new Polygon(finalXPoints, finalYPoints, finalXPoints.length));

        g2d.draw(getShape());


        //g.fillOval(getX(), getY(), size*25, size*25);
    }

    public int getRadius() {
        return radius;
    }
}
