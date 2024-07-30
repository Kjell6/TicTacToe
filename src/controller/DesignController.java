package controller;

import config.Config;
import model.GameLogic;
import view.SettingsPanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DesignController extends MouseAdapter {
    private SettingsPanel set;
    private GameLogic logic;

    public DesignController(SettingsPanel s, GameLogic gl) {
        this.set = s;
        this.logic = gl;
    }

    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        int design = Config.design;
        String[] designTxt = {"Default", "Minimal"};
        for (int i = 1; i <= design; i++) {
            if (i == design) {
                if (i == designTxt.length) {
                    button.setText("Design: " + designTxt[0]);
                    Config.setDesign(1);
                    i = 2;
                } else {
                    button.setText("Design: " + designTxt[i]);
                    Config.setDesign(design + 1);
                    i = 2;
                }
            }
        }
        set.repaint();
    }


}

