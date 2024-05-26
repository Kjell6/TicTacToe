import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Reset extends MouseAdapter {
    Layout layout;

    public Reset(Layout layout) {
        this.layout = layout;
    }

    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        layout.reset();
    }
}
