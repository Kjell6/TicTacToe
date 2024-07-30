package view;

import config.Config;
import model.GameLogic;
import model.O;
import model.X;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScorePanel extends JPanel{

    Timer timer;
    GameLogic logic;
    O oScore;
    X xScore;

    public ScorePanel(GameLogic gl) {
        setBackground(Config.BACKGROUND_COLOR);
        logic = gl;
        setPreferredSize(new Dimension(Config.GAME_SIZE, Config.SCORE_Y_Size));
        int size = 18;
        xScore = new X(gl, (Config.GAME_SIZE / 3) - (size / 2), (Config.SCORE_Y_Size / 2), size);
        xScore.visible = true;
        oScore = new O(gl, (Config.GAME_SIZE / 3) * 2- (size / 2), (Config.SCORE_Y_Size / 2), size);
        oScore.visible = true;
    }

    public void start() {
        timer = new Timer(Config.LOOP_PERIOD, new GameLoop());
        timer.start();
    }


    private void onTick() {
        repaint();
    }


    @Override
    protected void paintComponent(Graphics graphics) {
        //Hintergrund
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;

        // Zeichne ein Rechteck mit abgerundeten unteren Ecken
        int x = 0;
        int y = 0;
        int width = Config.GAME_SIZE;
        int height = Config.SCORE_Y_Size;
        int arcWidth = 10; // Breite des abgerundeten Bogens
        int arcHeight = 10; // Höhe des abgerundeten Bogens

        //g2d.fillRect(x, y, width, (height / 6) * 5);
        //g2d.fillRoundRect(x, y + height / 2, width, height / 2, arcWidth, arcHeight);

        //Score zeichnen

        oScore.render(graphics);
        xScore.render(graphics);

        Font font = new Font("Arial", Font.BOLD, 40);
        FontMetrics fm = graphics.getFontMetrics(graphics.getFont());
        //Spieler X
        g2d.setColor(Config.X_COLOR); // Farbe für Spieler X
        g2d.setFont(font);
        String score = " : " + logic.scoreX;
        g2d.drawString(score, xScore.xPosition + (xScore.size / 2),
                Config.SCORE_Y_Size / 2 + (fm.getHeight() / 2) + Config.FIELD_SPACE);

        //Spieler O
        score = " : " + logic.scoreO;
        g2d.setColor(Config.O_COLOR); // Farbe für Spieler O
        g2d.drawString(score, oScore.xPosition + (oScore.size / 2) * 2,
                Config.SCORE_Y_Size / 2 + (fm.getHeight() / 2) + Config.FIELD_SPACE);

        setBackground(Config.BACKGROUND_COLOR);
    }

    private class GameLoop implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            onTick();
        }
    }
}
