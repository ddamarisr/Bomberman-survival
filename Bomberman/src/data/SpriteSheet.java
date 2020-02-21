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
public class SpriteSheet {
    private int[] pixels;
    private BufferedImage image;
    public final int SIZEX;
    public final int SIZEY;
    private Sprite[] loadedSprites=null;
    private boolean spritesLoaded=false;
    private int spriteSizeX;

    public SpriteSheet(BufferedImage image) {
        
        this.image = image;
        SIZEX=image.getWidth();
        SIZEY=image.getHeight();
        
        pixels=new int [SIZEX*SIZEY];
        pixels=image.getRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZEX);
        
    }

    public int[] getPixels() {
        return pixels;
    }

    public BufferedImage getImage() {
        return image;
    }
    
    
    public void loadSprites (int spriteSizeX,int spriteSizeY){
        this.spriteSizeX=spriteSizeX;
        loadedSprites=new Sprite[(SIZEX/spriteSizeX)*(SIZEY/spriteSizeY)];
        int spriteID=0;
        for (int y=0;y<SIZEY;y=y+spriteSizeY) //+1 padding
        {
            for (int x=0;x<SIZEX;x=x+spriteSizeX){ //+1 padding 
                
                loadedSprites[spriteID]=new Sprite(this,x,y,spriteSizeX,spriteSizeY);
                spriteID++;
                
            }
        }
        
        
        spritesLoaded=true;
    }
    
    public Sprite getSprite(int x, int y){
        
        if (spritesLoaded){
            
            int spriteID=x+y*(SIZEX/spriteSizeX);
            
            if (spriteID<loadedSprites.length){
                return loadedSprites[spriteID];    
            }
            
            else {
                System.out.println("SpriteID of"+spriteID+" is out of range with a length of "+loadedSprites.length);
                
            }
            
        }
        else{
            System.out.println("SpriteSheet could not get a sprite with no loaded sprites.");
            
        }
        return null;
        
    }

    public Sprite[] getLoadedSprites() {
        return loadedSprites;
    }
    
    
}