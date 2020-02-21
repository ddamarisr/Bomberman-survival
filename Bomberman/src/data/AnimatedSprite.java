/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author danie
 */
import businessLogic.Game;
import java.awt.image.BufferedImage;
public class AnimatedSprite extends Sprite{
    
    private Sprite [] sprites;
    
    private int currentSprite=0;
    
    private final int tranSpeed;
    
    private int counter=0;
    
    private int startSprite=0;
    
    private int endSprite;

    public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int tranSpeed) {
        sprites=new Sprite[positions.length];
        this.tranSpeed=tranSpeed;
        this.endSprite=positions.length-1;
        for (int i=0;i<positions.length;i++){
            sprites[i]=new Sprite(sheet, positions[i].x, positions[i].y,positions[i].w, positions[i].h);
        }
    }
    
    
    
   
    
    // @parameter tranSpeed represent how many frames pass before the sprite goes ahead
    public AnimatedSprite(BufferedImage [] images, int tranSpeed) {
        sprites=new Sprite [images.length];
        this.tranSpeed=tranSpeed;
        this.endSprite=images.length-1;
        for (int i=0;i<images.length;i++){
            sprites[i]=new Sprite(images[i]);
        }
    }
    
 
    
    public AnimatedSprite(SpriteSheet sheet, int tranSpeed) {
        sprites=sheet.getLoadedSprites();
        this.tranSpeed=tranSpeed;
        this.endSprite=sprites.length-1; 
        
    }

    public void update(Game game) {
        if(counter>=tranSpeed){
            counter=0;
            incrementSprite();
        }
        counter++;
    }
    
   
    public void setAnimationRange(int startSprite,int endSprite){
        this.startSprite=startSprite;
        this.endSprite=endSprite;
        reset();
    }
    
    public void setBombAnimationRange(int startSprite,int endSprite){
        this.startSprite=startSprite;
        this.endSprite=endSprite;
        resetBomb();
    }
    
    public void reset(){
        counter=0;
        currentSprite=startSprite+1;
    }
    
    public void resetBomb(){
        counter=0;
        currentSprite=startSprite;
    }
    
    @Override
    public int getWidth() {
        return sprites[currentSprite].getWidth();
    }

    @Override
    public int getHeight() {
        return sprites[currentSprite].getWidth();
    }

    @Override
    public int[] getPixels() {
        return sprites[currentSprite].getPixels();
    }
    
    public void incrementSprite(){
        currentSprite++;
        if (currentSprite>=endSprite){
            currentSprite=startSprite;
        }
    }
   
}
