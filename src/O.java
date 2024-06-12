import java.awt.*;

public class O extends PlayerObject{

    /**
     * Konstruktor. Initialisiert GameLogic, Position, Größe und Farbe.
     *
     * @param gl Spiellogik
     * @param xp x-Position
     * @param yp y-Position
     */
    public O(GameLogic gl, int xp, int yp) {
        super(gl, xp, yp);
        this.size = Config.O_SIZE;
        this.color = Config.O_COLOR;
    }
    public O(GameLogic gl, int xp, int yp, int size) {
        super(gl, xp, yp, size);
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
}
