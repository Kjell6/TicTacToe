import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Feld extends MouseAdapter {
    private Layout layout;
    private String playerTxt = "";
    private GameLogic gameLogic;

    public Feld(GameLogic gl) {
        this.gameLogic = gl;

    }

    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().isEmpty()) {
            if (gameLogic.player % 2 != 0) {
                gameLogic.player++;
                playerTxt ="X";
                button.setText(playerTxt);
            } else {
                gameLogic.player++;
                playerTxt = "O";
                button.setText(playerTxt);
            }

            if(gameLogic.botActive && !gameLogic.checkWin()) {
                gameLogic.player++;
                gameLogic.bot();
            }

            if (gameLogic.checkWin()) {
                gameLogic.showWinner();
            }
        }
    }
}

