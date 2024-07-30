package controller;

import config.Config;
import model.GameLogic;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerController extends MouseAdapter {
    private GameLogic gameLogic;

    public PlayerController(GameLogic gl) {
        this.gameLogic = gl;
    }

    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        int player = gameLogic.getStartingPlayer();
        if ((player % 2) != 0) {
            gameLogic.setStartingPlayerPlayer(0);
            button.setText("Starting: O");
            button.setForeground(Config.O_COLOR);
            gameLogic.playerButtonBot();
        } else {
            gameLogic.setStartingPlayerPlayer(1);
            button.setText("Starting: X");
            button.setForeground(Config.X_COLOR);
        }
        gameLogic.reset();
    }
}
