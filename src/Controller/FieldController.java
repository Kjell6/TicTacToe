package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Model.GameLogic;

public class FieldController extends MouseAdapter {
    private String playerTxt = "";
    private GameLogic gameLogic;

    public FieldController(GameLogic gl) {
        this.gameLogic = gl;

    }

    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().isEmpty()) {
            if (gameLogic.player % 2 != 0) {
                gameLogic.player++;
                playerTxt ="X";
                button.setForeground(new Color(35, 68, 167));
                button.setText(playerTxt);
            } else {
                gameLogic.player++;
                playerTxt = "O";
                button.setForeground(new Color(178, 70, 145));
                button.setText(playerTxt);
            }

            if(gameLogic.botActive && !gameLogic.checkWin()) {
                gameLogic.player++;
                gameLogic.bot();
            }

            Font currentFont = button.getFont();
            Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), 20); // Hier 20 durch Ihre gewünschte Größe ersetzen
            button.setFont(newFont);

            if (gameLogic.checkWin()) {
                gameLogic.showWinner();
            }
            if (gameLogic.full()) {
                gameLogic.delayedReset();
            }
        }
    }
}

