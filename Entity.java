import javafx.scene.shape.Circle;
import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.util.ArrayList;

public abstract class Entity {

    private int size;
    private int x, y, angle;
    private double speed;
    private int rotation = 4;
    private double dx = 0, dy = 0;
    private boolean recoil = false;
    private int lives = 3;
    private Shape shape;
    private ArrayList<Integer> xpoints;
    private ArrayList<Integer> ypoints;
    private Color color = new Color(71, 202, 209);


    public Entity(int x, int y, int angle){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angle = angle;
    }

    public Entity(){}

    public void move(){
        dx += (speed * Math.sin(Math.toRadians(angle)));
        dy += (speed * Math.cos(Math.toRadians(angle)));
        dy *= -1;
        x += (int)dx;
        y += (int)dy;
        dx -= (int)dx;
        dy -= (int)dy;
        dy *= -1;
    }

    public void rotate(boolean left) {
        if(left)
            angle -= rotation;
        else
            angle += rotation;
    }

    public abstract void paint(Graphics g);

    public int getX() {
        int tempX = x % 1000;

        while(tempX < 0)
            tempX += 1000;

        return tempX;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        int tempY = y % 1000;

        while(tempY < 0)
            tempY += 1000;

        return tempY;

        /*y = y % 1000;
        if(y < 0)
            y += 1000;
        return y;*/
    }

    public boolean collidesWith(Rectangle object){
        return getBounds().intersects(object);
    }


    public Rectangle getBounds(){
        return shape.getBounds();
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public boolean isRecoil() {
        return recoil;
    }

    public void setRecoil(boolean recoil) {
        this.recoil = recoil;
    }

    public int getRawX(){return x;}

    public int getRawY(){return y;}

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public ArrayList<Integer> getXpoints() {
        return xpoints;
    }

    public void setXpoints(ArrayList<Integer> xpoints) {
        this.xpoints = xpoints;
    }

    public ArrayList<Integer> getYpoints() {
        return ypoints;
    }

    public void setYpoints(ArrayList<Integer> ypoints) {
        this.ypoints = ypoints;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
