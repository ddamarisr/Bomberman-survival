/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import businessLogic.Game;
import businessLogic.KeyBoardListener;
import businessLogic.RenderHandler;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import javax.swing.JLabel;
import userInterface.Menu;
import userInterface.TablaPuntajes;

/**
 *
 * @author juan david carrilllo
 */
public class Player implements GameObject {
    
    private Rectangle playerRectangle;
    private int lifes;
    private int score;
    private String name;
    private Rectangle camera;
    private Sprite sprite;
    private AnimatedSprite  animatedSprite=null;
    private Sprite spriteBomb;
    private int direction=2;
    private int motionX;
    private int motionY;
    private Coordinate tileCoord=new Coordinate(1,1);
    private final int waitInSeconds=3;
    private int counter=waitInSeconds*60;
    private Bomb bomb;
    private Coordinate bombPos;
    private int expCounter;
    private Explosion explosion;
    private final double explosionTime=0.6;
    private boolean exploding;
    private boolean bombPlaced=false;
    private boolean isDead;
    private int deathCounter=0;
    private ArrayList<Enemy> enemiesList;
    private Enemy Enemy;
    public static int survivedTime;
    public JLabel gameo;
    
    
    //1=LEFT, 2=DOWN,3=RIGHT,4=UP   
    
    public Player(int xPos,int yPos,Sprite sprite,Sprite bomb, boolean isDead){
        this.sprite=sprite;
        this.spriteBomb=bomb;
        this.isDead=isDead;
        
        if (sprite!=null && sprite instanceof AnimatedSprite){
            animatedSprite=(AnimatedSprite) sprite;
            
    }
        
        updateDirection();
        playerRectangle=new Rectangle(xPos,yPos,16,16);
        playerRectangle.generateGraphics(1, 0xFF00FF90);
    }
    
    
    
    
    private void updateDirection(){
        if (animatedSprite!=null){
            animatedSprite.setAnimationRange(direction*3, (direction*3)+2);
        }
    }
    
    
    
    public void placeBomb(Game game, int waitSeconds, double explosionTime) throws InterruptedException { //ERASE BOMBS
        KeyBoardListener keyListener = game.getKeyListener();
        if (counter >= (waitSeconds * 60) && bombPlaced) {
            game.ExplosionHandler(bombPos,game.getExpAnimations());
            expCounter=0;
            game.getObjects().remove(bomb);
            bombPlaced=false;
            bomb.scanBomb(game);
            exploding=true;
        } else if (expCounter >= (explosionTime * 60) && exploding){
            for (int i=0;i<game.getObjects().size();i++) {
                if (game.getObjects().get(i) instanceof Explosion ||game.getObjects().get(i) instanceof Block){
                    game.getObjects().remove(i);
                    i=0;
            }
            }
            exploding=false;
        } else if (keyListener.action() && (counter >= (waitSeconds * 60))) {
            bombPos= new Coordinate(game.getRenderer().xZoom*16*tileCoord.getxPos(),game.getRenderer().yZoom*16*tileCoord.getyPos());
            Bomb bomba = new Bomb(bombPos,  spriteBomb);
            game.getObjects().add(bomba);
            this.bomb = bomba;
            bombPlaced=true;
            counter = 0;
        } 
        counter++;
        expCounter++;
    }
    
        public void detectEnemy(Game game) {
        enemiesList = game.getEnemies();
        for (int i = 0; i < enemiesList.size(); i++) {
            Enemy = enemiesList.get(i);
            if (tileCoord.getxPos() == Enemy.getTileEnemyCoord().getxPos() && tileCoord.getyPos() == Enemy.getTileEnemyCoord().getyPos()) {
                AnimatedSprite pDeath = game.getPlayerDeathAnimation();
                pDeath.setAnimationRange(0, 7);
                animatedSprite=pDeath;
                isDead=true;
            }
        }
    }
    
