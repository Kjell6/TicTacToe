package model;

import config.Config;

import java.awt.*;

public class X extends PlayerObject {

    /**
     * Konstruktor. Initialisiert model.GameLogic, Position, Größe und Farbe.
     *
     * @param gl Spiellogik
     * @param xp x-Position
     * @param yp y-Position
     */
    public X(GameLogic gl, int xp, int yp) {
        super(gl, xp, yp);
        this.size = Config.X_SIZE;
    }
    public X(GameLogic gl, int xp, int yp, int size) {
        super(gl, xp, yp, size);
    }

    public void render(Graphics g) {
        if (visible) {
            if (Config.design == 1) {
                int thickness = size / 2;
                g.setColor(Config.X_COLOR);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                // Diagonal from top-left to bottom-right
                g2d.drawLine(xPosition - size / 2, yPosition - size / 2, xPosition + size / 2, yPosition + size / 2);
                // Diagonal from bottom-left to top-right
                g2d.drawLine(xPosition - size / 2, yPosition + size / 2, xPosition + size / 2, yPosition - size / 2);
            }
            if (Config.design == 2 ) {
                int thickness = size / 3;
                g.setColor(Config.X_COLOR);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(thickness));

                // Diagonal from top-left to bottom-right
                g2d.drawLine(xPosition - size / 2, yPosition - size / 2, xPosition + size / 2, yPosition + size / 2);
                // Diagonal from bottom-left to top-right
                g2d.drawLine(xPosition - size / 2, yPosition + size / 2, xPosition + size / 2, yPosition - size / 2);
            }
        }
    }
}
