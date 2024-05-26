import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Layout {
    public JFrame frame = new JFrame("TicTacToe");
    private JButton[][] buttons = new JButton[3][3];
    private JButton resB = new JButton("Reset");
    private JEditorPane text = new JEditorPane();
    private JButton botB = new JButton("enable Bot");
    private GameLogic gameLogic;
    private Reset resetButton;
    private Bot botButton;
    private  Feld feldButton;

    public Layout(GameLogic gl) {
        this.gameLogic = gl;
        this.feldButton = new Feld(gl);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].addMouseListener(feldButton);
                frame.add(buttons[i][j]);
            }
        }

        this.resetButton = new Reset(gl);
        resB.addMouseListener(resetButton);
        frame.add(resB);
        resB.setForeground(new Color(201, 79, 79));

        frame.setLayout(new GridLayout(4,3));
        frame.setSize(new Dimension(500, 600));

        text.setEditable(false);
        frame.add(text);
        text.setText("Player X: 0 Points\nPlayer O: 0 Points");

        this.botButton = new Bot(gl);
        botB.addMouseListener(botButton);
        botB.setForeground(new Color(90, 158, 94));
        frame.add(botB);

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

    public JEditorPane getScoreText() {
        return this.text;
    }
}
