package controller;

import model.GameLogic;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ResetController extends MouseAdapter {
    private GameLogic gameLogic;

    public ResetController(GameLogic gl) {
        this.gameLogic = gl;
    }

    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        gameLogic.reset();
    }
}
