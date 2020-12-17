import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Ball extends JComponent implements Runnable {
    public int radius = 15;
    private final static Color color = Color.RED;
    PingPongFrame frame;
    private int speedX = 1;
    private int speedY = 1;
    private int x = Main.width/2;
    private int y = (Main.height-30)/2 - this.radius;
    private final Random random = new Random();
    private final ColliderBox ballColliderBox;

    public Ball(int radius, PingPongFrame frame) {
        super();
        this.frame = frame;
        this.radius = radius;
        ballColliderBox = new ColliderBox(x, y,this.radius*2,this.radius*2);
        setLocation(x, y);
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
        speedY = ((random.nextInt(2) + 1) * 2 - 3);
        speedX = ((random.nextInt(2) + 1) * 2 - 3);
        this.setLocation(Main.width/2, (Main.height-30)/2 - this.radius );
    }

    @Override
    public void run() {
        frame.repaint();
        int paddleHeight = Paddle.height;
        int paddleWidth = Paddle.weight;
        boolean changedYFromPaddle = false;
        boolean changedXFromPaddle = false;
        while (Main.isRunning) {
            int x1, x2, y1, y2;
            synchronized (frame.player1Paddle) {
                frame.player1Paddle.x = frame.player1Paddle.getLocation().x;
                frame.player1Paddle.paddleColliderBox.x = frame.player1Paddle.getLocation().x;
                frame.player1Paddle.y = frame.player1Paddle.getLocation().y;
                frame.player1Paddle.paddleColliderBox.y = frame.player1Paddle.getLocation().y;
            }
            synchronized (frame.player2Paddle) {
                frame.player2Paddle.x = frame.player2Paddle.getLocation().x;
                frame.player2Paddle.paddleColliderBox.x = frame.player2Paddle.getLocation().x;
                frame.player2Paddle.y = frame.player2Paddle.getLocation().y;
                frame.player2Paddle.paddleColliderBox.y = frame.player2Paddle.getLocation().y;
            }
            this.x = this.getLocation().x;
            ballColliderBox.x = this.getLocation().x;
            this.y = this.getLocation().y;
            ballColliderBox.y = this.getLocation().y;

            if(ballColliderBox.getDownColliderLine() >= frame.downEdge.getUpColliderLine()
            || ballColliderBox.getUpColliderLine() <= frame.upEdge.getDownColliderLine()
            || (ballColliderBox.getLeftColliderLine() <= frame.player1Paddle.paddleColliderBox.getRightColliderLine()
                    && ballColliderBox.getUpColliderLine() >= frame.player1Paddle.paddleColliderBox.getUpColliderLine() + frame.player1Paddle.paddleColliderBox.height - 3
                    && ballColliderBox.getUpColliderLine() <= frame.player1Paddle.paddleColliderBox.getDownColliderLine()
                    && ballColliderBox.getRightColliderLine() >= frame.player1Paddle.paddleColliderBox.getLeftColliderLine())
            || (ballColliderBox.getRightColliderLine() >= frame.player2Paddle.paddleColliderBox.getLeftColliderLine()
                    && ballColliderBox.getUpColliderLine() >= frame.player2Paddle.paddleColliderBox.getUpColliderLine() + frame.player2Paddle.paddleColliderBox.height - 3
                    && ballColliderBox.getUpColliderLine() <= frame.player2Paddle.paddleColliderBox.getDownColliderLine()
                    && ballColliderBox.getLeftColliderLine() <= frame.player2Paddle.paddleColliderBox.getRightColliderLine())
            || (ballColliderBox.getLeftColliderLine() <= frame.player1Paddle.paddleColliderBox.getRightColliderLine()
                    && ballColliderBox.getDownColliderLine() <= frame.player1Paddle.paddleColliderBox.getDownColliderLine() - frame.player1Paddle.paddleColliderBox.height + 3
                    && ballColliderBox.getDownColliderLine() >= frame.player1Paddle.paddleColliderBox.getUpColliderLine()
                    && ballColliderBox.getRightColliderLine() >= frame.player1Paddle.paddleColliderBox.getLeftColliderLine())
            || (ballColliderBox.getRightColliderLine() >= frame.player2Paddle.paddleColliderBox.getLeftColliderLine()
                    && ballColliderBox.getDownColliderLine() <= frame.player2Paddle.paddleColliderBox.getDownColliderLine() - frame.player2Paddle.paddleColliderBox.height + 3
                    && ballColliderBox.getDownColliderLine() >= frame.player2Paddle.paddleColliderBox.getUpColliderLine()
                    && ballColliderBox.getLeftColliderLine() <= frame.player2Paddle.paddleColliderBox.getRightColliderLine()))
            {
                    speedY *= -1;


                this.setLocation(x + speedX*3,y + speedY*3);
                this.x = this.getLocation().x;
                ballColliderBox.x = this.getLocation().x;
                this.y = this.getLocation().y;
                ballColliderBox.y = this.getLocation().y;
            }
            if((ballColliderBox.getLeftColliderLine() <= frame.player1Paddle.paddleColliderBox.getRightColliderLine()
            && ballColliderBox.getDownColliderLine() >= frame.player1Paddle.paddleColliderBox.getUpColliderLine()
            && ballColliderBox.getUpColliderLine() <= frame.player1Paddle.paddleColliderBox.getDownColliderLine()
            && ballColliderBox.getRightColliderLine() >= frame.player1Paddle.paddleColliderBox.getLeftColliderLine())
                ||(ballColliderBox.getRightColliderLine() >= frame.player2Paddle.paddleColliderBox.getLeftColliderLine()
                && ballColliderBox.getUpColliderLine() <= frame.player2Paddle.paddleColliderBox.getDownColliderLine()
                && ballColliderBox.getDownColliderLine() >= frame.player2Paddle.paddleColliderBox.getUpColliderLine()
                && ballColliderBox.getLeftColliderLine() <= frame.player2Paddle.paddleColliderBox.getRightColliderLine())
            )
            {
                speedX *= -1;
                this.setLocation(x + speedX*3,y + speedY*3);
                this.x = this.getLocation().x;
                ballColliderBox.x = this.getLocation().x;
                this.y = this.getLocation().y;
                ballColliderBox.y = this.getLocation().y;
            }


            this.setLocation(x + speedX, y +speedY);
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        switchBallToStartPosition();
       //frame.score.scoreValue = "0:0";
        frame.repaint();
    }
}
