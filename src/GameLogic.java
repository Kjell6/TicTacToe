import javax.swing.*;
import java.util.Random;

public class GameLogic {
    private int pX = 0;
    private int pY = 0;
    public int player = 1;
    public boolean botActive = false;
    private Layout layout;

    public GameLogic() {
        this.layout = new Layout(this);
    }

    public void score(String playerTxt) {
        if (playerTxt.equals("X")) {
            pX++;
        } else {
            pY++;
        }
        layout.getScoreText().setText(String.format("Player X: %d Points\nPlayer O: %d Points", pX, pY));
    }

    public void reset() {
        JButton[][] buttons = layout.getButtons();
        for (JButton bs[] : buttons) {
            for (JButton b : bs) {
                b.setText("");
            }
        }
        setPlayer(1);
    }

    public void setPlayer(int p) {
        player = p;
    }

    public boolean checkWin() {
        JButton[][] buttons = layout.getButtons();

        for (int i = 0; i < buttons.length; i++) {
            //Horizontal
            if (buttons[i][0].getText().equals(buttons[i][1].getText())
                    && buttons[i][0].getText().equals(buttons[i][2].getText())
                    && !buttons[i][0].getText().isEmpty()) {
                return true;
            }

            //Vertikal
            if (buttons[0][i].getText().equals(buttons[1][i].getText())
                    && buttons[0][i].getText().equals(buttons[2][i].getText())
                    && !buttons[0][i].getText().isEmpty()) {
                return true;
            }
        }

        //Diagonal
        if (!buttons[1][1].getText().isEmpty() &&
                ((buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                        buttons[0][0].getText().equals(buttons[2][2].getText())) ||
                        (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                                buttons[0][2].getText().equals(buttons[2][0].getText())))) {
            return true;
        }
        return false;
    }

    public void showWinner() {
        String winner = "";
        if (player % 2 == 0) {
            winner ="X";
        } else {
            winner = "O";
        }
        JOptionPane.showMessageDialog(layout.frame, String.format("Player %s wins!", winner));
        reset();
        score(winner);
    }

    // Start Bot Logic

    private void botRandom() {
        JButton[][] buttons = layout.getButtons();
        Random rand = new Random();
        int y = rand.nextInt(3);
        int x = rand.nextInt(3);

        int usedFields = 0;
        for (JButton[] b1 : buttons) {
            for (JButton b : b1) {
                if (!b.getText().isEmpty()) usedFields++;
            }
        }

        if (buttons[y][x].getText().isEmpty()) {
            buttons[y][x].setText("O");
        } else {
            if (usedFields < 9) botRandom();
        }
    }

    private boolean winningRows() {
        JButton[][] buttons = layout.getButtons();
        int count = 0;
        int countX = 0;
        int row = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("O")) count++;
                if (buttons[i][j].getText().equals("X")) countX++;
            }
            if (count == 2 && countX == 0) row = i;
            count = 0;
            countX = 0;
        }

        if (row > -1) {
            for (int i = 0; i < 3; i++) {
                if (buttons[row][i].getText().isEmpty()) {
                    buttons[row][i].setText("O");
                    i = 3;
                }
            }
        }

        return row > -1;
    }

    private boolean winningColumn() {
        JButton[][] buttons =  layout.getButtons();
        int count = 0;
        int countX = 0;
        int column = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[j][i].getText().equals("O")) count++;
                if (buttons[j][i].getText().equals("X")) countX++;
            }
            if (count == 2 && countX == 0) column = i;
            count = 0;
            countX = 0;
        }

        if (column > -1) {
            for (int i = 0; i < 3; i++) {
                if (buttons[i][column].getText().isEmpty()) {
                    buttons[i][column].setText("O");
                    i = 3;
                }
            }
        }

        return column > -1;
    }

    private boolean winningDiagonal() {
        JButton[][] buttons =  layout.getButtons();
        int count = 0;
        int countX = 0;
        //Links oben nach rechts unten
        for (int j = 0; j < 3; j++) {
            if (buttons[j][j].getText().equals("O")) count++;;
            if (buttons[j][j].getText().equals("X")) countX++;
        }
        if (count == 2 && countX == 0) {
            for (int j = 0; j < 3; j++) {
                if (buttons[j][j].getText().isEmpty()) {
                    buttons[j][j].setText("O");
                    return true;
                }
            }
        }
        count = 0;
        countX = 0;

        //links unten nach rechts oben
        for (int i = 2, j = 0; j < 3; j++, i--) {
            if (buttons[i][j].getText().equals("O")) count++;;
            if (buttons[i][j].getText().equals("X")) countX++;
        }
        if (count == 2 && countX == 0) {
            for (int i = 2, j = 0; j < 3; j++, i--) {
                if (buttons[i][j].getText().isEmpty()) {
                    buttons[i][j].setText("O");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean losingRows() {
        JButton[][] buttons =  layout.getButtons();
        int count = 0;
        int countO = 0;
        int row = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("X")) count++;
                if (buttons[i][j].getText().equals("O")) countO++;
            }
            if (count == 2 && countO == 0) row = i;
            count = 0;
            countO = 0;
        }

        if (row > -1) {
            for (int i = 0; i < 3; i++) {
                if (buttons[row][i].getText().isEmpty()) {
                    buttons[row][i].setText("O");
                    i = 3;
                }
            }
        }

        return row > -1;
    }

    private boolean losingColumn() {
        JButton[][] buttons =  layout.getButtons();
        int count = 0;
        int countO = 0;
        int column = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[j][i].getText().equals("X")) count++;
                if (buttons[j][i].getText().equals("O")) countO++;
            }
            if (count == 2 && countO == 0) column = i;
            count = 0;
            countO = 0;
        }

        if (column > -1) {
            for (int i = 0; i < 3; i++) {
                if (buttons[i][column].getText().isEmpty()) {
                    buttons[i][column].setText("O");
                    i = 3;
                }
            }
        }

        return column > -1;
    }

    private boolean losingDiagonal() {
        JButton[][] buttons =  layout.getButtons();
        int count = 0;
        int countO = 0;
        //Links oben nach rechts unten
        for (int j = 0; j < 3; j++) {
            if (buttons[j][j].getText().equals("X")) count++;;
            if (buttons[j][j].getText().equals("O")) countO++;
        }
        if (count == 2 && countO == 0) {
            for (int j = 0; j < 3; j++) {
                if (buttons[j][j].getText().isEmpty()) {
                    buttons[j][j].setText("O");
                    return true;
                }
            }
        }
        count = 0;
        countO = 0;

        //links unten nach rechts oben
        for (int i = 2, j = 0; j < 3; j++, i--) {
            if (buttons[i][j].getText().equals("X")) count++;;
            if (buttons[i][j].getText().equals("O")) countO++;
        }
        if (count == 2 && countO == 0) {
            for (int i = 2, j = 0; j < 3; j++, i--) {
                if (buttons[i][j].getText().isEmpty()) {
                    buttons[i][j].setText("O");
                    return true;
                }
            }
        }
        return false;
    }

    public void bot() {
        if (!winningRows() && !winningColumn() && !winningDiagonal()
                && !losingRows() && !losingColumn() && !losingDiagonal()) {
            botRandom();
        }
    }
    //End Bot Logic

    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic();
    }
}
