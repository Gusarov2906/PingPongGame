import javax.swing.*;
import java.awt.*;

public class Info extends JComponent {
    public String score = "0:0";
    public Info() {
        super();
        setLocation(0, 0);
        setSize(new Dimension(Main.width, Main.height));
    }
    @Override
    public void paintComponent(Graphics g) {
        try {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.RED);
            g2.setFont(new Font("Bauhaus 93", Font.BOLD, 60));
            g2.drawString(score, Main.width/2-35, 65);
            g2.setFont(new Font("Bauhaus 93", Font.PLAIN, 40));
            if (!Main.isRunning) g2.drawString("Pause(Press R to reset)", Main.width/2-170, Main.height - 50);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
