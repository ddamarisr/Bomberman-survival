/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

/**
 *
 * @author danie
 */
import data.AnimatedSprite;
import data.Block;
import data.Board;
import data.Coordinate;
import data.Cronometer;
import data.Enemy;
import data.Explosion;
import data.GameObject;
import data.Map;
import data.Player;
import data.Rectangle;
import data.Score;
import data.Sprite;
import data.SpriteSheet;
import data.Tiles;
import data.WriteRead;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Canvas;
import java.awt.Font;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.JLabel;



public class Game extends JFrame implements Runnable{
    private static ArrayList <Coordinate> listaRompible;
    private static int boardWidth;
    private static int boardHeight;
    private final Canvas canvas = new Canvas();
    private final RenderHandler renderer;
    private BufferedImage testImage;
    private final Rectangle testRectangle=new Rectangle(30,30,100,100);
    public static int alpha=0xFFFF00DC;
    private Sprite testSprite; 
    private final SpriteSheet sheet;
    private final Tiles tiles;
    private final Map map;
    private ArrayList<GameObject> objects;
    private final Player player; 
    private final KeyBoardListener keyListener=new KeyBoardListener(this);
    private static Board board;
    private final SpriteSheet playerSheet;
    private int windowWidth;
    private int windowHeight;
    private final SpriteSheet bombSheet;
    private final SpriteSheet expCenterSheet;
    private Explosion explosion;
    private final AnimatedSprite [] expAnimations;
    private final SpriteSheet expUpSheet;
    private final SpriteSheet expDownSheet;
    private final SpriteSheet expRightSheet;
    private final SpriteSheet expLeftSheet;
    private final ArrayList <Enemy> enemies;
    private final int enemiesAmount=8;
    private final SpriteSheet enemySheet;
    private final AnimatedSprite enemyAnimation;
    private final SpriteSheet blockSheet;
    private final SpriteSheet pDeathSheet;
    private final AnimatedSprite playerDeathAnimation;
    private ArrayList <Coordinate> entitiesLoc;
    private final SpriteSheet eDeathSheet;
    private final AnimatedSprite enemyDeathAnimation;
    private JLabel time;
    private final Cronometer crono;
    private final JLabel timer;
    private final JLabel text;
    private static Tiles[][] tablero;
    private static ArrayList <Coordinate> breakableBlocks;
    private boolean lastStage=false;
    private final int firstStageTime=25;
    public static TreeMap <Integer,Score> scores=new TreeMap();
    private boolean endGame=false;
    public JFrame vida=new JFrame();

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getVida() {
        return vida;
    }

    public void setVida(JFrame vida) {
        this.vida = vida;
    }
    
    


    public void setListaRompible(ArrayList<Coordinate> listaRompible) {
        Game.listaRompible = listaRompible;
    }

    public static ArrayList<Coordinate> getListaRompible() {
        return listaRompible;
    }
   
    public void generarEnemigo(){
        
    }
    
    public static void generarBloquesRigidos(Tiles[][]tablero){
        for (int i = 0; i < 13; i++) {
            if (i == 0 || i == 12) { // Crear los bloques rigidos de arriba y de abajo
                for (int j = 0; j < 31; j++) {
                    Block bloque = new Block(false);
                    Tiles hola = new Tiles(bloque);
                    tablero[i][j] = hola;
                }
            } else if (i % 2 == 0) {
                for (int j = 0; j < 31; j = j + 2) { // Crear los bloques rígidos interiores.
                    Block bloque = new Block(false);
                    Tiles hola = new Tiles(bloque);
                    tablero[i][j] = hola;
                } 
            } else {
                Block bloque = new Block(false);
                Tiles hola = new Tiles(bloque);
                tablero[i][0] = hola;
                tablero [i][30]=hola;
            } 
        }   
    }
    
