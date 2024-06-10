import java.awt.*;

public class O {
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
    public O(GameLogic gl, int xp, int yp) {
        this.gameLog = gl;
        this.xPosition = xp;
        this.yPosition = yp;
        this.size = Config.O_SIZE;
        this.color = Config.O_COLOR;
    }
    public O(GameLogic gl, int xp, int yp, int size) {
        this.gameLog = gl;
        this.xPosition = xp;
        this.yPosition = yp;
        this.size = size;
        this.color = Config.O_COLOR;
    }

    public void render(Graphics g) {
        if (this.visible) {
            int outerRadius = size;
            int innerRadius = size / 2;
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            // Draw outer circle
            g2d.fillOval(xPosition - outerRadius, yPosition - outerRadius, 2 * outerRadius, 2 * outerRadius);
            // Draw inner circle to create a hole
            Color innercolor;
            if (yPosition < 50) {
                innercolor = Config.BACKGROUND_COLOR;
            } else {
                innercolor = Config.FIELD_COLOR;
            }
            g2d.setColor(innercolor); // Set color to the background color to create a "hole"
            g2d.fillOval(xPosition - innerRadius, yPosition - innerRadius, 2 * innerRadius, 2 * innerRadius);
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
