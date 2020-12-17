package MainPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
/**
 * Paddle - class realize paddle object with run method
 */

public class Paddle extends JComponent implements Runnable {

    public int x;
    public int y = (Main.height -30)/2 - height/2;
    public static int weight = 30;
    public static int height = 150;
    private static final Color color = Color.GREEN;
    private final int playerId;
    public ColliderBox paddleColliderBox;

    private static final int speed = 2;
    private BufferedImage paddleTexture;

    /**
     * Constructor with parameters
     * @param x - x coordinate
     * @param playerId - id of player
     */
    public Paddle(int x, int playerId)
    {
        super();
        this.playerId = playerId;
        this.x = x;
        this.paddleColliderBox = new ColliderBox(this.x, this.y, weight, height);
        setLocation(this.x,this.y);
        setSize(new Dimension(weight,height));
        try{
            paddleTexture = ImageIO.read(new File("src/textures/paddle.png"));
        }
        catch (Exception e)
        {

        }
    }

    /**
     * Main thread method wo control puddle
     */
    @Override
    public void run() {
        while (Main.isRunning) {
            switch (playerId) {
                case 1 -> {
                    if (Main.pressedW && this.getLocation().y - speed > 0) {
                        this.setLocation(this.getLocation().x, this.getLocation().y - speed);
                    }
                    if (Main.pressedS && this.getLocation().y + speed < Main.height - height - 35) {
                        this.setLocation(this.getLocation().x, this.getLocation().y + speed);
                    }
                }
                case 2 -> {
                    if (Main.pressedUpArrow && this.getLocation().y - speed > 0) {
                        this.setLocation(this.getLocation().x, this.getLocation().y - speed);
                    }
                    if (Main.pressedDownArrow && this.getLocation().y + speed < Main.height - height - 35) {
                        this.setLocation(this.getLocation().x, this.getLocation().y + speed);
                    }
                }
            }
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Draw method
     * @param g - object of Graphics type
     */

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(color);
        g2.setColor(color);
        Rectangle2D rect = new Rectangle2D.Double(0, 0, weight, height);
        var paddleTextureTp = new TexturePaint(paddleTexture, new Rectangle(0,0,weight,height));
        g2.setPaint(paddleTextureTp);
        g2.draw(rect);
        g2.fill(rect);
    }
}