    public static ArrayList <Coordinate> generarBloquesRompibles(Tiles[][]tablero){ 
        breakableBlocks= new ArrayList (); //Luego usaremos las coordenadas de los bloques rompibles
        Random aleatorio=new Random();
        int cantidad=40; //Generar 40 bloques rompibles 
        int rangoi=(((tablero.length-2)-1)+1);
        int rangoj=(((tablero[0].length-2)-1)+1);
        int i=0;
        while (i<cantidad){
            int randomi=aleatorio.nextInt(rangoi)+1; //Rangos al interior de la matriz
            int randomj=aleatorio.nextInt(rangoj)+1;
            
            if(!((randomi==1&&randomj==1)||(randomi==2&&randomj==1)||(randomi==1&&randomj==2)||(randomi==2&&randomj==2))){//So the player cant get enclosed when spawns
                if (tablero[randomi][randomj]==null){ 
                Block bloque = new Block(true);
                Tiles hola = new Tiles(bloque);
                tablero[randomi][randomj]=hola;
                Coordinate coordenada=new Coordinate(randomi,randomj);
                
                breakableBlocks.add(coordenada);
                i++;
            }
                
            }
            }
        return breakableBlocks;
        }   
    
    public static void generarVacio (Tiles[][]tablero){
        Tiles vacia=new Tiles();
        tablero [1][1]=vacia;
        for (int i=0;i<tablero.length;i++){
            for (int j=0;j<tablero[i].length;j++){
            if (tablero[i][j]==null){  //Llenar todo lo que queda con casillas vacías
                tablero [i][j]=vacia;
            }
        }
        }
    }
    
    public static Board generarEscenario() {
        boardWidth=13;
        boardHeight=31;
        Tiles[][] tablero = new Tiles[boardWidth][boardHeight]; //El escenario es de tamaño 13x31
        generarBloquesRigidos(tablero);
        breakableBlocks=generarBloquesRompibles(tablero);
        generarVacio(tablero);
        Board defo = new Board(tablero);
        return defo;
    }
    
