import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Ball extends JComponent implements Runnable {
    public int radius = 15;
    private final static Color color = Color.RED;
    PingPongFrame frame;
    private int paddleHeight;
    private int paddleWidth;
    private int speedX = 10;
    private int speedY = 10;
    private Random random = new Random();

    public Ball(PingPongFrame frame) {
        super();
        this.frame = frame;
        setLocation(640, 360);
        setSize(new Dimension(2 * radius + 2, 2 * radius + 2));
        switchBallToStartPosition();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        Ellipse2D circle = new Ellipse2D.Double(0, 0, 2 * radius, 2 * radius);
        g2.draw(circle);
        g2.fill(circle);
    }
    public void switchBallToStartPosition() {
        speedY = 10 * ((random.nextInt(2) + 1) * 2 - 3);
        speedX = 10 * ((random.nextInt(2) + 1) * 2 - 3);
        this.setLocation(Main.width/2, Main.height/2);
    }

    @Override
    public void run() {
        frame.repaint();
        paddleHeight = Paddle.h;
        paddleWidth = Paddle.w;
        while (Main.isRunning) {
            int x1, x2, y1, y2;
            synchronized (frame.player1Paddle) {
                x1 = frame.player1Paddle.getLocation().x;
                y1 = frame.player1Paddle.getLocation().y;
            }
            synchronized (frame.player2Paddle) {
                x2 = frame.player2Paddle.getLocation().x;
                y2 = frame.player2Paddle.getLocation().y;
            }
            int xBall = this.getLocation().x;
            int yBall = this.getLocation().y;
            if (((x1 + paddleWidth > xBall) && (y1 <= yBall + 2 * this.radius) && (y1 + paddleHeight >= yBall))
                    || ((x2 < xBall + 2 * this.radius) && (y2 <= yBall + 2 * this.radius) && (y2 + paddleHeight >= yBall))) {
                speedX *= -1;
                this.setLocation(xBall + (int)(speedX * 1.5), yBall + (int)(speedY * 1.5));
            }
            else {
                if (yBall < 10 || yBall + 2 * this.radius > Main.height - 40) {
                    speedY *= -1;
                    this.setLocation(xBall + speedX, yBall + speedY);
                }
                else {
                    if (xBall <= 5) {
                        switchBallToStartPosition();

                        frame.repaint();
                    }
                    else {
                        if (xBall + 2 * this.radius >= Main.width) {
                            switchBallToStartPosition();

                            frame.repaint();
                        }
                        else {
                            this.setLocation(xBall + speedX, yBall + speedY);
                        }
                    }
                }
            }
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        switchBallToStartPosition();
       //frame.score.scoreValue = "0:0";
        frame.repaint();
    }
}
