import java.awt.*;

public class PlayerObject {
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
    public PlayerObject(GameLogic gl, int xp, int yp) {
        this.gameLog = gl;
        visible = false;
        this.xPosition = xp;
        this.yPosition = yp;
    }
    public PlayerObject(GameLogic gl, int xp, int yp, int size) {
        this.gameLog = gl;
        visible = false;
        this.xPosition = xp;
        this.yPosition = yp;
        this.size = size;
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
