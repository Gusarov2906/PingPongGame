import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PingPongFrame extends JFrame {
    public Info info;
    public Paddle player1Paddle;
    public Paddle player2Paddle;
    public Ball ball;
    private JPanel panel;

    public PingPongFrame()
    {
        player1Paddle = new Paddle(200, 1);
        player2Paddle = new Paddle(Main.width - 200, 2);
        ball = new Ball(this);
        setTitle("PingPongGame");
        panel = new JPanel();
        panel.setLayout(null);
        panel.add(player1Paddle);
        panel.add(player2Paddle);
        panel.add(ball);
        panel.setBackground(Color.BLUE);
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
            if (event.getKeyCode() == KeyEvent.VK_P) {
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
