/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import businessLogic.Game;
import businessLogic.RenderHandler;

/**
 *
 * @author danie
 */
public class Explosion implements GameObject {
    private int xPos;
    private int yPos;
    private Sprite sprite;
    private AnimatedSprite  animatedSprite=null;
    
    public Explosion(int xPos,int yPos,Sprite sprite){
           
        this.sprite=sprite;
        this.xPos=xPos;
        this.yPos=yPos;
        
        if (sprite!=null && sprite instanceof AnimatedSprite){
            animatedSprite=(AnimatedSprite) sprite;
            animatedSprite.setBombAnimationRange(0,3); 
        }   
}
    
@Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        //renderer.renderRectangle(playerRectangle, xZoom, yZoom);
        if(animatedSprite!=null){
            renderer.renderSprite(animatedSprite, xPos , yPos, xZoom, yZoom);
        } else if(sprite!=null){
            renderer.renderSprite(sprite,xPos, yPos, xZoom, yZoom);
        } 
    }
    
@Override
    public void update(Game game) {
        animatedSprite.update(game);
    }

    @Override
    public String toString() {
        return "Explosion{" + "xPos=" + xPos + ", yPos=" + yPos+ '}';
    }
    
    
    
}