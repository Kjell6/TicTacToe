import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DesignController extends MouseAdapter {
    private Settings set;
    private GameLogic logic;

    public DesignController(Settings s, GameLogic gl) {
        this.set = s;
        this.logic = gl;
    }

    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        int design = Config.design;
        if (design == 1) {
            button.setText("Design: Minimal");
            Config.setDesign(design + 1);
        }
        if (design == 2) {
            button.setText("Design: Default");
            Config.setDesign(design - 1);
        }
        set.repaint();
        logic.setBackground(Config.BACKGROUND_COLOR);
    }


}

