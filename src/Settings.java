import javax.swing.*;
import java.awt.*;

public class Settings extends JPanel{
    private JButton resetButton;
    private JButton botButton;
    private JButton playerButton;
    private JButton difficultyButton;
    private GameLogic gameLogic;
    private ResetController resetController;
    private BotController botController;
    private PlayerController playerController;
    private DifficultyController difficultyController;

    public Settings(GameLogic gl) {
        setBackground(Config.BACKGROUND_COLOR);
        setLayout(new GridLayout(1, 4));
        this.gameLogic = gl;
        Color foreground =  new Color(190, 190, 190);

        resetButton = new JButton("Reset") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(Config.BUTTON_COLOR);
                } else {
                    g.setColor(Config.FIELD_COLOR);
                }
                g.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
                super.paintComponent(g);
            }
        };
        resetButton.setBackground(Color.WHITE); // Setze die Hintergrundfarbe auf Weiß
        resetButton.setForeground(foreground); // Setze die Textfarbe auf Schwarz
        resetButton.setBorderPainted(false); // Deaktiviere die Randzeichnung
        resetButton.setFocusPainted(false); // Deaktiviere die Fokussierungsmarkierung
        this.resetController = new ResetController(gl);
        resetButton.addMouseListener(resetController);
        add(resetButton);

        playerButton = new JButton("Starting: X") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(Config.BUTTON_COLOR);
                } else {
                    g.setColor(Config.FIELD_COLOR);
                }
                g.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
                super.paintComponent(g);
            }
        };
        playerButton.setBackground(Color.WHITE); // Setze die Hintergrundfarbe auf Weiß
        playerButton.setForeground(Color.BLACK); // Setze die Textfarbe auf Schwarz
        playerButton.setBorderPainted(false); // Deaktiviere die Randzeichnung
        playerButton.setFocusPainted(false); // Deaktiviere die Fokussierungsmarkierung
        this.playerController = new PlayerController(gl);
        playerButton.addMouseListener(playerController);
        playerButton.setForeground(Config.X_COLOR);
        add(playerButton);

        botButton = new JButton("enable Bot") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(Config.BUTTON_COLOR);
                } else {
                    g.setColor(Config.FIELD_COLOR);
                }
                g.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
                super.paintComponent(g);
            }
        };
        botButton.setBackground(Color.WHITE); // Setze die Hintergrundfarbe auf Weiß
        botButton.setForeground(Color.BLACK); // Setze die Textfarbe auf Schwarz
        botButton.setBorderPainted(false); // Deaktiviere die Randzeichnung
        botButton.setFocusPainted(false); // Deaktiviere die Fokussierungsmarkierung
        this.botController = new BotController(gl);
        botButton.addMouseListener(botController);
        botButton.setForeground(new Color(90, 158, 94));
        add(botButton);

        difficultyButton = new JButton("Difficulty: Hard") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(Config.BUTTON_COLOR);
                } else {
                    g.setColor(Config.FIELD_COLOR);
                }
                g.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
                super.paintComponent(g);
            }
        };
        difficultyButton.setBackground(Color.WHITE); // Setze die Hintergrundfarbe auf Weiß
        difficultyButton.setForeground(foreground); // Setze die Textfarbe auf Schwarz
        difficultyButton.setBorderPainted(false); // Deaktiviere die Randzeichnung
        difficultyButton.setFocusPainted(false); // Deaktiviere die Fokussierungsmarkierung
        this.difficultyController = new DifficultyController(gl);
        difficultyButton.addMouseListener(difficultyController);
        add(difficultyButton);



        setPreferredSize(new Dimension(Config.GAME_SIZE, Config.SETTINGS_Y_Size));
    }
    public void setPlayerButton(String txt) {
        playerButton.setText(txt);
    }
}
