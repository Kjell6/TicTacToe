package model;

import config.Config;
import view.SettingsPanel;

import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class GameLogic{
    public Field[][] fields;
    SettingsPanel settings;
    public int player = 1;
    private int startingPlayer = 1;
    public boolean botActive = false;
    public int difficulty = 3;
    public int scoreX = 0;
    public int scoreO = 0;
    private final List<IWinnerInterface> subscribers;

    public GameLogic() {
        this.subscribers = new LinkedList<>();
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
    }


    public void setSettings(SettingsPanel s) {
        settings = s;
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

    /**
     * Subscribe/ add a listener to the subscriber list
     *
     * @param listener  the listener
     */
    public void subscribe(IWinnerInterface listener) {
        subscribers.add(listener);
    }

    /**
     * Unsubscribe/ remove a listener from the subscriber list
     */
    public void unsubscribe(IWinnerInterface listener) {
        subscribers.remove(listener);
    }

    /**
     * Notifies all subscribed listeners that someone won.
     */
    public void publishWinner() {
        for (IWinnerInterface listener : subscribers) {
            listener.showWinner();
        }
    }

    /**
     * Notifies all subscribed listeners of a display number change.
     *
     */
    public void publisBackgroundChange() {
        for (IWinnerInterface listener : subscribers) {
            listener.backGroundChange();
        }
    }

    /*
    public void changeDesign() {
        if (design == 1) {
            design = 2;
            config.Config.setDesign(design);
        }
        if (design == 2) {
            design = 1;
            config.Config.setDesign(design);
        }
    }
    */
}
