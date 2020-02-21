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
public interface GameObject {
    public void render(RenderHandler renderer,int xZoom,int yZoom); //everytime it's possible
    public void update(Game game);//60 times per second
    

}
