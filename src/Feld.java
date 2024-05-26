import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Feld extends MouseAdapter {
    public int player = 1;
    private Layout layout;
    private String playerTxt = "";

    public Feld(Layout layout) {
        this.layout = layout;
    }

    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().isEmpty()) {
            if (player % 2 != 0) {
                player++;
                playerTxt ="X";
                button.setText(playerTxt);
            } else {
                player++;
                playerTxt = "O";
                button.setText(playerTxt);
            }

            if(layout.botActive && !layout.checkWin()) {
                player++;
                layout.bot();
            }

            if (layout.checkWin()) {
                String winner = "";
                if (player % 2 == 0) {
                    winner ="X";
                } else {
                    winner = "O";
                }
                JOptionPane.showMessageDialog(layout.frame, String.format("Player %s wins!", winner));
                layout.reset();
                layout.score(winner);
            }
        }
    }
}

