import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
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
    private BufferedImage ballTexture;

    public Ball(int radius, PingPongFrame frame) {
        super();
        this.frame = frame;
        this.radius = radius;
        ballColliderBox = new ColliderBox(x, y,this.radius*2+2,this.radius*2+2);
        setLocation(x, y);
        setSize(new Dimension(2 * radius + 2, 2 * radius + 2));
        switchBallToStartPosition();
        try{
            ballTexture = ImageIO.read(new File("src/textures/ball1.png"));
        }
        catch (Exception e)
        {

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        Ellipse2D circle = new Ellipse2D.Double(0, 0, 2 * radius, 2 * radius);
        var ballTextureTp = new TexturePaint(ballTexture, new Rectangle(0,0,30,30));
        g2.draw(circle);
        g2.setPaint(ballTextureTp);
        g2.fill(circle);
    }
    public void switchBallToStartPosition() {
        switch (((random.nextInt(100))%4))
        {
            case 0:
                speedY = 1;
                speedX = 1;
                break;
            case 1:
                speedY = 1;
                speedX = -1;
                break;
            case 2:
                speedY = -1;
                speedX = 1;
                break;
            case 3:
                speedY = -1;
                speedX = -1;
                break;
        }
        this.setLocation(Main.width/2, (Main.height-30)/2 - this.radius );
    }

    public void resetPaddlePosition()
    {
        frame.player1Paddle.y = (Main.height -30)/2 - Paddle.height /2;
        frame.player1Paddle.paddleColliderBox.y = (Main.height -30)/2 - Paddle.height /2;
        frame.player2Paddle.y = (Main.height -30)/2 - Paddle.height /2;
        frame.player2Paddle.paddleColliderBox.y = (Main.height -30)/2 - Paddle.height /2;
        frame.player1Paddle.setLocation(frame.player1Paddle.x, frame.player1Paddle.y);
        frame.player2Paddle.setLocation(frame.player2Paddle.x, frame.player2Paddle.y);
    }


    @Override
    public void run() {
        frame.repaint();
        while (Main.isRunning) {
            if(Main.isReset) {
                resetPaddlePosition();
                switchBallToStartPosition();
                Main.isReset = false;
                frame.info.score = "0:0";
                frame.scorePlayer1 = 0;
                frame.scorePlayer2 = 0;
            }
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
                    && ballColliderBox.getUpColliderLine() >= frame.player1Paddle.paddleColliderBox.getUpColliderLine() + frame.player1Paddle.paddleColliderBox.height - 10
                    && ballColliderBox.getUpColliderLine() <= frame.player1Paddle.paddleColliderBox.getDownColliderLine()
                    && ballColliderBox.getRightColliderLine() >= frame.player1Paddle.paddleColliderBox.getLeftColliderLine())
            || (ballColliderBox.getRightColliderLine() >= frame.player2Paddle.paddleColliderBox.getLeftColliderLine()
                    && ballColliderBox.getUpColliderLine() >= frame.player2Paddle.paddleColliderBox.getUpColliderLine() + frame.player2Paddle.paddleColliderBox.height - 10
                    && ballColliderBox.getUpColliderLine() <= frame.player2Paddle.paddleColliderBox.getDownColliderLine()
                    && ballColliderBox.getLeftColliderLine() <= frame.player2Paddle.paddleColliderBox.getRightColliderLine())
            || (ballColliderBox.getLeftColliderLine() <= frame.player1Paddle.paddleColliderBox.getRightColliderLine()
                    && ballColliderBox.getDownColliderLine() <= frame.player1Paddle.paddleColliderBox.getDownColliderLine() - frame.player1Paddle.paddleColliderBox.height + 10
                    && ballColliderBox.getDownColliderLine() >= frame.player1Paddle.paddleColliderBox.getUpColliderLine()
                    && ballColliderBox.getRightColliderLine() >= frame.player1Paddle.paddleColliderBox.getLeftColliderLine())
            || (ballColliderBox.getRightColliderLine() >= frame.player2Paddle.paddleColliderBox.getLeftColliderLine()
                    && ballColliderBox.getDownColliderLine() <= frame.player2Paddle.paddleColliderBox.getDownColliderLine() - frame.player2Paddle.paddleColliderBox.height + 10
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

            if(ballColliderBox.getLeftColliderLine() < 2)
            {
                switchBallToStartPosition();
                frame.scorePlayer2 ++;
                frame.info.score = frame.scorePlayer1 + ":" + frame.scorePlayer2;
                frame.repaint();
            }
            else if(ballColliderBox.getRightColliderLine() > (Main.width - 2))
            {
                switchBallToStartPosition();
                frame.scorePlayer1 ++;
                frame.info.score = frame.scorePlayer1 + ":" + frame.scorePlayer2;
                frame.repaint();
            } else {
                this.setLocation(x + speedX, y + speedY);
            }
            if (frame.scorePlayer1 == 5)
            {
                resetPaddlePosition();
                switchBallToStartPosition();
                Main.isRunning = false;
                frame.info.score = "Player1 wins!";

            }
            else if (frame.scorePlayer2 == 5)
            {
                resetPaddlePosition();
                switchBallToStartPosition();
                Main.isRunning = false;
                frame.info.score = "Player2 wins!";
            }
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }


        frame.repaint();
    }
}
