package view;

import config.Config;
import model.GameLogic;

import javax.swing.*;
import java.awt.*;

public class TicTacToe {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Tic Tac Toe");
        // exit application on close
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // show window in the center of the screen
        frame.setLocationRelativeTo(null);
        // window is not resizable
        frame.setResizable(false);
        // arrange components
        frame.pack();
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // create game logic object
        GameLogic gameLogic = new GameLogic();
        SettingsPanel settings = new SettingsPanel(gameLogic);
        GamePanel gamePanel = new GamePanel(gameLogic);
        gameLogic.setSettings(settings);
        ScorePanel scorePanel = new ScorePanel(gameLogic);
        gameLogic.subscribe(gamePanel);
        Config.setDesign(1);
        gamePanel.start();

        // add panel to window
        gbc.gridy = 0;
        frame.add(scorePanel, gbc);
        gbc.gridy = 1;
        frame.add(gamePanel, gbc);
        gbc.gridy = 2;
        frame.add(settings, gbc);
        frame.pack();
        frame.setVisible(true);
        gamePanel.start();
        scorePanel.start();
        frame.getContentPane().setBackground(Config.BACKGROUND_COLOR);

        // show window
        frame.setVisible(true);
    }
}
