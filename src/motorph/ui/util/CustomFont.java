package motorph.ui.util;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.font.TextAttribute;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CustomFont {

    private static Font neuePlakExtendedRegular;
    private static Font neuePlakExtendedBold;
    private static Font neuePlakExtendedSemiBold;

    public static Font getExtendedRegular(float size) {
        if (neuePlakExtendedRegular == null) {
            loadFont();
        }
        return neuePlakExtendedRegular.deriveFont(size);
    }

    public static Font getExtendedBold(float size) {
        if (neuePlakExtendedBold == null) {
            loadFont();
        }
        return neuePlakExtendedBold.deriveFont(size);
    }

    public static Font getExtendedSemiBold(float size) {
        if (neuePlakExtendedSemiBold == null) {
            loadFont();
        }
        return neuePlakExtendedSemiBold.deriveFont(size);
    }

    private static void loadFont() {
        try {
            InputStream regularStream = CustomFont.class.getResourceAsStream("/fonts/NeuePlakExtendedRegular.ttf");
            if (regularStream != null) {
                neuePlakExtendedRegular = Font.createFont(Font.TRUETYPE_FONT, regularStream);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(neuePlakExtendedRegular);
            } else {
                System.err.println("NeuePlakExtendedRegular Font file not found in /fonts/");
            }

            InputStream boldStream = CustomFont.class.getResourceAsStream("/fonts/NeuePlakExtendedBold.ttf");
            if (boldStream != null) {
                neuePlakExtendedBold = Font.createFont(Font.TRUETYPE_FONT, boldStream);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(neuePlakExtendedBold);
            } else {
                System.err.println("NeuePlakExtendedBold Font file not found in /fonts/");
            }

            InputStream semiBoldStream = CustomFont.class.getResourceAsStream("/fonts/NeuePlakExtendedSemiBold.ttf");
            if (semiBoldStream != null) {
                neuePlakExtendedSemiBold = Font.createFont(Font.TRUETYPE_FONT, semiBoldStream);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(neuePlakExtendedSemiBold);
            } else {
                System.err.println("NeuePlakExtendedSemiBold Font file not found in /fonts/");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (neuePlakExtendedRegular == null)
                neuePlakExtendedRegular = new Font("SansSerif", Font.PLAIN, 12);
            if (neuePlakExtendedBold == null)
                neuePlakExtendedBold = new Font("SansSerif", Font.BOLD, 12);
            if (neuePlakExtendedSemiBold == null)
                neuePlakExtendedSemiBold = new Font("SansSerif", Font.BOLD, 12);
        }
    }
}
