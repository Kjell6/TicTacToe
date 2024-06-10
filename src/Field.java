import java.awt.*;

public class Field {
    protected java.awt.Color color;
    protected int xPosition;
    protected int yPosition;
    protected int size;
    protected GameLogic gameLog;
    public String content;

    /**
     * Konstruktor. Initialisiert GameLogic, Position, Größe und Farbe.
     *
     * @param gl Spiellogik
     * @param xp x-Position
     * @param yp y-Position
     */
    public Field(GameLogic gl, int xp, int yp) {
        this.gameLog = gl;
        this.xPosition = xp;
        this.yPosition = yp;
        this.size = Config.FIELD_SIZE;
        this.content = "";
        this.color = Config.FIELD_COLOR;
    }

    public void render(Graphics graphics) {
        graphics.setColor(this.color);
        graphics.fillRoundRect(xPosition - size / 2, yPosition - size / 2, size, size, 50, 50);
    }

    public String getText() {
        return content;
    }

    public void setText(String s) {
        content = s;
    }

    /**
     * Gibt die Hitbox des Objekts zurück.
     *
     * @return Hitbox
     */
    public Rectangle getHitBox() {
        return new Rectangle(this.xPosition - this.size / 2, this.yPosition - this.size / 2, this.size, this.size);
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
