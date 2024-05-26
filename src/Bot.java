import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Bot extends MouseAdapter {
    private Layout layout;

    public Bot(Layout l) {
        layout = l;
    }

    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        if (!layout.botActive) {
            button.setText("disable Bot");
            button.setForeground(new Color(201, 79, 79));
            layout.botActive = true;
        } else {
            button.setText("enable Bot");
            button.setForeground(new Color(90, 158, 94));
            layout.botActive = false;
        }

    }


}
