import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;


public class Board extends JPanel implements ActionListener {

    Game game;
    Timer timer;

    Color baseColor = new Color(71, 202, 209);

    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;

    ArrayList<Entity> actors;


    private int maxBullets = 3;
    ArrayList<Bullet> bullets = new ArrayList<>();

    ArrayList<Integer> removeList = new ArrayList<>();
    ArrayList<Integer> bulletRemoveList = new ArrayList<>();




    private int respawnTimer = 60 * 3;

    private int[] xpoints = {-6*3, 0, 6*3, 0};
    private int[] ypoints = {12*3, -12*3, 12*3, 6*3};
    private Shape lifeToken = new Polygon(xpoints, ypoints, 4);


    public Board(Game game){
        this.game = game;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        timer = new Timer(1000/60, this);
        setup();
        timer.start();
    }

    public void setup(){
        actors = new ArrayList<>();

        actors.add(new Ship(WIDTH/2, HEIGHT/2, 0, game));

        spawnAsteroids();


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();

        collisionDetection();

        if(respawnTimer != 0) {
            respawnTimer--;
            actors.get(0).setColor(Color.YELLOW);
        }
        else
            actors.get(0).setColor(baseColor);

        //clear bullets array
        for(int i = 0; i < bullets.size(); i++){
            int x = bullets.get(i).getRawX();
            int y = bullets.get(i).getRawY();
            if (x < 0 || x > WIDTH || y < 0 || y > HEIGHT) {
                bulletRemoveList.add(i);
            }

        }
        removeFrom();
        /*for(int i = 0; i < removeList.size(); i++){
            bullets.remove(removeList.get(i) - i);
        }
        removeList = new ArrayList<>();*/

        //ship and asteroid movement
        for(int i = 0; i < actors.size(); i++)
            actors.get(i).move();

        //ship turning
        Entity ship = actors.get(0);
        if(game.isRight())
            ship.rotate(false);
        if(game.isLeft())
            ship.rotate(true);

        //bullet movement
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).move();
        }

        //bullet firing
        if(respawnTimer == 0) {
            if (game.isSpace())
                if (!actors.get(0).isRecoil())
                    fire();
            if (!game.isSpace())
                actors.get(0).setRecoil(false);
        }

        updateLevel();


     }

    public void fire(){
        Entity ship = actors.get(0);

        //fire bullet
        if(bullets.size() <= maxBullets){
            bullets.add(new Bullet(ship.getX(), ship.getY(), ship.getAngle()));
            ship.setRecoil(true);
        }

    }

    public void collisionDetection(){
        Entity ship = actors.get(0);

        //player collision detection
        if(respawnTimer == 0) {
            for (int i = 1; i < actors.size(); i++) {
                if (actors.get(0).collidesWith(actors.get(i).getBounds())) {
                    actors.get(0).setX(WIDTH / 2);
                    actors.get(0).setY(HEIGHT / 2);
                    actors.get(0).setLives(actors.get(0).getLives() - 1);
                    respawnTimer = 60 * 3;
                }
            }
        }

        int presize = actors.size();
        for(int i = 0; i < bullets.size(); i++){
            for(int j = 1; j < presize; j++){
                if(bullets.get(i).collidesWith(actors.get(j).getBounds()) && !bullets.get(i).getColor().equals(Color.BLACK)){
                    if(actors.get(j).getSize() != 1) {
                        actors.add(new Asteroid(actors.get(j).getX(), actors.get(j).getY(), actors.get(j).getSize() - 1));
                        actors.add(new Asteroid(actors.get(j).getX(), actors.get(j).getY(), actors.get(j).getSize() - 1));
                    }
                    STATS.addScore(300 + ((3-actors.get(j).getSize())*200) + (STATS.getLevel() * 100));
                    removeList.add(j);
                    bulletRemoveList.add(i);
                    bullets.get(i).setColor(Color.BLACK);
                }
            }
        }
        removeFrom();
    }

    //checks if the center of the circle is within the rectangle, or whether any of the rectangle's points lies within the circle
    /*public boolean collidesWith(Shape circle, Shape rectangle){
        boolean temp;

        double cx = circle.getBounds().getX() + circle.getBounds().getWidth()/2;
        double cy = circle.getBounds().getY() + circle.getBounds().getHeight()/2;

        double rx = rectangle.getBounds().getX() + rectangle.getBounds().getWidth()/2;
        double ry = rectangle.getBounds().getY() + rectangle.getBounds().getHeight()/2;

        //distances of all the points of the rectangle to the center of the circle
        double d1 = Math.sqrt(Math.pow(rx-3-cx ,2) + Math.pow(ry-6-cy , 2));
        double d2 = Math.sqrt(Math.pow(rx-3-cx ,2) + Math.pow(ry+6-cy , 2));
        double d3 = Math.sqrt(Math.pow(rx+3-cx ,2) + Math.pow(ry-6-cy , 2));
        double d4 = Math.sqrt(Math.pow(rx+3-cx ,2) + Math.pow(ry+6-cy , 2));

        temp = rectangle.contains(cx, cy);
        temp = temp ||  25 > d1 || 25 > d2 || 25 > d3 || 25 > d4;
        return temp;
    }*/

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(actors.get(0).getLives() > 0) {
            drawLives(g);

            //asteroid and ship painting
            for (int i = 0; i < actors.size(); i++) {
                actors.get(i).paint(g);
            }

            //bullet painting
            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).paint(g);
            }

            showScore(g);
        }
        else
            drawEndScreen(g);
    }

    public void drawEndScreen(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        Font font = new Font("Serif", Font.PLAIN, 50);
        g2d.setFont(font);
        g2d.setColor(new Color(71, 202, 209));

        g2d.drawString("Final Score: " + STATS.getScore(), 20, HEIGHT/2-25);

    }

    public void showScore(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        Font font = new Font("Serif", Font.PLAIN, 50);
        g2d.setFont(font);
        g2d.setColor(Color.GREEN);

        g2d.drawString(STATS.getScore() + "", 20, HEIGHT - 70);


    }

    public void removeFrom(){

        int originalLength = removeList.size();
        for(int i = 0; i < originalLength; i++){
            actors.remove(removeList.get(i) - i);
        }
        removeList = new ArrayList<>();

        originalLength = bulletRemoveList.size();
        for(int i = 0; i < originalLength; i++){
            System.out.println((bulletRemoveList.get(i) - i )+ " " + i);
            bullets.remove(bulletRemoveList.get(i) - i);
        }
        bulletRemoveList = new ArrayList<>();

    }

    private void drawLives(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.RED);

        for(int x = 30; x < (actors.get(0).getLives())*54+30; x+=54){
            AffineTransform translate = new AffineTransform();
            translate.translate(x, 50);
            Shape tempToken = translate.createTransformedShape(lifeToken);
            g2d.draw(tempToken);
        }
    }

    public void spawnAsteroids(){
        for(int i = 0; i < STATS.getLevel()+3; i++)
            actors.add(new Asteroid(3));
    }

    public void updateLevel(){
        if(actors.size() == 1){
            STATS.levelUp();
            spawnAsteroids();
            respawnTimer = 60 * 3;
        }
    }


}
