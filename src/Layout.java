import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Layout {
    public JFrame frame = new JFrame("TicTacToe");
    private JButton[][] buttons = new JButton[3][3];
    private JButton resetButton = new JButton("Reset");
    private JEditorPane scoreText = new JEditorPane();
    private JButton botButton = new JButton("enable Bot");
    private JButton playerButton = new JButton("Starting Player: X");
    private GameLogic gameLogic;
    private ResetController resetController;
    private BotController botController;
    private FieldController fieldController;
    private PlayerController playerController;

    public Layout(GameLogic gl) {
        this.gameLogic = gl;
        this.fieldController = new FieldController(gl);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].addMouseListener(fieldController);
                frame.add(buttons[i][j]);
            }
        }

        this.resetController = new ResetController(gl);
        resetButton.addMouseListener(resetController);
        frame.add(resetButton);
        resetButton.setForeground(new Color(201, 79, 79));

        frame.setLayout(new GridLayout(5,3));
        frame.setSize(new Dimension(500, 600));

        scoreText.setEditable(false);
        frame.add(scoreText);
        scoreText.setText("Player X: 0 Points\nPlayer O: 0 Points");

        this.botController = new BotController(gl);
        botButton.addMouseListener(botController);
        botButton.setForeground(new Color(90, 158, 94));
        frame.add(botButton);

        this.playerController = new PlayerController(gl);
        playerButton.addMouseListener(playerController);
        frame.add(playerButton);

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

    public JEditorPane getScoreText() {
        return this.scoreText;
    }
}
