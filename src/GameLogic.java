import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameLogic extends JPanel{
    private Field[][] fields;
    Timer timer;
    Settings settings;
    public int player = 1;
    private int startingPlayer = 1;
    public boolean botActive = false;
    public int difficulty = 3;
    public int scoreX = 0;
    public int scoreO = 0;

    public GameLogic() {
        setBackground(Config.BACKGROUND_COLOR);
        fields = new Field[3][3];
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
                if (fields[fieldI][fieldJ].getFieldValue().isEmpty()) {
                    if (player % 2 != 0) {
                        player++;
                        fields[fieldI][fieldJ].setValueX();
                    } else {
                        player++;
                        fields[fieldI][fieldJ].setValueO();
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
            }
        }

        // Linien zeichnen
        g2d.setColor(Config.LINE_COLOR);
        float[] dashPattern = {5, 5}; // Länge der Striche und Lücken in Pixeln
        BasicStroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(0, Config.GAME_SIZE / 3, Config.GAME_SIZE, Config.GAME_SIZE / 3);
        g2d.drawLine(0, (Config.GAME_SIZE / 3) * 2, Config.GAME_SIZE, (Config.GAME_SIZE / 3) * 2);
        g2d.drawLine((Config.GAME_SIZE / 3), 0, (Config.GAME_SIZE / 3), Config.GAME_SIZE);
        g2d.drawLine((Config.GAME_SIZE / 3) * 2, 0, ((Config.GAME_SIZE / 3) * 2), Config.GAME_SIZE);
        // synchronize graphics state
    }

    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j].setValueEmpty();
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
            if (fs[i][0].getFieldValue().equals(fs[i][1].getFieldValue())
                    && fs[i][0].getFieldValue().equals(fs[i][2].getFieldValue())
                    && !fs[i][0].getFieldValue().isEmpty()) {
                return true;
            }

            //Vertikal
            if (fs[0][i].getFieldValue().equals(fs[1][i].getFieldValue())
                    && fs[0][i].getFieldValue().equals(fs[2][i].getFieldValue())
                    && !fs[0][i].getFieldValue().isEmpty()) {
                return true;
            }
        }

        //Diagonal
        return !fs[1][1].getFieldValue().isEmpty() &&
                ((fs[0][0].getFieldValue().equals(fs[1][1].getFieldValue()) &&
                        fs[0][0].getFieldValue().equals(fs[2][2].getFieldValue())) ||
                        (fs[0][2].getFieldValue().equals(fs[1][1].getFieldValue()) &&
                                fs[0][2].getFieldValue().equals(fs[2][0].getFieldValue())));
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
        Color c;
        if (winner.equals("X")) {
            c = Config.X_COLOR;
        } else {
            c = Config.O_COLOR;
        }
        color = "rgb(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")";
        JOptionPane.showMessageDialog(this,
                String.format("<html><center><span style='font-family: Lucida Grande; font-size: 20pt; color: %s;'>Player %s wins!</span></center></html>", color, winner),
                "Winner", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/Assets/1021220.png"));
        score(winner);
        reset();
    }

    public boolean full() {
        int full = 0;
        for (Field[] field : fields) {
            for (int j = 0; j < fields.length; j++) {
                if (!field[j].content.isEmpty()) full++;
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

        if (fs[y][x].content.isEmpty()) {
            fs[y][x].setValueO();
        } else if (!full()) {
            botRandom();
        }
    }

    private boolean checkWinPossibility(String player) {
        return checkLines(player, true) || checkLines(player, false) || checkDiagonals(player);
    }

    private boolean checkLines(String player, boolean isRow) {
        for (int i = 0; i < 3; i++) {
            int playerCount = 0;
            int opponentCount = 0;
            int emptyField = -1;
            for (int j = 0; j < 3; j++) {
                Field current;
                if (isRow) {
                    current = fields[i][j];
                } else {
                    current = fields[j][i];
                }

                if (current.getFieldValue().equals(player)) {
                    playerCount++;
                } else if (!current.getFieldValue().isEmpty()) {
                    opponentCount++;
                } else {
                    emptyField = j;
                }
            }
            if (playerCount == 2 && opponentCount == 0 && emptyField != -1) {
                if (isRow) {
                    fields[i][emptyField].setValueO();
                } else {
                    fields[emptyField][i].setValueO();
                }
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals(String player) {
        int[] playerCounts = new int[2];
        int[] opponentCounts = new int[2];
        int[][] emptyFields = new int[2][2];

        for (int i = 0; i < 3; i++) {
            if (fields[i][i].getFieldValue().equals(player)) {
                playerCounts[0]++;
            } else if (!fields[i][i].getFieldValue().isEmpty()) {
                opponentCounts[0]++;
            } else {
                emptyFields[0][0] = i;
                emptyFields[0][1] = i;
            }

            if (fields[2 - i][i].getFieldValue().equals(player)) {
                playerCounts[1]++;
            } else if (!fields[2 - i][i].getFieldValue().isEmpty()) {
                opponentCounts[1]++;
            } else {
                emptyFields[1][0] = 2 - i;
                emptyFields[1][1] = i;
            }
        }

        for (int i = 0; i < 2; i++) {
            if (playerCounts[i] == 2 && opponentCounts[i] == 0) {
                fields[emptyFields[i][0]][emptyFields[i][1]].setValueO();
                return true;
            }
        }
        return false;
    }

    public void bot() {
        if (difficulty == 3) {
            if (!checkWinPossibility("O") && !checkWinPossibility("X")) {
                botRandom();
            }
        }
        if (difficulty == 2) {
            Random rand = new Random();
            int diff = rand.nextInt(3);
            if (diff != 0) {
                if (!checkWinPossibility("O") && !checkWinPossibility("X")) {
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
    //End Bot Logic

    public void playerButtonBot() {
        if (startingPlayer % 2 == 0) {
            if (botActive) {
                settings.setPlayerButton("Starting: O (Bot)");
            } else {
                settings.setPlayerButton("Starting: O");
            }
        }
    }

    public void setStartingPlayerPlayer(int p) {
        startingPlayer = p;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

}
