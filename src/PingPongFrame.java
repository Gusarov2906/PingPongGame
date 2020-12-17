import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class PingPongFrame extends JFrame {
    public Info info;
    public final Paddle player1Paddle;
    public final Paddle player2Paddle;
    public Ball ball;
    private JPanel panel;
    public ColliderBox downEdge;
    public ColliderBox upEdge;
    private BufferedImage backgroundTexture;
    public int scorePlayer1;
    public int scorePlayer2;

    public PingPongFrame()
    {
        info = new Info();
        downEdge = new ColliderBox(0,Main.height -35,Main.width,5);
        upEdge = new ColliderBox(0,0,Main.width,5);
        player1Paddle = new Paddle(20, 1);
        player2Paddle = new Paddle(Main.width - 50, 2);
        ball = new Ball(20,this);
        setTitle("PingPongGame");
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(1,0,47));
        panel.add(info);
        panel.add(player1Paddle);
        panel.add(player2Paddle);
        panel.add(ball);



        setVisible(true);
        setResizable(false);
        setSize(new Dimension(Main.width, Main.height));
        setContentPane(panel);
        setLocation(600,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(new KeyEventHandler());
    }

    public void run()
    {
        Thread player1Thread = new Thread(player1Paddle);
        Thread player2Thread = new Thread(player2Paddle);
        Thread ballThread = new Thread(ball);
        player1Thread.start();
        player2Thread.start();
        ballThread.start();
        //ball.run();
    }
    public class KeyEventHandler extends KeyAdapter {

        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                Main.isRunning = !Main.isRunning;
                if (Main.isRunning) {
                    run();
                }
            }
            if (event.getKeyCode() == KeyEvent.VK_R) {
                Main.isReset = true;
                Main.isRunning = !Main.isRunning;
                if (Main.isRunning) {
                    run();
                }
            }
            if (event.getKeyCode() == KeyEvent.VK_W) Main.pressedW = true;
            if (event.getKeyCode() == KeyEvent.VK_S) Main.pressedS = true;
            if (event.getKeyCode() == KeyEvent.VK_UP) Main.pressedUpArrow = true;
            if (event.getKeyCode() == KeyEvent.VK_DOWN) Main.pressedDownArrow = true;
        }
        public void keyReleased(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_W) Main.pressedW = false;
            if (event.getKeyCode() == KeyEvent.VK_S) Main.pressedS = false;
            if (event.getKeyCode() == KeyEvent.VK_UP) Main.pressedUpArrow = false;
            if (event.getKeyCode() == KeyEvent.VK_DOWN) Main.pressedDownArrow = false;
        }

    }
}