    public void  ExplosionHandler (Coordinate coordinate,Sprite[] explosionSprite){
        Tiles[][] tablero=board.getTiles();
        int columns=(int) (Math.floorDiv(coordinate.getyPos(), 16*RenderHandler.yZoom));
        int rows=(int) (Math.floorDiv(coordinate.getxPos(), 16*RenderHandler.xZoom));
        Explosion center=new Explosion(coordinate.getyPos(),coordinate.getxPos(),explosionSprite[0]);
        objects.add(center);
        this.explosion=center;
        if (tablero[rows][columns-1].getTag()=="0"){
            Explosion left=new Explosion (coordinate.getyPos()-16*RenderHandler.xZoom,coordinate.getxPos(),explosionSprite[3]);
            objects.add(left);
        }
        if (tablero[rows+1][columns].getTag()=="0"){
            Explosion down=new Explosion (coordinate.getyPos(),coordinate.getxPos()+16*RenderHandler.yZoom,explosionSprite[2]);
            objects.add(down);
        }
        if (tablero[rows][columns+1].getTag()=="0"){
            Explosion right=new Explosion (coordinate.getyPos()+16*RenderHandler.xZoom,coordinate.getxPos(),explosionSprite[4]);
            objects.add(right);
        }
        if (tablero[rows-1][columns].getTag()=="0"){
            Explosion up=new Explosion (coordinate.getyPos(),coordinate.getxPos()-16*RenderHandler.yZoom,explosionSprite[1]);
            objects.add(up);
        }
       updateBoard(coordinate);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public TreeMap<Integer, Score> getScores() {
        return scores;
    }
    
    public void updateBoard(Coordinate coordinate) {
        Tiles[][] tablero = board.getTiles();
        int columns = (int) (Math.floorDiv(coordinate.getyPos(), 16 * RenderHandler.yZoom));
        int rows = (int) (Math.floorDiv(coordinate.getxPos(), 16 * RenderHandler.xZoom));
        if (tablero[rows][columns - 1].getTag() == "2") {
            tablero[rows][columns - 1].setTag("0");
            map.updateTile((rows*tablero[0].length)+(columns-1));
            AnimatedSprite blockSprite=new AnimatedSprite(blockSheet,10);
            Block bye=new Block (coordinate.getyPos()-16*RenderHandler.xZoom,coordinate.getxPos(),blockSprite);
            objects.add(bye);
            breakableBlocks.remove(new Coordinate (rows,columns-1));
        }
        
        if (tablero[rows+1][columns].getTag() == "2") {
            tablero[rows+1][columns].setTag("0");
            map.updateTile(((rows+1)*tablero[0].length)+(columns));
            AnimatedSprite blockSprite=new AnimatedSprite(blockSheet,10);
            Block bye =new Block (coordinate.getyPos(),coordinate.getxPos()+16*RenderHandler.yZoom,blockSprite);
            objects.add(bye);
            breakableBlocks.remove(new Coordinate (rows+1,columns));
        }
        
        if (tablero[rows][columns + 1].getTag() == "2") {
            tablero[rows][columns + 1].setTag("0");
            map.updateTile((rows*tablero[0].length)+(columns+1));
            AnimatedSprite blockSprite=new AnimatedSprite(blockSheet,10);
            Block bye=new Block (coordinate.getyPos()+16*RenderHandler.xZoom,coordinate.getxPos(),blockSprite);
            objects.add(bye);
            breakableBlocks.remove(new Coordinate (rows,columns+1));
        }
        
        if (tablero[rows-1][columns].getTag() == "2") {
            tablero[rows-1][columns].setTag("0");
            map.updateTile(((rows-1)*tablero[0].length)+(columns));
            AnimatedSprite blockSprite=new AnimatedSprite(blockSheet,10);
            Block bye=new Block (coordinate.getyPos(),coordinate.getxPos()-16*RenderHandler.yZoom,blockSprite);
            objects.add(bye);
            breakableBlocks.remove(new Coordinate (rows-1,columns));
        }
    }
    
    public void updateBoard(ArrayList<Coordinate> breakableBlocks) {
        Tiles[][] tablero = board.getTiles();
        int r=0;
        for (int i = 0; i < breakableBlocks.size(); i++) {
            int columns = breakableBlocks.get(i).getxPos();
            int rows = breakableBlocks.get(i).getyPos();
            tablero[rows][columns].setTag("0");
            map.updateTile((rows * tablero[0].length) + (columns));
            AnimatedSprite blockSprite = new AnimatedSprite(blockSheet, 10);
            Block bye = new Block(columns*16*RenderHandler.xZoom,rows*16*RenderHandler.yZoom, blockSprite);
            objects.add(bye);
            r++;
    }
        System.out.println(r);
        lastStage=true;
        breakableBlocks.clear();
    }
    
    
    
    public boolean collisionDetection(Coordinate tilePos,int direction){
        Tiles[][] tablero=board.getTiles();
        int xTilePos=tilePos.getxPos();
        int yTilePos=tilePos.getyPos();
        
        boolean possibleMove=false;
        switch (direction) {
            case 0:
                if (tablero[yTilePos][xTilePos-1].getTag()=="0"){
                    possibleMove=true;  
                }
                break;
            case 1:
                if (tablero[yTilePos+1][xTilePos].getTag()=="0"){
                    possibleMove=true;  
                }
                break;
            case 2:
                if (tablero[yTilePos][xTilePos+1].getTag()=="0"){
                    possibleMove=true;  
                }
                break;
            case 3:
                if (tablero[yTilePos-1][xTilePos].getTag()=="0"){
                    possibleMove=true;  
                }
                break;
            default:
                break;
        }
        
        return possibleMove;
    }
    
  
    
    

    public Game() throws IOException, FileNotFoundException, ClassNotFoundException {
        
        
        Game.board = generarEscenario();
        
        
        
        scores=WriteRead.reader();
        
        // JFrame vida=new JFrame();
        timer=new JLabel();
        timer.setFont(new Font("Serif", Font.BOLD, 30));
        timer.setForeground(Color.YELLOW);
        
        text=new JLabel();
        text.setFont(new Font("Serif", Font.BOLD, 25));
        text.setForeground(Color.YELLOW);
        text.setText("OBJETIVO ACTUAL: ATACAR");
        crono = new Cronometer();
        crono.start();
        
        vida.getContentPane().setBackground(Color.black);
        vida.setBounds(0,0,960,800);
        vida.setTitle("BOMBERMAN");
        timer.setBounds(445, 620, 150, 50);
        text.setBounds(275,650,600,50);
        vida.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // shutdown
        vida.setLocationRelativeTo(null);
        vida.add(timer);
        vida.add(text);
        vida.add(canvas);
        vida.setVisible(true);
        canvas.createBufferStrategy(3);
        renderer=new RenderHandler(960,620);
        testRectangle.generateGraphics(2,1234);
        
        //Loading assets
        
        BufferedImage sheetImage=loadImage("assets/7884.png");
        sheet=new SpriteSheet(sheetImage);
        sheet.loadSprites(16, 16);
        
        BufferedImage playerSheetImage=loadImage("assets/PlayerMov.png");
        playerSheet=new SpriteSheet(playerSheetImage);
        playerSheet.loadSprites(16, 16);
        
        BufferedImage bombSheetImage=loadImage("assets/bomb.png");
        bombSheet=new SpriteSheet(bombSheetImage);
        bombSheet.loadSprites(16, 16);
        
        BufferedImage explotionCenterSheetImage=loadImage("assets/center.png");
        expCenterSheet=new SpriteSheet(explotionCenterSheetImage);
        expCenterSheet.loadSprites(16, 16);
        
        BufferedImage explotionUpSheetImage=loadImage("assets/up.png");
        expUpSheet=new SpriteSheet(explotionUpSheetImage);
        expUpSheet.loadSprites(16, 16);
        
        BufferedImage explotionDownSheetImage=loadImage("assets/down.png");
        expDownSheet=new SpriteSheet(explotionDownSheetImage);
        expDownSheet.loadSprites(16, 16);
        
        BufferedImage explotionRightSheetImage=loadImage("assets/right.png");
        expRightSheet=new SpriteSheet(explotionRightSheetImage);
        expRightSheet.loadSprites(16, 16);
        
        BufferedImage explotionLeftSheetImage=loadImage("assets/left.png");
        expLeftSheet=new SpriteSheet(explotionLeftSheetImage);
        expLeftSheet.loadSprites(16, 16);
        
        BufferedImage enemySheetImage=loadImage("assets/Enemy.png");
        enemySheet = new SpriteSheet(enemySheetImage);
        enemySheet.loadSprites(16, 16);
        
        BufferedImage blockSheetImage=loadImage("assets/destruction.png");
        blockSheet = new SpriteSheet(blockSheetImage);
        blockSheet.loadSprites(16, 16);
        
        BufferedImage playerDeathSheetImage=loadImage("assets/playerDeath.png");
        pDeathSheet = new SpriteSheet(playerDeathSheetImage);
        pDeathSheet.loadSprites(16, 16);
        
        BufferedImage EnemyDeathSheetImage=loadImage("assets/EnemyDeath.png");
        eDeathSheet = new SpriteSheet(EnemyDeathSheetImage);
        eDeathSheet.loadSprites(16, 16);
       
        //Load tiles
        tiles=new Tiles(new File("Tiles.txt"),sheet);
        
        //Load Map
        map=new Map(board,tiles,0);
        
        
        //Load objects
        objects=new ArrayList<>();
        enemies=new ArrayList<>();
        expAnimations=new AnimatedSprite[5];
        int yInPos=16*RenderHandler.yZoom; //where to generate player pixels if Tile is [1][1] upper left corner
        int xInPos=16*RenderHandler.xZoom;
        AnimatedSprite playerAnimation=new AnimatedSprite(playerSheet,5);
        AnimatedSprite bombAnimation=new AnimatedSprite(bombSheet,20);
        expAnimations[0]=new AnimatedSprite(expCenterSheet,10);
        expAnimations[1]=new AnimatedSprite(expUpSheet,10);
        expAnimations[2]=new AnimatedSprite(expDownSheet,10);
        expAnimations[3]=new AnimatedSprite(expLeftSheet,10);
        expAnimations[4]=new AnimatedSprite(expRightSheet,10);
        
        enemyAnimation=new AnimatedSprite(enemySheet,5);
        
        playerDeathAnimation=new AnimatedSprite(pDeathSheet,10);
        
        enemyDeathAnimation=new AnimatedSprite(eDeathSheet,15);
        
        
        
        player = new Player(xInPos, yInPos, playerAnimation, bombAnimation, false);

        objects.add(player);

        for (int i = 0; i < enemiesAmount; i++) {
            Enemy ghost = new Enemy(xInPos+96*(i+2), yInPos+48*(i+2), enemyAnimation);
            objects.add(ghost);
            enemies.add(ghost);
        }

        
        //Add Listeners
        canvas.addKeyListener(keyListener);
        canvas.addFocusListener(keyListener);
       
    }
    
    public Player getPlayer() {
        return player;
    }

    public Cronometer getCrono() {
        return crono;
    }
    
    public static Tiles[][] getTablero() {
        return tablero;
    }
    
    public KeyBoardListener getKeyListener(){
        return keyListener;
    }
    
    public void render() {
        
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);
        map.render(renderer, RenderHandler.xZoom, RenderHandler.yZoom);
        
        for (int i=0;i<objects.size();i++){
             objects.get(i).render(renderer,RenderHandler.xZoom,RenderHandler.yZoom);
         } 
           
        renderer.render(graphics);
        graphics.dispose();
        bufferStrategy.show();
        renderer.clear(); 

    }

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<GameObject> objects) {
        this.objects = objects;
    }
    
    public void clear (){
        for (int i=0;i<objects.size();i++){
            if (objects.get(i)instanceof Block){
                objects.remove(i);
            }
        }
    }
    

    public RenderHandler getRenderer() {
        return renderer;
    }

    public static ArrayList codie() {
        ArrayList corden = new ArrayList();
        Tiles[][] prueba = board.getTiles();
        Coordinate coo = null;
        Coordinate coor = null;

        for (int i = 0; i < prueba.length; i++) {
            for (int j = 0; j < prueba[i].length; j++) {
                coo = new Coordinate(i, j);

                if (prueba[i][j].getTag() == "1" || prueba[i][j].getTag() == "2") {
                    corden.add(coo);
                }

            }

        }
    return corden;
    }

    public Explosion getExplosion() {
        return explosion;
    }

    public void setExplosion(Explosion explosion) {
        this.explosion = explosion;
    }
    
    public AnimatedSprite[] getExpAnimations() {
        return expAnimations;
    }

    public AnimatedSprite getPlayerDeathAnimation() {
        return playerDeathAnimation;
    }

    public AnimatedSprite getEnemyDeathAnimation() {
        return enemyDeathAnimation;
    }

    public JLabel getText() {
        return text;
    }
    
    

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }
    
    public void update(){
        
        if(crono.getSegundos()==firstStageTime){
            updateBoard(breakableBlocks);
            text.setBounds(325,650,600,50);
            text.setText("¡¡¡CORRE POR TU VIDA!!!");
            
            for (int i=0;i<enemies.size();i++){
                enemies.get(i).setIsEnhanced(true);
            }
        }
        
        if (crono.getSegundos()==firstStageTime+1){
            clear();
        }
         for (int i=0;i<objects.size();i++){
             objects.get(i).update(this);
         }
        timer.setText(crono.getFormat());
    }
    
    private BufferedImage loadImage(String path) throws IOException{
        BufferedImage loadedImage=ImageIO.read(ClassLoader.getSystemClassLoader().getResource(path));
        BufferedImage formattedImage= new BufferedImage(loadedImage.getWidth(),loadedImage.getHeight(),BufferedImage.TYPE_INT_RGB);
        formattedImage.getGraphics().drawImage(loadedImage,0,0,null);
        return formattedImage;
    }

    @Override
    public void run() { //Thread Inizialization
        BufferStrategy bufferStrategy=canvas.getBufferStrategy();
        int i=0;
        int x=0;
        long lastTime=System.nanoTime();
        double nanoSecondConversion=1.0E9/60; //60 frames per second fixed time-step update
        double changeInSeconds=0;
        
        while (!endGame){
            long now=System.nanoTime();
            
            changeInSeconds+=(now-lastTime)/nanoSecondConversion;
            
            while(changeInSeconds>=1){
                update(); //this method is called every 60 seconds
                changeInSeconds--;
            }
            render();
            lastTime=now;
        }
       
    }

}