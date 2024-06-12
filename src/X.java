import java.awt.*;

public class X extends PlayerObject{

    /**
     * Konstruktor. Initialisiert GameLogic, Position, Größe und Farbe.
     *
     * @param gl Spiellogik
     * @param xp x-Position
     * @param yp y-Position
     */
    public X(GameLogic gl, int xp, int yp) {
        super(gl, xp, yp);
        this.size = Config.X_SIZE;
        this.color = Config.X_COLOR;
    }
    public X(GameLogic gl, int xp, int yp, int size) {
        super(gl, xp, yp, size);
        this.color = Config.X_COLOR;
    }

    public void render(Graphics g) {
        if (visible) {
            int thickness = size / 2;
            g.setColor(color);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));


            // Diagonal from top-left to bottom-right
            g2d.drawLine(xPosition - size / 2, yPosition - size / 2, xPosition + size / 2, yPosition + size / 2);
            // Diagonal from bottom-left to top-right
            g2d.drawLine(xPosition - size / 2, yPosition + size / 2, xPosition + size / 2, yPosition - size / 2);
        }
    }
}
