package config;

import java.awt.*;

public class Config {
    public static final int FIELD_SIZE = 150;
    public static final int FIELD_SPACE = 7;
    public static final int GAME_SIZE = Config.FIELD_SIZE * 3 + Config.FIELD_SPACE * 4; //628
    public static final int LOOP_PERIOD = 50;
    public static final int SETTINGS_Y_SIZE = 140; //100
    public static final int SCORE_Y_Size = 45;
    public static final int X_SIZE = FIELD_SIZE / 2;
    public static final int O_SIZE = (int) (FIELD_SIZE / 2.5);
    public static Color X_COLOR = new Color(39, 196, 86);
    public static Color O_COLOR = new Color(92, 31, 239, 255);
    public static Color FIELD_COLOR = new Color(0, 8, 7);
    public static Color BUTTON_COLOR = new Color(44, 44, 44);
    public static Color BUTTON_COLOR_Pressed = new Color(28, 28, 28);
    public static Color LINE_COLOR = new Color(246, 248, 255);
    public static Color BACKGROUND_COLOR = new Color(0, 8, 7, 255);
    public static int design = 1;

    public static void setDesign(int d) {
        if (d == 1) {
            design = 1;
            X_COLOR = new Color(39, 196, 86);
            O_COLOR = new Color(92, 31, 239, 255);
            FIELD_COLOR = new Color(0, 8, 7);
            BUTTON_COLOR = new Color(44, 44, 44);
            BUTTON_COLOR_Pressed = new Color(28, 28, 28);
            LINE_COLOR = new Color(44, 44, 44);
            BACKGROUND_COLOR = new Color(0, 8, 7, 255);
        }
        if (d == 2) {
            design = 2;
            X_COLOR = new Color(255, 255, 255);
            O_COLOR = new Color(255, 255, 255, 255);
            FIELD_COLOR = new Color(0, 0, 0);
            BUTTON_COLOR = new Color(255, 255, 255);
            BUTTON_COLOR_Pressed = new Color(141, 141, 141);
            LINE_COLOR = new Color(44, 42, 44);
            BACKGROUND_COLOR = new Color(0, 0, 0, 255);
        }
    }
}
