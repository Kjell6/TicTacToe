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
    private Color foreground =  new Color(190, 190, 190);

    public Settings(GameLogic gl) {
        setBackground(Config.BACKGROUND_COLOR);
        setLayout(new GridLayout(1, 4));
        this.gameLogic = gl;

        resetButton = new CustomButton("Reset");
        this.resetController = new ResetController(gl);
        resetButton.addMouseListener(resetController);
        add(resetButton);

        playerButton = new CustomButton("Starting: X");
        this.playerController = new PlayerController(gl);
        playerButton.addMouseListener(playerController);
        playerButton.setForeground(Config.X_COLOR);
        add(playerButton);

        botButton = new CustomButton("enable Bot");
        this.botController = new BotController(gl);
        botButton.addMouseListener(botController);
        botButton.setForeground(new Color(90, 158, 94));
        add(botButton);

        difficultyButton = new CustomButton("Difficulty: Hard");
        this.difficultyController = new DifficultyController(gl);
        difficultyButton.addMouseListener(difficultyController);
        add(difficultyButton);



        setPreferredSize(new Dimension(Config.GAME_SIZE, Config.SETTINGS_Y_Size));
    }
    public void setPlayerButton(String txt) {
        playerButton.setText(txt);
    }

    public class CustomButton extends JButton {

        public CustomButton(String text) {
            super(text);
            // Setze zusätzliche Eigenschaften des Buttons hier, falls notwendig
            this.setBackground(Color.WHITE); // Setze die Hintergrundfarbe auf Weiß
            this.setForeground(foreground); // Setze die Textfarbe auf Schwarz
            this.setBorderPainted(false); // Deaktiviere die Randzeichnung
            this.setFocusPainted(false); // Deaktiviere die Fokussierungsmarkierung
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                g.setColor(Config.BUTTON_COLOR_Pressed);
            } else {
                g.setColor(Config.BUTTON_COLOR);
            }
            g.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
            super.paintComponent(g);
        }
    }

}

