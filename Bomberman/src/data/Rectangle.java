/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import businessLogic.Game;

/**
 *
 * @author danie
 */
public class Rectangle {
    public int x,y,w,h;
    private int [] pixels;

    public Rectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
   
    Rectangle(){
        this(0,0,0,0);
    }
    
    public void generateGraphics(int borderWidth,int color){
        
        pixels=new int [w*h];
        for (int i=0;i<pixels.length;i++){
            pixels[i]=Game.alpha;
        }
        for (int y=0;y<borderWidth;y++){
            for (int x=0;x<w;x++){
                pixels[x+y*w]=color;
        }
        }
        
        for (int y=0;y<h;y++){
            for (int x=0;x<borderWidth;x++){
                pixels[x+y*w]=color;
            }
        }
        
        for (int y=0;y<h;y++){
            for (int x=w-borderWidth;x<w;x++){
                pixels[x+y*w]=color;
            }
        }
        
        for (int y=h-borderWidth;y<h;y++){
            for (int x=0;x<w;x++){
                pixels[x+y*w]=color;
        }
        }
          
    }
    
    public int[]getPixels(){
        if(pixels!=null){
            return pixels;
        }
        
        else {
            System.out.println("Attempted to retrieve pixels from a Rectangle without generating graphics");
            return null;
        }
    }

    
    
}