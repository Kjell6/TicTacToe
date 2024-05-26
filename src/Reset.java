import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Reset extends MouseAdapter {
    Layout layout;
    private GameLogic gameLogic;

    public Reset(GameLogic gl) {
        this.gameLogic = gl;
    }

    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        gameLogic.reset();
    }
}
