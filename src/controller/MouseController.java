package controller;

import model.Field;
import model.GameLogic;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Mouse Adapter
 */
public class MouseController extends MouseAdapter {

    private GameLogic gameLogic;

    public MouseController(GameLogic gl) {
        this.gameLogic = gl;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        Field[][] fs = gameLogic.fields;
        boolean fieldPressed = false;
        int fieldI = -1;
        int fieldJ = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (fs[i][j].getHitBox().contains(mouseX, mouseY)) {
                    fieldI = i;
                    fieldJ = j;
                    fieldPressed = true;
                }
            }
        }

        String playerTxt = "";
        if (fieldPressed) {
            if (gameLogic.fields[fieldI][fieldJ].getFieldValue().isEmpty()) {
                if (gameLogic.player % 2 != 0) {
                    gameLogic.player++;
                    gameLogic.fields[fieldI][fieldJ].setValueX();
                } else {
                    gameLogic.player++;
                    gameLogic.fields[fieldI][fieldJ].setValueO();
                }

                if(gameLogic.botActive && !gameLogic.checkWin()) {
                    gameLogic.player++;
                    gameLogic.bot();
                }

                if (gameLogic.checkWin()) {
                    gameLogic.publishWinner();
                }
                if (gameLogic.full()) {
                    gameLogic.delayedReset();
                }
            }
        }
    }
}
