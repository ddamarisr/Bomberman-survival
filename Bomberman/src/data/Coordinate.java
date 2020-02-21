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
public class Coordinate {
    private int xPos;
    private int yPos;

    public Coordinate(int filas, int columnas) {
        this.yPos = filas;
        this.xPos = columnas;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    
    

    @Override
    public String toString() {
        return "Coordenada{" + "filas=" + yPos + ", columnas=" + xPos + '}';
    }
    
    
}
