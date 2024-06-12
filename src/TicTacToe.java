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
        Settings settings = new Settings(gameLogic);
        gameLogic.setSettings(settings);
        ScorePanel scorePanel = new ScorePanel(gameLogic);
        gameLogic.start();

        // add panel to window
        gbc.gridy = 0;
        frame.add(scorePanel, gbc);
        gbc.gridy = 1;
        frame.add(gameLogic, gbc);
        gbc.gridy = 2;
        frame.add(settings, gbc);
        frame.pack();
        frame.setVisible(true);
        gameLogic.start();
        scorePanel.start();
        frame.getContentPane().setBackground(Config.BACKGROUND_COLOR);

        // show window
        frame.setVisible(true);
    }
}
