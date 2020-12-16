import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyEventHandler extends KeyAdapter {


        public PingPongFrame frame;

        KeyEventHandler(PingPongFrame frame)
        {
            this.frame = frame;
        }

        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_P) {
                Main.isRunning = !Main.isRunning;
                if (Main.isRunning) {
                    frame.run();
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
