/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import businessLogic.Game;
import businessLogic.RenderHandler;
import java.util.ArrayList;

/**
 *
 * @author juan david carrilllo
 */
public class Bomb implements GameObject {
    private ArrayList <GameObject> objects;
    private int xPos;
    private int yPos;
    private Sprite sprite;
    private AnimatedSprite  animatedSprite=null;
    private int columnPos;
    private int rowPos;
    
    
    public Bomb(Coordinate bombPos, Sprite sprite){
           
        this.sprite=sprite;
        this.xPos=bombPos.getyPos();
        this.yPos=bombPos.getxPos();
        this.columnPos=(int) (Math.floorDiv(bombPos.getyPos(), 16 * RenderHandler.yZoom));
        this.rowPos = (int) (Math.floorDiv(bombPos.getxPos(), 16 * RenderHandler.xZoom));
        
        if (sprite!=null && sprite instanceof AnimatedSprite){
            animatedSprite=(AnimatedSprite) sprite;
            animatedSprite.setBombAnimationRange(0,3); 
            
        
    }
        
    }

    public void scanBomb(Game game) {
        objects = game.getObjects();
        for (int i = 0; i < objects.size(); i++) {

            boolean playerIsDead = false;
            boolean enemyIsDead = false;

            if (objects.get(i) instanceof Enemy) {
                Enemy enemy = (Enemy) objects.get(i);
                if (enemy.getTileEnemyCoord().getxPos() == columnPos && enemy.getTileEnemyCoord().getyPos() == rowPos) {
                    enemyIsDead = true;
                } else if (enemy.getTileEnemyCoord().getxPos() == columnPos + 1 && enemy.getTileEnemyCoord().getyPos() == rowPos) {
                    enemyIsDead = true;
                } else if (enemy.getTileEnemyCoord().getxPos() == columnPos - 1 && enemy.getTileEnemyCoord().getyPos() == rowPos) {
                    enemyIsDead = true;
                } else if (enemy.getTileEnemyCoord().getxPos() == columnPos && enemy.getTileEnemyCoord().getyPos() == rowPos + 1) {
                    enemyIsDead = true;
                } else if (enemy.getTileEnemyCoord().getxPos() == columnPos && enemy.getTileEnemyCoord().getyPos() == rowPos - 1) {
                    enemyIsDead = true;
                }
                
                if (enemyIsDead) {
                    AnimatedSprite eDeath = game.getEnemyDeathAnimation();
                    eDeath.setAnimationRange(0, 5);
                    enemy.setAnimatedSprite(eDeath);
                    enemy.setIsDead(true);
                }
                
            }

            if (objects.get(i) instanceof Player) {
                Player player = (Player) objects.get(i);
                if (player.getTileCoord().getxPos() == columnPos && player.getTileCoord().getyPos() == rowPos) {
                    playerIsDead = true;
                } else if (player.getTileCoord().getxPos() == columnPos + 1 && player.getTileCoord().getyPos() == rowPos) {
                    playerIsDead = true;
                } else if (player.getTileCoord().getxPos() == columnPos - 1 && player.getTileCoord().getyPos() == rowPos) {
                    playerIsDead = true;
                } else if (player.getTileCoord().getxPos() == columnPos && player.getTileCoord().getyPos() == rowPos + 1) {
                    playerIsDead = true;
                } else if (player.getTileCoord().getxPos() == columnPos && player.getTileCoord().getyPos() == rowPos - 1) {
                    playerIsDead = true;
                }
                
                if (playerIsDead) {
                    AnimatedSprite pDeath = game.getPlayerDeathAnimation();
                    pDeath.setAnimationRange(0, 7);
                    player.setAnimatedSprite(pDeath);
                    player.setIsDead(true);
                }
            }


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

  
    
    
    
    
    
    
}
