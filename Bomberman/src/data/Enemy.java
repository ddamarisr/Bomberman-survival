/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;


import businessLogic.AStar;
import businessLogic.Game;
import businessLogic.Node;
import businessLogic.RenderHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;









/**
 *
 * @author juan david carrilllo
 */
public class Enemy implements GameObject{
    
    private String type ;
    private int score;
    private final Rectangle enemyRectangle;
    private final Sprite sprite;
    private AnimatedSprite  animatedSprite=null;
    private int direction=2;
    private int motionX;
    private int motionY;
    private final Coordinate tileEnemyCoord= new Coordinate(10,10);
    private double dis;
    private int a;
    private final int xPose;
    private final int yPose;
    private boolean isDead;
    private int deathCounter=0;
    private List<Node> path;
    private boolean isEnhanced=false;
    
    
    
    public Enemy(int xPose,int yPose,Sprite sprite){
        this.sprite=sprite;
        this.xPose=xPose;
        this.yPose=yPose;
        
        if (sprite!=null && sprite instanceof AnimatedSprite){
            animatedSprite=(AnimatedSprite) sprite;
        
    }
        updateDirection();
        enemyRectangle=new Rectangle(xPose,yPose,16,16);
        enemyRectangle.generateGraphics(1, 0xFF00FF90);
    
    
    }   
    
    
    
    
    private void updateDirection(){
        if (animatedSprite!=null){
            animatedSprite.setAnimationRange(direction, (direction)+2);
        }
    }
    

    public Coordinate getTileEnemyCoord() {
        return tileEnemyCoord;
    }
    
      
    public void move(Game game) {
        int newDirection = direction;
        Random random = new Random();
        a = random.nextInt(4);  
        if ((enemyRectangle.x % 48)== 0 && (enemyRectangle.y%48) == 0) {
            //"Soft" Movement could be enhances used linear interpolation
            animatedSprite.reset();
            motionX = 0;
            motionY = 0;
            if (a == 0 && game.collisionDetection(tileEnemyCoord, 0)) {
                motionX = -2;
                newDirection = 0;
            } else if (a == 1 && game.collisionDetection(tileEnemyCoord, 1)) {
                motionY = 2;
                newDirection = 1;
            } else if (a == 2 && game.collisionDetection(tileEnemyCoord, 2)) {

                motionX = 2;
                newDirection = 2;
            } else if (a == 3 && game.collisionDetection(tileEnemyCoord, 3)) {
                motionY = -2;
                newDirection = 3;
            }
        }
        enemyRectangle.y+=motionY;
        enemyRectangle.x+=motionX;
        
        if (newDirection!=direction){
            direction=newDirection;
            updateDirection();
        }
        
            animatedSprite.update(game);
       
        
    }

    public void setAnimatedSprite(AnimatedSprite animatedSprite) {
        this.animatedSprite = animatedSprite;
    }
        
        
    
    
    public String getType(){
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int puntaje) {
        this.score = puntaje;
    }
    
    public void enhancedMove(Game game) {
        generarEnemigo(enemyRectangle.y,enemyRectangle.x,game.getPlayer().getPlayerRectangle().y,game.getPlayer().getPlayerRectangle().x);
        int newDirection = direction;
        List<Node>path1 = path;
        
     
        
        try{
            int xe = path1.get(1).getCol();
            int ye = path1.get(1).getRow();
            int yene = (enemyRectangle.y)/48;
            int xene = (enemyRectangle.x)/48;
            if((yene)-(ye)==-1 && (xene)-(xe)==-1){
                a=3;
            
            }
            else if ((yene)-(ye)==-1 && (xene)-(xe)==1){
                try{
                    a=2;}catch(Exception e){
                        a=3;
                    }}
            else if ((yene)-(ye)==1 && (xene)-(xe)==-1){
                try{
                    a=3;}catch(Exception e){
                        a=0;
                    }}
            else if ((yene)-(ye)==-1 && (xene)-(xe)==0){
                a=1;}
            else if((yene)-(ye)==1 && (xene)-(xe)==1){
                try{
                    a=3;}catch(Exception e){
                        a=0;
                    }
            }
            else if ((yene)-(ye)==1 && (xene)-(xe)==0){
                a=3;
            
            }else if((yene)-(ye)==0 && (xene)-(xe)==-1){
                a=2;
            }
            else if((yene)-(ye)==1 && (xene)-(xe)==0){
                a=3;
            }else if((yene)-(ye)==0 && (xene)-(xe)==1){
                a=0;
            }
            if ((enemyRectangle.x % 48)== 0 && (enemyRectangle.y%48) == 0) {
                //"Soft" Movement could be enhances used linear interpolation
                animatedSprite.reset();
                motionX = 0;
                motionY = 0;
                if (a == 0 && game.collisionDetection(tileEnemyCoord, 0)) {
                    motionX = -3;
                    newDirection = 0;
                } else if (a == 1 && game.collisionDetection(tileEnemyCoord, 1)) {
                    motionY = 3;
                    newDirection = 1;
                } else if (a == 2 && game.collisionDetection(tileEnemyCoord, 2)) {
                    
                    motionX = 3;
                    newDirection = 2;
                } else if (a == 3 && game.collisionDetection(tileEnemyCoord, 3)) {
                    motionY = -3;
                    newDirection = 3;
                }
            }
            
            ArrayList<Coordinate> boardq = Game.getListaRompible();

enemyRectangle.y+=motionY;
enemyRectangle.x+=motionX;

if (newDirection!=direction){
    direction=newDirection;
    updateDirection();
}

animatedSprite.update(game);

        }catch(Exception e){
        }   }
    
