import java.awt.*;

public class X {
    protected java.awt.Color color;
    protected int xPosition;
    protected int yPosition;
    protected int size;
    protected GameLogic gameLog;
    public boolean visible;

    /**
     * Konstruktor. Initialisiert GameLogic, Position, Größe und Farbe.
     *
     * @param gl Spiellogik
     * @param xp x-Position
     * @param yp y-Position
     */
    public X(GameLogic gl, int xp, int yp) {
        this.gameLog = gl;
        visible = false;
        this.xPosition = xp;
        this.yPosition = yp;
        this.size = Config.X_SIZE;
        this.color = Config.X_COLOR;
    }
    public X(GameLogic gl, int xp, int yp, int size) {
        this.gameLog = gl;
        visible = false;
        this.xPosition = xp;
        this.yPosition = yp;
        this.size = size;
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

    /**
     * Setzt die Position des Objekts.
     *
     * @param x x-Position
     * @param y y-Position
     */
    public void setPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    /**
     * Gibt die x-Position zurück.
     *
     * @return x-Position
     */
    public int getX() {
        return this.xPosition;
    }

    /**
     * Gibt die y-Position zurück.
     *
     * @return y-Position
     */
    public int getY() {
        return this.yPosition;
    }

    /**
     * Gibt die x-Größe zurück.
     *
     * @return x-Größe
     */
    public int getXSize() {
        return this.size;
    }

    /**
     * Gibt die y-Größe zurück.
     *
     * @return y-Größe
     */
    public int getYSize() {
        return this.size;
    }
}
