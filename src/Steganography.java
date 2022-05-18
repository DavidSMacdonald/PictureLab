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

    public static void main(String[] args) {
        Picture beach = new Picture ("butterfly1.jpg");
        beach.explore();
        Picture copy = testSetLow(beach, Color.PINK);
        copy.explore();
        Picture copy2 = revealPicture(copy);
        copy2.explore();

    }
}
