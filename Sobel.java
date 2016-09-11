/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageeditor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sobel {
    public static File file = null;


    public static void run() throws IOException {
    BufferedImage image = null;
    
    // the sobel matrix in two 2D arrays
    int[][] sx = {{-1,0,1},{-1,0,1},{-1,0,1}};
    int[][] sy = {{-1,-1,-1},{0,0,0},{1,1,1}};
    
    // a sobel template 2D array for calculation
    int[][] sob;
    
    // read the image
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.err.println(e);
        }
        
        // get image width and height
        int width = image.getWidth();
        int height = image.getHeight();
        
        // at first need to greyscale and populate sob[][] array
        sob = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int a = (pixel >> 24) & 0xff;
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;
                
                // calculate average
                int avg = (r+g+b)/3;
                
                sob[x][y] = avg;
                // replace RGB value with average
                pixel = (avg << 24) | (avg << 16) | (avg << 8) | avg;
                image.setRGB(x, y, pixel);
            }
        }
        
        // sobel calculation
        for (int y = 1; y < height-1; y++) {
            for (int x = 1; x < width-1; x++) {
                int px = (sx[0][0] * sob[x-1][y-1]) + (sx[0][1] * sob[x][y-1]) +
                (sx[0][2] * sob[x+1][y-1]) + (sx[1][0] * sob[x-1][y]) + 
                (sx[1][1] * sob[x][y]) + (sx[1][2] * sob[x+1][y]) + 
                (sx[2][0] * sob[x-1][y+1]) + (sx[2][1] * sob[x][y+1]) + 
                (sx[2][2] * sob[x+1][y+1]);
 
                int py = (sy[0][0] * sob[x-1][y-1]) + (sy[0][1] * sob[x][y-1]) +
                (sy[0][2] * sob[x+1][y-1]) + (sy[1][0] * sob[x-1][y]) + 
                (sy[1][1] * sob[x][y]) + (sy[1][2] * sob[x+1][y]) + 
                (sy[2][0] * sob[x-1][y+1]) + (sy[2][1] * sob[x][y+1]) + 
                (sy[2][2] * sob[x+1][y+1]);
                
                int pixel = (int) Math.sqrt((px * px) + (py * py));

                if (pixel>255) {
                    pixel = 255;
                } else if (pixel<0) {
                    pixel = 0;
                }
                
                Color pix = new Color(pixel,pixel,pixel);
                image.setRGB(x, y, pix.getRGB());
            }
        }
        
        // write image
        try {
            file = new File("image_sobel.jpg");
            image = image.getSubimage(1, 1, width-2, height-2);
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
