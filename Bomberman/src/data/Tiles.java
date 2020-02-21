/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import businessLogic.RenderHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author juan david carrilllo
 */
public class Tiles{
    
    private Block block;
    private String tag;
    private SpriteSheet spriteSheet;
    private ArrayList <Tile> tilesList=new ArrayList();
    private int xPos;
    private int yPos;
    private Player player;
    
   

    public Tiles(Block bloque) {
        this.block = bloque;
        this.tag=bloque.getTag();
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Tiles() {
        this.tag="0";
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    
    //This only works whenever sprites are loaded
    public Tiles (File tilesFile, SpriteSheet spriteSheet){
        this.spriteSheet=spriteSheet;
        
        Scanner scanner;
        try {
            scanner = new Scanner(tilesFile);
            while (scanner.hasNextLine()){
                String line=scanner.nextLine();
                if(!line.startsWith("//")){
                    String[] splitString=line.split("-");
                    String tileName=splitString[0];
                    int spriteX=Integer.parseInt(splitString[1]);
                    int spriteY=Integer.parseInt(splitString[2]);
                    Tile tile=new Tile(tileName,spriteSheet.getSprite(spriteX, spriteY));
                    tilesList.add(tile);
                }
            //Read each line
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       
        
        
        
    }
    
    public void renderTile(int tileID, RenderHandler renderer, int xPos, int yPos, int xZoom, int yZoom){
        if (tileID>=0&&tilesList.size()>tileID){
            renderer.renderSprite(tilesList.get(tileID).sprite, xPos, yPos, xZoom, yZoom);
         
        } else{
            System.out.println("TileID"+tileID+" is not within range "+tilesList.size()+".");
        }       
    }
    
    class Tile {
        public String tileName;
        public Sprite sprite;
        private int xPos;
        private int yPos;

        public Tile(String tileName, Sprite sprite) {
            this.tileName = tileName;
            this.sprite = sprite;
        }

        public Tile(int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
        }
        
        
        
        
        
    }
    
    
    
    
    
    
    
    }
   
    
    
