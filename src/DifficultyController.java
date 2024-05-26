import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DifficultyController extends MouseAdapter {
    private GameLogic gameLogic;

    public DifficultyController(GameLogic gl) {
        this.gameLogic = gl;
    }

    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        if (gameLogic.difficulty == 3) {
            button.setText("Difficulty: Easy");
            gameLogic.difficulty = 1;
            gameLogic.reset();
        }

        if (gameLogic.difficulty == 1) {
            button.setText("Difficulty: Medium");
            gameLogic.difficulty = 2;
            gameLogic.reset();
        }

        if (gameLogic.difficulty == 2) {
            button.setText("Difficulty: Hard");
            gameLogic.difficulty = 3;
            gameLogic.reset();
        }
    }
}
