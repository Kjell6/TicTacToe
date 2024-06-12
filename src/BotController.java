import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotController extends MouseAdapter {
    private GameLogic gameLogic;

    public BotController(GameLogic gl) {
        this.gameLogic = gl;
    }

    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        if (!gameLogic.botActive) {
            button.setText("disable Bot");
            //button.setForeground(new Color(201, 79, 79));
            gameLogic.botActive = true;
            gameLogic.reset();
            gameLogic.playerButtonBot();
        } else {
            button.setText("enable Bot");
            //button.setForeground(new Color(90, 158, 94));
            gameLogic.botActive = false;
            gameLogic.reset();
            gameLogic.playerButtonBot();
        }

    }


}
