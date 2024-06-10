import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameLogic extends JPanel{
    private Field[][] fields;
    public X[][] XonField;
    public O[][] OonField;
    private X x;
    private O o;
    Timer timer;
    Settings settings;
    ScorePanel scorePanel;
    public int player = 1;
    private int startingPlayer = 1;
    public boolean botActive = false;
    public int difficulty = 3;
    public int scoreX = 0;
    public int scoreO = 0;

    public GameLogic() {
        setBackground(Config.BACKGROUND_COLOR);
        fields = new Field[3][3];
        OonField = new O[3][3];
        XonField = new X[3][3];
        int space = Config.FIELD_SPACE;
        int height = Config.FIELD_SIZE + space;
        int width = Config.FIELD_SIZE * 3 + (2 * space);
        int start = (Config.GAME_SIZE - width) / 2 + (Config.FIELD_SIZE / 2);
        for (int i = 0; i < 3; i++) {
            int yP = start + i * (Config.FIELD_SIZE + space);
            for (int j = 0; j < 3; j++) {
                //Felder erzeugen
                int xP = start + j * (Config.FIELD_SIZE + space);
                Field f = new Field(this, xP, yP);
                fields[i][j] = f;

                //O auf Feldern erzeugen
                O o = new O(this, xP, yP);
                OonField[i][j] = o;

                //X auf Feldern erzeugen
                X x = new X(this, xP, yP);
                XonField[i][j] = x;
            }
        }

        // set panel size
        setPreferredSize(new Dimension(Config.GAME_SIZE, Config.GAME_SIZE));
        //Key Listener
        setFocusable(true);
        //Key Listener
        setFocusable(true);
        this.addMouseListener(new TTTMouseAdapter());
    }

    /**
     * Mouse Adapter
     */
    private class TTTMouseAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            Field[][] fs = fields;
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
                if (fields[fieldI][fieldJ].getText().isEmpty()) {
                    if (player % 2 != 0) {
                        player++;
                        playerTxt ="X";
                        fields[fieldI][fieldJ].setText(playerTxt);
                        XonField[fieldI][fieldJ].visible = true;
                    } else {
                        player++;
                        playerTxt = "O";
                        fields[fieldI][fieldJ].setText(playerTxt);
                        OonField[fieldI][fieldJ].visible = true;
                    }

                    if(botActive && !checkWin()) {
                        player++;
                        bot();
                    }

                    if (checkWin()) {
                        showWinner();
                    }
                    if (full()) {
                        delayedReset();
                    }

                }
            }
        }

    }

    public void setSettings(Settings s) {
        settings = s;
    }

    public void setScorePanel(ScorePanel s) {
        this.scorePanel = s;
    }

    public void onTick() {
        repaint();
    }

    public void start() {
        timer = new Timer(Config.LOOP_PERIOD, new GameLoop());
        timer.start();
    }

    private class GameLoop implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            onTick();
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        // paint panel
        super.paintComponent(graphics);
        // configure rendering pipeline: Enable antialiasing and high render quality
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        //graphics.setColor(BLACK);
        //graphics.fillRoundRect(0, 0, Config.GAME_SIZE, Config.GAME_SIZE, 15, 15);
        // render bricks, paddle, and ball
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j].render(graphics);
                XonField[i][j].render(graphics);
                OonField[i][j].render(graphics);
            }
        }
        // synchronize graphics state
    }

    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j].setText("");
                XonField[i][j].visible = false;
                OonField[i][j].visible = false;
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
        ActionListener actionAfter = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();

                // Stop the timer
                ((Timer)e.getSource()).stop();
            }
        };
        // Create a Timer that waits for 0,7 seconds
        Timer timer = new Timer(700, actionAfter);
        // Start the timer
        timer.start();
    }


    public void score(String playerTxt) {
        if (playerTxt.equals("X")) {
            scoreX++;
        } else {
            scoreO++;
        }
    }

    public boolean checkWin() {
        Field[][] fs = fields;

        for (int i = 0; i < fs.length; i++) {
            //Horizontal
            if (fs[i][0].getText().equals(fs[i][1].getText())
                    && fs[i][0].getText().equals(fs[i][2].getText())
                    && !fs[i][0].getText().isEmpty()) {
                return true;
            }

            //Vertikal
            if (fs[0][i].getText().equals(fs[1][i].getText())
                    && fs[0][i].getText().equals(fs[2][i].getText())
                    && !fs[0][i].getText().isEmpty()) {
                return true;
            }
        }

        //Diagonal
        if (!fs[1][1].getText().isEmpty() &&
                ((fs[0][0].getText().equals(fs[1][1].getText()) &&
                        fs[0][0].getText().equals(fs[2][2].getText())) ||
                        (fs[0][2].getText().equals(fs[1][1].getText()) &&
                                fs[0][2].getText().equals(fs[2][0].getText())))) {
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
            Color c = Config.X_COLOR;
            color = "rgb(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")";
        } else {
            Color c = Config.O_COLOR;
            color = "rgb(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")";
        }
        JOptionPane.showMessageDialog(this,
                String.format("<html><center><span style='font-family: Lucida Grande; font-size: 20pt; color: %s;'>Player %s wins!</span></center></html>", color, winner),
                "Winner", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/Assets/1021220.png"));
        reset();
        score(winner);
    }

    public boolean full() {
        Field[][] fs = fields;
        int full = 0;
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                if (!fields[i][j].content.isEmpty()) full++;
            }
        }
        return full == 9;
    }

    // Start Bot Logic

    private void botRandom() {
        Field[][] fs = fields;
        Random rand = new Random();
        int y = rand.nextInt(3);
        int x = rand.nextInt(3);

        int usedFields = 0;
        for (Field[] f1 : fs) {
            for (Field f : f1) {
                if (!f.content.isEmpty()) usedFields++;
            }
        }

        if (fs[y][x].content.isEmpty()) {
            fs[y][x].content = "O";
            OonField[y][x].visible = true;
        } else {
            if (usedFields < 9) botRandom();
        }
    }

    private boolean winningRows() {
        Field[][] fs = fields;
        int count = 0;
        int countX = 0;
        int row = -1;
        String botPlayer = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (fs[i][j].getText().equals("O")) count++;
                if (fs[i][j].getText().equals("X")) countX++;
            }
            if (count == 2 && countX == 0) row = i;
            count = 0;
            countX = 0;
        }

        if (row > -1) {
            for (int i = 0; i < 3; i++) {
                if (fs[row][i].getText().isEmpty()) {
                    fs[row][i].setText("O");
                    OonField[row][i].visible = true;
                    i = 3;
                }
            }
        }

        return row > -1;
    }

    private boolean winningColumn() {
        Field[][] fs = fields;
        int count = 0;
        int countX = 0;
        int column = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (fs[j][i].getText().equals("O")) count++;
                if (fs[j][i].getText().equals("X")) countX++;
            }
            if (count == 2 && countX == 0) column = i;
            count = 0;
            countX = 0;
        }

        if (column > -1) {
            for (int i = 0; i < 3; i++) {
                if (fs[i][column].getText().isEmpty()) {
                    fs[i][column].setText("O");
                    OonField[i][column].visible = true;
                    i = 3;
                }
            }
        }

        return column > -1;
    }

    private boolean winningDiagonal() {
        Field[][] fs = fields;
        int count = 0;
        int countX = 0;
        //Links oben nach rechts unten
        for (int j = 0; j < 3; j++) {
            if (fs[j][j].getText().equals("O")) count++;;
            if (fs[j][j].getText().equals("X")) countX++;
        }
        if (count == 2 && countX == 0) {
            for (int j = 0; j < 3; j++) {
                if (fs[j][j].getText().isEmpty()) {
                    fs[j][j].setText("O");
                    OonField[j][j].visible = true;
                    return true;
                }
            }
        }
        count = 0;
        countX = 0;

        //links unten nach rechts oben
        for (int i = 2, j = 0; j < 3; j++, i--) {
            if (fs[i][j].getText().equals("O")) count++;;
            if (fs[i][j].getText().equals("X")) countX++;
        }
        if (count == 2 && countX == 0) {
            for (int i = 2, j = 0; j < 3; j++, i--) {
                if (fs[i][j].getText().isEmpty()) {
                    fs[i][j].setText("O");
                    OonField[i][j].visible = true;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean losingRows() {
        Field[][] fs = fields;
        int count = 0;
        int countO = 0;
        int row = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (fs[i][j].getText().equals("X")) count++;
                if (fs[i][j].getText().equals("O")) countO++;
            }
            if (count == 2 && countO == 0) row = i;
            count = 0;
            countO = 0;
        }

        if (row > -1) {
            for (int i = 0; i < 3; i++) {
                if (fs[row][i].getText().isEmpty()) {
                    fs[row][i].setText("O");
                    OonField[row][i].visible = true;
                    i = 3;
                }
            }
        }

        return row > -1;
    }

    private boolean losingColumn() {
        Field[][] fs = fields;
        int count = 0;
        int countO = 0;
        int column = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (fs[j][i].getText().equals("X")) count++;
                if (fs[j][i].getText().equals("O")) countO++;
            }
            if (count == 2 && countO == 0) column = i;
            count = 0;
            countO = 0;
        }

        if (column > -1) {
            for (int i = 0; i < 3; i++) {
                if (fs[i][column].getText().isEmpty()) {
                    fs[i][column].setText("O");
                    OonField[i][column].visible = true;
                    i = 3;
                }
            }
        }

        return column > -1;
    }

    private boolean losingDiagonal() {
        Field[][] fs = fields;
        int count = 0;
        int countO = 0;
        //Links oben nach rechts unten
        for (int j = 0; j < 3; j++) {
            if (fs[j][j].getText().equals("X")) count++;;
            if (fs[j][j].getText().equals("O")) countO++;
        }
        if (count == 2 && countO == 0) {
            for (int j = 0; j < 3; j++) {
                if (fs[j][j].getText().isEmpty()) {
                    fs[j][j].setText("O");
                    OonField[j][j].visible = true;
                    return true;
                }
            }
        }
        count = 0;
        countO = 0;

        //links unten nach rechts oben
        for (int i = 2, j = 0; j < 3; j++, i--) {
            if (fs[i][j].getText().equals("X")) count++;;
            if (fs[i][j].getText().equals("O")) countO++;
        }
        if (count == 2 && countO == 0) {
            for (int i = 2, j = 0; j < 3; j++, i--) {
                if (fs[i][j].getText().isEmpty()) {
                    fs[i][j].setText("O");
                    OonField[i][j].visible = true;
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
                settings.setPlayerButton("Starting: O (Bot)");
            } else {
                settings.setPlayerButton("Starting: O");
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
}
