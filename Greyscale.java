/*
 * Greyscale.java
 * JAVA app for converting image to greyscale
 * @author Gabor
 * 2016
 */
package imageeditor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Greyscale {
    public static File file = null;

    public void run() throws IOException {
        BufferedImage image = null;
        
        // read the image
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.err.println(e);
        }
        
        // get image width and height
        int width = image.getWidth();
        int height = image.getHeight();
        
        // convert to greyscale
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int a = (pixel >> 24) & 0xff;
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;
                
                // calculate average
                int avg = (r+g+b)/3;
                
                // replace RGB value with average
                pixel = (avg << 24) | (avg << 16) | (avg << 8) | avg;
                image.setRGB(x, y, pixel);
            }
        }
        
        // write image
        try {
            file = new File("image_grey.jpg");
            ImageIO.write(image, "jpg", file);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public void setFile (File myFile) {
        this.file = myFile;
    }
    
    public File getFile() {
        return file;
    }
}