    public void dissapear(Game game) throws IOException{
        game.getText().setText(null);
        game.getText().setBounds(70,200,800,200);
        game.getText().setOpaque(false);
        game.getText().setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/gameOver/gameOver.png")));
        animatedSprite.update(game);
        deathCounter=deathCounter+1;
        if(deathCounter==60){
            game.getObjects().remove(this);
          
            for (int i=0;i<game.getObjects().size();i++){
                if (game.getObjects().get(i) instanceof Enemy){
                    Enemy enemy=(Enemy)game.getObjects().get(i);
                    enemy.setIsEnhanced(false);
                }
            }
            this.survivedTime=game.getCrono().getSegundos();
           gameOVER(game);
            System.out.println("Por favor ingrese un nombre para guardar su puntaje: ");
            Scanner reader=new Scanner (System.in);
            String playerName=reader.nextLine();
            game.setEndGame(true);
        }
    }
    
    public void gameOVER(Game game){
        
        
        
        
       TablaPuntajes tabla = null ;
        try {
            tabla = new TablaPuntajes();
           tabla.setGame(game);
           tabla.setLocationRelativeTo(null);
           
           
           
           
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabla.setVisible(true);
        
    }
    
    public void move(Game game){
        int newDirection=direction;
        boolean didMove=false;
        if (playerRectangle.x % 48 == 0 && playerRectangle.y % 48 == 0) { //"Soft" Movement could be enhanced used linear interpolation
            animatedSprite.reset();
            KeyBoardListener keyListener = game.getKeyListener();
            motionX = 0;
            motionY = 0;
            if (keyListener.left()&&game.collisionDetection(tileCoord, 0)){
                motionX=-3;
                newDirection=0;
                didMove=true;
            } else if (keyListener.right()&&game.collisionDetection(tileCoord, 2)) {
                motionX=3;
                newDirection=2;
                didMove=true;
            } else if (keyListener.up()&&game.collisionDetection(tileCoord, 3)) {
                didMove=true;
                motionY=-3;
                newDirection=3;
            } else if (keyListener.down()&&game.collisionDetection(tileCoord, 1)) {
                didMove=true;
                motionY=3;
                newDirection=1;
            }
        }
        playerRectangle.y+=motionY;
        playerRectangle.x+=motionX;
        
        if (newDirection!=direction){
            direction=newDirection;
            updateDirection();
        }
        
            animatedSprite.update(game);
       
        updateCamera(game.getRenderer().getCamera());
        
    }

    public Rectangle getPlayerRectangle() {
        return playerRectangle;
    }
    
    
    
    public void updateTileCoord(int xPos,int yPos){
        int xTilePos=(int) (Math.floorDiv(xPos, 16*RenderHandler.xZoom));
        int yTilePos=(int) (Math.floorDiv(yPos, 16*RenderHandler.yZoom));
        tileCoord.setxPos(xTilePos);
        tileCoord.setyPos(yTilePos);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        //renderer.renderRectangle(playerRectangle, xZoom, yZoom);
        if(animatedSprite!=null){
            renderer.renderSprite(animatedSprite,playerRectangle.x , playerRectangle.y, xZoom, yZoom);
        } else if(sprite!=null){
            renderer.renderSprite(sprite,playerRectangle.x , playerRectangle.y, xZoom, yZoom);
        } else {
            renderer.renderRectangle(playerRectangle, xZoom, yZoom);
        }
      
    }
    

    @Override
    public void update(Game game) {
        if (isDead) {
            try {
                dissapear(game);
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            detectEnemy(game);
            move(game);
            updateTileCoord(playerRectangle.x, playerRectangle.y);
        }
        try {
            placeBomb(game, waitInSeconds, explosionTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateCamera(Rectangle camera){
        if (playerRectangle.x>=480&&playerRectangle.x<=1008){
            camera.x=playerRectangle.x-(camera.w/2);
    }

    }
    public Coordinate getBombPos() {
        return bombPos;
    }

    public Coordinate getTileCoord() {
        return tileCoord;
    }

    public void setAnimatedSprite(AnimatedSprite animatedSprite) {
        this.animatedSprite = animatedSprite;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }
    
    
    
    
        
        
    }