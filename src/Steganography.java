import java.awt.*;

public class Steganography {
    public static void clearLow(Pixel p) {
        int nRed = p.getRed();
        int nGreen = p.getGreen();
        int nBlue = p.getBlue();

        nRed = nRed/4*4;
        nGreen = nGreen/4*4;
        nBlue = nBlue/4*4;

        p.setColor(new Color(nRed,nGreen,nBlue));
    }

    public static Picture testClearLow(Picture img) {
        Picture copy = new Picture(img);
        Pixel[][] pix = copy.getPixels2D();

        for (int r = 0; r < pix.length; r++)
            for (int c = 0; c < pix[0].length; c++)
                clearLow(pix[r][c]);

        return copy;
    }

    public static void setLow(Pixel p, Color c) {
        int nRed = p.getRed();
        int nGreen = p.getGreen();
        int nBlue = p.getBlue();

        nRed = nRed/4*4     + c.getRed()/64;
        nBlue = nBlue/4*4   + c.getBlue()/64;
        nGreen = nGreen/4*4 + c.getGreen()/64;

        p.setColor(new Color(nRed,nGreen,nBlue));

    }

    public static void setLow4(Pixel p, Color c) {
        int nRed = p.getRed();
        int nGreen = p.getGreen();
        int nBlue = p.getBlue();

        nRed = nRed/16*16     + c.getRed()/16;
        nBlue = nBlue/16*16  + c.getBlue()/16;
        nGreen = nGreen/16*16 + c.getGreen()/16;

        p.setColor(new Color(nRed,nGreen,nBlue));

    }

    public static Picture testSetLow(Picture p, Color co) {
        Picture copy = new Picture(p);
        Pixel[][] pix = copy.getPixels2D();

        for (int r = 0; r < pix.length; r++)
            for (int c = 0; c < pix[0].length; c++)
                setLow(pix[r][c], co);

        return copy;

    }

    public static Picture revealPicture(Picture hidden) {
        Picture copy = new Picture(hidden);
        Pixel[][] pixels = copy.getPixels2D();
        Pixel[][] source = hidden.getPixels2D();

        int nRed;
        int nGreen;
        int nBlue;

        for (int i = 0; i < pixels.length; i++)
            for (int j = 0; j < pixels[0].length; j++) {
                nRed = source[i][j].getRed() % 4 * 64;
                nGreen = source[i][j].getGreen() % 4 * 64;
                nBlue = source[i][j].getBlue() % 4 * 64;

                pixels[i][j].setColor(new Color(nRed,nGreen,nBlue));
            }
        return copy;
    }
    public static Picture revealPicture4(Picture hidden) {
        Picture copy = new Picture(hidden);
        Pixel[][] pixels = copy.getPixels2D();
        Pixel[][] source = hidden.getPixels2D();

        int nRed;
        int nGreen;
        int nBlue;

        for (int i = 0; i < pixels.length; i++)
            for (int j = 0; j < pixels[0].length; j++) {
                nRed = source[i][j].getRed() % 16 * 16;
                nGreen = source[i][j].getGreen() % 16 * 16;
                nBlue = source[i][j].getBlue() % 16 * 16;

                pixels[i][j].setColor(new Color(nRed,nGreen,nBlue));
            }
        return copy;
    }

    public static boolean canHide(Picture p1, Picture p2) {
        return p1.getHeight()==p2.getHeight()&&p1.getWidth()==p2.getWidth();
    }

    public static Picture hidePicture(Picture source, Picture secret) {
        if (!canHide(source, secret)) return null;
        Picture copy = new Picture(source);
        Pixel[][] pixels = copy.getPixels2D(); // new image pixels
        Pixel[][] sec = secret.getPixels2D();  // base swan image pixels

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                /**
                 * Set every pixel in new image to the first two bits of swan
                 *
                 */
                setLow(pixels[i][j], sec[i][j].getColor());
            }
        }
        return copy;
    }

    public static Picture hidePicture4(Picture source, Picture secret) {
        if (!canHide(source, secret)) return null;
        Picture copy = new Picture(source);
        Pixel[][] pixels = copy.getPixels2D(); // new image pixels
        Pixel[][] sec = secret.getPixels2D();  // base swan image pixels

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                /**
                 * Set every pixel in new image to the first two bits of swan
                 *
                 */
                setLow4(pixels[i][j], sec[i][j].getColor());
            }
        }
        return copy;
    }



    public static void main(String[] args) {
        Picture swan4 = new Picture ("swan.jpg");
        Picture gorge4 = new Picture("gorge.jpg");
        gorge4.explore();
        Picture hidden4 = hidePicture4(gorge4, swan4);
        hidden4.explore();
        Picture revealed4 = revealPicture4(hidden4);
        revealed4.explore();

        Picture swan = new Picture ("swan.jpg");
        Picture gorge = new Picture("gorge.jpg");
        gorge.explore();
        Picture hidden = hidePicture(gorge, swan);
        hidden.explore();
        Picture revealed = revealPicture(hidden);
        revealed.explore();
    }
}
