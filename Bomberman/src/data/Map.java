/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import businessLogic.RenderHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author danie
 */
public class Map {
    private Tiles tileSet;
    private int fillTileID=-1;
    private File mapFile;
    private Board board;
    
    private ArrayList<MappedTile> mappedTiles=new ArrayList<MappedTile>();
    private HashMap <Integer,String> comments=new HashMap <Integer,String>();
    
    /*public Map(File mapFile, Tiles tileSet){
        int currentLine=0;
        this.mapFile=mapFile;
        this.tileSet=tileSet;
        Scanner scanner;
        try {
            scanner = new Scanner(mapFile);
            while (scanner.hasNextLine()){
                String line=scanner.nextLine();
                if(!line.startsWith("//")){ 
                    if(line.contains(":")){
                        String[] splitString =line.split(":");
                        if (splitString[0].equalsIgnoreCase("Fill")){
                            fillTileID=Integer.parseInt(splitString[1]);
                            continue;
                        }
                    }
                    String[] splitString=line.split(",");
                    if(splitString.length>=3){
                        MappedTile mappedTile=new MappedTile(Integer.parseInt(splitString[0]),Integer.parseInt(splitString[1]),Integer.parseInt(splitString[2]));
                        mappedTiles.add(mappedTile);
                        
                    }
                    
                } else {
                    comments.put(currentLine,line);    
                }
            //Read each line
            currentLine++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }*/
 
    
    
    
    public Map(Board board, Tiles tileSet,int fillTileID){
        this.tileSet=tileSet;
        this.board=board;
        Tiles taggedMap [][]=board.getTiles();
        for (int i = 0; i < taggedMap.length; i++) {
            for (int j = 0; j < taggedMap[i].length; j++) {
                MappedTile mappedTile=new MappedTile(Integer.parseInt(taggedMap[i][j].getTag()),j,i);
                mappedTiles.add(mappedTile);
            }
        } 
    }
    
    /*public void saveMap(){
        try {
            
        int currentLine=0;
        if(mapFile.exists()){
           mapFile.delete(); 
        }
        mapFile.createNewFile();
        //mapFile.createNewFile();
        
        PrintWriter printWriter=new PrintWriter(mapFile);
       
        
        if(fillTileID>=0){
            if(comments.containsKey(currentLine)){
                
                printWriter.println(comments.get(currentLine));
                currentLine++;
        }
            printWriter.println("Fill:"+fillTileID);
        }
                 
        for (int i=0;i<mappedTiles.size();i++){
            if(comments.containsKey(currentLine)){
                printWriter.println(comments.get(currentLine));
            }
            
            MappedTile tile=mappedTiles.get(i);
            printWriter.println(tile.id+","+ tile.x+","+tile.y);
            currentLine++;
        }
            
        printWriter.close();    
             
        }
        catch (IOException e){
        e.printStackTrace();
        }
    }*/
  
    public void render (RenderHandler renderer,int xZoom,int yZoom){
        int xIncrement=16*xZoom;
        int yIncrement=16*yZoom;
        
        if(fillTileID>=0){
            
            Rectangle camera=renderer.getCamera();
         
            for (int y=camera.y -xIncrement-(camera.y%yIncrement);y<camera.y+camera.h;y+=yIncrement){
                for (int x=camera.x-yIncrement-(camera.x%xIncrement);x<camera.x+camera.w;x+=xIncrement){
                    
                    tileSet.renderTile(fillTileID, renderer, x, y, xZoom, yZoom);
                      
                }
            }
        }
        
        for (int tileIndex=0;tileIndex<mappedTiles.size();tileIndex++){
            MappedTile mappedTile=mappedTiles.get(tileIndex);
            tileSet.renderTile(mappedTile.id,renderer,mappedTile.x*xIncrement,mappedTile.y*yIncrement,xZoom,yZoom);
       
        }
        
    }

    public ArrayList<MappedTile> getMappedTiles() {
        return mappedTiles;
    }
    
    
    
    /*public void setTile(int tileX,int tileY,int tileID){
        
        boolean foundTile=false;
        
        for (int i=0;i<mappedTiles.size();i++){
            MappedTile mappedTile=mappedTiles.get(i);
            
            if(mappedTile.x==tileX && mappedTile.y==tileY){
                mappedTile.id=tileID;
                foundTile=true;
                break;
                 
            }

        }
        if(!foundTile){
            mappedTiles.add(new MappedTile(tileID,tileX,tileY));
        }
    }*/

    
    public void removeTile(int tileX, int tileY) {
        for (int i=0;i<mappedTiles.size();i++){
            MappedTile mappedTile=mappedTiles.get(i);
            if(mappedTile.x==tileX && mappedTile.y==tileY){
                mappedTiles.remove(i);
            }
 
    }
    }
    
    public void updateTile(int index){
        MappedTile mappedTile= mappedTiles.get(index);
        mappedTile.id=0;
    }
    
    
    
    class MappedTile{
        
        public int id,x,y;
        
        public MappedTile (int id, int x,int y){
            this.id=id;
            this.x=x;
            this.y=y;
        }

        @Override
        public String toString() {
            return "MappedTile{" + "id=" + id + ", x=" + x + ", y=" + y + '}';
        }
        
    }
    
}