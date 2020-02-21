/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author juan david carrilllo
 */
public class Board {
    
    
    private Tiles [][] tiles ;

    public Tiles[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tiles[][] tiles) {
        this.tiles = tiles;
    }

    public Board(Tiles[][] tiles) {
        this.tiles = tiles;
    }

    @Override
    public String toString() {
        String printBoard = "\n";
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                printBoard = printBoard + tiles[i][j].getTag();
            }
            printBoard = printBoard + "\n";
        }
        return printBoard;

    }

}