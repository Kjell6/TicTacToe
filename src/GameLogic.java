import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameLogic {
    private int scoreX = 0;
    private int scoreY = 0;
    private int startingPlayer = 1;
    public int player = 1;
    public boolean botActive = false;
    public int difficulty = 3;
    private Layout layout;

    public GameLogic() {
        this.layout = new Layout(this);
    }

    public void reset() {
        JButton[][] buttons = layout.getButtons();
        for (JButton bs[] : buttons) {
            for (JButton b : bs) {
                b.setText("");
            }
        }
        if (botActive && startingPlayer % 2 == 0) {
            bot();
            player = 1;
        } else {
            player = startingPlayer;
        }
    }

    public void delayedReset() {
        // Create a Timer that waits for 0,7 seconds
        Timer timer = new Timer(700, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Action 2
                reset();

                // Stop the timer
                ((Timer)e.getSource()).stop();
            }
        });

        // Start the timer
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start();
    }

    public void score(String playerTxt) {
        if (playerTxt.equals("X")) {
            scoreX++;
        } else {
            scoreY++;
        }
        JEditorPane[] scores = layout.getScoreTexts();
        scores[0].setText(String.format("<html><center><span style='font-family: Lucida Grande; font-size: 25pt; color: rgb(35, 68, 167);'>X - %d</span></center></html>", scoreX));
        scores[1].setText(String.format("<html><center><span style='font-family: Lucida Grande; font-size: 25pt; color: rgb(178, 70, 145);'>O - %d</span></center></html>", scoreY));
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

    public String getWinner() {
        String winner = "";
        if (player % 2 == 0) {
            winner ="X";
        } else {
            winner = "O";
            if (botActive && startingPlayer % 2 == 0) player++;
        }
        return winner;
    }

    public void showWinner() {
        String winner = getWinner();
        String color = "";
        if (winner.equals("X")) {
            color = "rgb(35, 68, 167)";
        } else {
            color = "rgb(178, 70, 145)";
        }
        JOptionPane.showMessageDialog(layout.frame,
                String.format("<html><center><span style='font-family: Lucida Grande; font-size: 20pt; color: %s;'>Player %s wins!</span></center></html>", color, winner),
            "Winner", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("/Users/kjell/Dokumente/Programmieren/TicTacToe/src/1021220.png"));
        reset();
        score(winner);
    }

    public boolean full() {
        JButton buttons[][] = layout.getButtons();
        int full = 0;
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                if (!buttons[i][j].getText().isEmpty()) full++;
            }
        }
        return full == 9;
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
            buttons[y][x].setForeground(new Color(178, 70, 145));
            Font currentFont = buttons[y][x].getFont();
            Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), 20); // Hier 20 durch Ihre gewünschte Größe ersetzen
            buttons[y][x].setFont(newFont);
        } else {
            if (usedFields < 9) botRandom();
        }
    }

    private boolean winningRows() {
        JButton[][] buttons = layout.getButtons();
        int count = 0;
        int countX = 0;
        int row = -1;
        String botPlayer = "";
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
                    buttons[row][i].setForeground(new Color(178, 70, 145));
                    Font currentFont = buttons[row][i].getFont();
                    Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), 20); // Hier 20 durch Ihre gewünschte Größe ersetzen
                    buttons[row][i].setFont(newFont);
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
                    buttons[i][column].setForeground(new Color(178, 70, 145));
                    Font currentFont = buttons[i][column].getFont();
                    Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), 20); // Hier 20 durch Ihre gewünschte Größe ersetzen
                    buttons[i][column].setFont(newFont);
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
                    buttons[j][j].setForeground(new Color(178, 70, 145));
                    Font currentFont = buttons[j][j].getFont();
                    Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), 20); // Hier 20 durch Ihre gewünschte Größe ersetzen
                    buttons[j][j].setFont(newFont);
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
                    buttons[j][j].setForeground(new Color(178, 70, 145));
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
                    buttons[row][i].setForeground(new Color(178, 70, 145));
                    Font currentFont = buttons[row][i].getFont();
                    Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), 20); // Hier 20 durch Ihre gewünschte Größe ersetzen
                    buttons[row][i].setFont(newFont);
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
                    buttons[i][column].setForeground(new Color(178, 70, 145));
                    Font currentFont = buttons[i][column].getFont();
                    Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), 20); // Hier 20 durch Ihre gewünschte Größe ersetzen
                    buttons[i][column].setFont(newFont);
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
                    buttons[j][j].setForeground(new Color(178, 70, 145));
                    Font currentFont = buttons[j][j].getFont();
                    Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), 20); // Hier 20 durch Ihre gewünschte Größe ersetzen
                    buttons[j][j].setFont(newFont);
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
                    buttons[i][j].setForeground(new Color(178, 70, 145));
                    Font currentFont = buttons[i][j].getFont();
                    Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), 20); // Hier 20 durch Ihre gewünschte Größe ersetzen
                    buttons[i][j].setFont(newFont);
                    return true;
                }
            }
        }
        return false;
    }

    public void bot() {
        if (difficulty == 3) {
            if (!winningRows() && !winningColumn() && !winningDiagonal()
                    && !losingRows() && !losingColumn() && !losingDiagonal()) {
                botRandom();
            }
        }
        if (difficulty == 2) {
            Random rand = new Random();
            int diff = rand.nextInt(3);
            if (diff != 0) {
                if (!winningRows() && !winningColumn() && !winningDiagonal()
                        && !losingRows() && !losingColumn() && !losingDiagonal()) {
                    botRandom();
                }
            } else {
                botRandom();
            }
        }
        if (difficulty == 1) {
            botRandom();
        }
    }

    public void playerButtonBot() {
        if (startingPlayer % 2 == 0) {
            if (botActive) {
                layout.setPlayerButton("Starting Player: O (Bot)");
            } else {
                layout.setPlayerButton("Starting Player: O");
            }
        }
    }
    //End Bot Logic

    public void setStartingPlayerPlayer(int p) {
        startingPlayer = p;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public int getPlayer() {
        return player;
    }

    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic();
    }
}