    public List<Node> getPath() {
        return path;
    }
 
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }
    
    public void dissapear(Game game){
        animatedSprite.update(game);
        deathCounter=deathCounter+1;
        if(deathCounter==60){
            game.getObjects().remove(this);
            game.getEnemies().remove(this);
        }
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        if(animatedSprite!=null){
            renderer.renderSprite(animatedSprite,enemyRectangle.x , enemyRectangle.y, xZoom, yZoom);
        } else if(sprite!=null){
            renderer.renderSprite(sprite,enemyRectangle.x , enemyRectangle.y, xZoom, yZoom);
        } else {
            renderer.renderRectangle(enemyRectangle, xZoom, yZoom);
        }
      
    
    
    }

    @Override
    public void update(Game game) {
        updateTileCoord(enemyRectangle.x, enemyRectangle.y);
        if (isDead) {
            dissapear(game);
        } else if (isEnhanced) {
            enhancedMove(game);
        } else{
            move(game);
        }
    }

    public void updateTileCoord(int xPose, int yPose) {
        int xTilePose=(int) (Math.floorDiv(xPose, 16*RenderHandler.xZoom));
        int yTilePose=(int) (Math.floorDiv(yPose, 16*RenderHandler.yZoom));
        tileEnemyCoord.setxPos(xTilePose);
        tileEnemyCoord.setyPos(yTilePose);
    }

    public void setIsEnhanced(boolean isEnhanced) {
        this.isEnhanced = isEnhanced;
    }
    
    
    
    public void generarEnemigo(int direX, int direY,int dirX, int dirY){
        //System.out.println("Direeccion Enemigo ("+(direX/48)+","+(direY/48)+")");
        //System.out.println("Direecion Bomberman ("+(dirX/48)+","+(dirY/48)+")");
        ArrayList<Coordinate> board = Game.codie();
       // board = board+Game.get
        //System.out.println("Entro a generar");
        //System.out.println(board.size());
        //System.out.println("Board !!  "+board);
        //int x=board.get(0).getxPos();
        //int fila=board.get(0).getyPos();
        dirX = dirX/48;
        dirY = dirY/48;
        direX = direX/48;
        direY = direY/48;
        int [][]board2 = Enemy.convertIntegers(board);
         //int [][] blocks = convertIntegers(blocksArray);
        //System.out.println(board2[0][0]+" esto es borad "); 
        //System.out.println("("+direX+","+direY+")");
        Node initialNode = new Node(direX, direY);
        //System.out.println("("+dirX+","+dirY+")");
        Node finalNode = new Node(dirX,dirY);
        int rows = 13;
        int cols = 31;
        //System.out.println(initialNode);
        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
        //System.out.println("Pasa");
        //int[][] blocksArray = new int[][]{{1, 3}, {2, 3}, {3, 3}};
        
        
        aStar.setBlocks(board2);
        path = aStar.findPath();
    
    
    
     }
    public static int[][] convertIntegers(ArrayList<Coordinate> integers)
{
    //System.out.println(integers);
    int[][] ret = new int[integers.size()][2];
    for (int i=0; i < ret.length; i++)
    {
        
            
           
        ret[i][0] = integers.get(i).getxPos();
        ret[i][1] = integers.get(i).getyPos();
        
        //System.out.println(ret[i][1]);
        //System.out.println(ret[i][0]);
        }
    return ret;
}
    
}
