/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.awt.image.BufferedImage;

/**
 *
 * @author danie
 */
public class Sprite {
    int width, height;
    int []pixels;
    
    public Sprite(SpriteSheet sheet,int startX,int StartY,int width,int height){
        this.width=width;
        this.height=height;
        
        pixels=new int [width*height];
        sheet.getImage().getRGB(startX, StartY, width, height, pixels, 0, width);
        
    }
    
    public Sprite (BufferedImage image){
        width=image.getWidth();
        height=image.getHeight();
        
        pixels=new int[width*height];
        
        image.getRGB(0, 0, width, height, pixels, 0, width);
        
    }
    
    public Sprite(){
        
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }
    
    
    
}