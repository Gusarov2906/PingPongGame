import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Paddle extends JComponent implements Runnable {
    public int x;
    public int y = (Main.height -30)/2 - height/2;
    public static int weight = 20;
    public static int height = 100;
    private static final Color color = Color.GREEN;
    private final int playerId;
    private static final int speed = 1;
    public ColliderBox paddleColliderBox;

    public Paddle(int x, int playerId)
    {
        super();
        this.playerId = playerId;
        this.x = x;
        this.paddleColliderBox = new ColliderBox(this.x, this.y, weight, height);
        setLocation(this.x,this.y);
        setSize(new Dimension(weight,height));
    }

    @Override
    public void run() {
        while (Main.isRunning) {
            switch (playerId) {
                case 1 -> {
                    if (Main.pressedW && this.getLocation().y - speed > 0) {
                        this.setLocation(this.getLocation().x, this.getLocation().y - speed);
                    }
                    if (Main.pressedS && this.getLocation().y + speed < Main.height - height - 30) {
                        this.setLocation(this.getLocation().x, this.getLocation().y + speed);
                    }
                }
                case 2 -> {
                    if (Main.pressedUpArrow && this.getLocation().y - speed > 0) {
                        this.setLocation(this.getLocation().x, this.getLocation().y - speed);
                    }
                    if (Main.pressedDownArrow && this.getLocation().y + speed < Main.height - height - 30) {
                        this.setLocation(this.getLocation().x, this.getLocation().y + speed);
                    }
                }
            }
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(color);
        g2.setColor(color);
        Rectangle2D rect = new Rectangle2D.Double(0, 0, weight, height);
        g2.draw(rect);
        g2.fill(rect);
    }
}
