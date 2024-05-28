import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Layout {
    public JFrame frame = new JFrame("TicTacToe");
    private JButton[][] buttons = new JButton[3][3];
    private JButton resetButton = new JButton("Reset");
    private JEditorPane scoreTextX = new JEditorPane("text/html", "");
    private JEditorPane scoreTextO = new JEditorPane("text/html", "");
    private JButton botButton = new JButton("enable Bot");
    private JButton playerButton = new JButton("Starting Player: X");
    private JButton difficultyButton = new JButton("Difficulty: Hard");
    private GameLogic gameLogic;
    private ResetController resetController;
    private BotController botController;
    private FieldController fieldController;
    private PlayerController playerController;
    private DifficultyController difficultyController;

    public Layout(GameLogic gl) {
        frame.setLayout(new GridBagLayout());
        frame.setSize(new Dimension(600, 700));

        GridBagConstraints gbc = new GridBagConstraints();

        // Center scoreText in bottomPanel
        JPanel scoreTextPanel = new JPanel(new GridLayout());
        scoreTextX.setEditable(false);
        scoreTextX.setText("<html><center><span style='font-family: Lucida Grande; font-size: 25pt; color: rgb(35, 68, 167);'>X - 0</span></center></html>");
        scoreTextPanel.add(scoreTextX);
        scoreTextO.setEditable(false);
        scoreTextO.setText("<html><center><span style='font-family: Lucida Grande; font-size: 25pt; color: rgb(178, 70, 145);'>O - 0</span></center></html>");
        scoreTextPanel.add(scoreTextO);

        // Add scoreTextPanel to bottomPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.01; // Top panel takes 5% of the vertical space
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(scoreTextPanel, gbc);

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 3));
        this.gameLogic = gl;
        this.fieldController = new FieldController(gl);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].addMouseListener(fieldController);
                topPanel.add(buttons[i][j]);
            }
        }

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8; // Top panel takes 80% of the vertical space
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(topPanel, gbc);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new GridLayout(1, 4));

        this.resetController = new ResetController(gl);
        resetButton.addMouseListener(resetController);
        bottomPanel.add(resetButton);
        resetButton.setForeground(new Color(201, 79, 79));

        this.playerController = new PlayerController(gl);
        playerButton.addMouseListener(playerController);
        playerButton.setForeground(new Color(35, 68, 167));
        bottomPanel.add(playerButton);

        this.botController = new BotController(gl);
        botButton.addMouseListener(botController);
        botButton.setForeground(new Color(90, 158, 94));
        bottomPanel.add(botButton);

        this.difficultyController = new DifficultyController(gl);
        difficultyButton.addMouseListener(difficultyController);
        bottomPanel.add(difficultyButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.15; // Bottom panel takes 15% of the vertical space
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(bottomPanel, gbc);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public JButton[][] getButtons() {
        JButton[][] res = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res[i][j] = this.buttons[i][j];
            }
        }
        return res;
    }

    public void setPlayerButton(String txt) {
        playerButton.setText(txt);
    }

    public JEditorPane[] getScoreTexts() {
        JEditorPane[] scores = new JEditorPane[2];
        scores[0] = scoreTextX;
        scores[1] = scoreTextO;
        return scores;
    }
}
