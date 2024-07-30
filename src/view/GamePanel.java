package view;

import config.Config;
import controller.MouseController;
import model.Field;
import model.GameLogic;
import model.IWinnerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements IWinnerInterface {
    GameLogic gameLogic;
    Timer timer;

    public GamePanel(GameLogic gl) {
        gameLogic = gl;
        setBackground(Config.BACKGROUND_COLOR);
        gameLogic.fields = new Field[3][3];
        int space = Config.FIELD_SPACE;
        int height = Config.FIELD_SIZE + space;
        int width = Config.FIELD_SIZE * 3 + (2 * space);
        int start = (Config.GAME_SIZE - width) / 2 + (Config.FIELD_SIZE / 2);
        for (int i = 0; i < 3; i++) {
            int yP = start + i * (Config.FIELD_SIZE + space);
            for (int j = 0; j < 3; j++) {
                //Felder erzeugen
                int xP = start + j * (Config.FIELD_SIZE + space);
                Field f = new Field(gameLogic, xP, yP);
                gameLogic.fields[i][j] = f;
            }
        }

        // set panel size
        setPreferredSize(new Dimension(Config.GAME_SIZE, Config.GAME_SIZE));
        //Key Listener
        setFocusable(true);
        //Key Listener
        setFocusable(true);
        this.addMouseListener(new MouseController(gameLogic));
    }

    public void onTick() {
        repaint();
    }

    public void start() {
        timer = new Timer(Config.LOOP_PERIOD, new GameLoop());
        timer.start();
    }

    @Override
    public void showWinner() {
        String winner = gameLogic.getWinner();
        String color = "";
        Color c;
        if (winner.equals("X")) {
            c = Config.X_COLOR;
        } else {
            c = Config.O_COLOR;
        }
        if (Config.design == 2) c = Color.BLACK;
        color = "rgb(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")";
        JOptionPane.showMessageDialog(this,
                String.format("<html><center><span style='font-family: Lucida Grande; font-size: 20pt; color: %s;'>Player %s wins!</span></center></html>", color, winner),
                "Winner", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/Assets/1021220.png"));
        gameLogic.score(winner);
        gameLogic.reset();
    }

    @Override
    public void backGroundChange() {
        setBackground(Config.BACKGROUND_COLOR);
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
        //graphics.fillRoundRect(0, 0, config.Config.GAME_SIZE, config.Config.GAME_SIZE, 15, 15);
        // render bricks, paddle, and ball
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameLogic.fields[i][j].render(graphics);
            }
        }

        // Linien zeichnen#
        if (Config.design == 1) {
            g2d.setColor(Config.LINE_COLOR);
            int thickness = 10;
            int space = 30;
            g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(space, Config.GAME_SIZE / 3, Config.GAME_SIZE - space, Config.GAME_SIZE / 3);
            g2d.drawLine(space, (Config.GAME_SIZE / 3) * 2, Config.GAME_SIZE - space, (Config.GAME_SIZE / 3) * 2);
            g2d.drawLine((Config.GAME_SIZE / 3), space, (Config.GAME_SIZE / 3), Config.GAME_SIZE - space);
            g2d.drawLine((Config.GAME_SIZE / 3) * 2, space, ((Config.GAME_SIZE / 3) * 2), Config.GAME_SIZE - space);
        }
        if (Config.design == 2) {
            g2d.setColor(Config.LINE_COLOR);
            float[] dashPattern = {5, 5}; // Länge der Striche und Lücken in Pixeln
            BasicStroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0);
            g2d.setStroke(dashed);
            g2d.drawLine(0, Config.GAME_SIZE / 3, Config.GAME_SIZE, Config.GAME_SIZE / 3);
            g2d.drawLine(0, (Config.GAME_SIZE / 3) * 2, Config.GAME_SIZE, (Config.GAME_SIZE / 3) * 2);
            g2d.drawLine((Config.GAME_SIZE / 3), 10, (Config.GAME_SIZE / 3), Config.GAME_SIZE);
            g2d.drawLine((Config.GAME_SIZE / 3) * 2, 10, ((Config.GAME_SIZE / 3) * 2), Config.GAME_SIZE);
        }
        // synchronize graphics state
    }
}
