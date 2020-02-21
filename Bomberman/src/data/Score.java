/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;

/**
 *
 * @author danie
 */
public class Score implements Serializable {
    
    private String playerName;
    private String survivedTime;
    
    private static final long serialVersionUID = 1L;

    public Score(String playerName, String survivedTime) { 
        this.playerName = playerName;
        this.survivedTime = survivedTime;
    }   
     public int getSurvivedTime() {
        return Integer.parseInt(survivedTime);
    }

    public void setSurvivedTime(String survivedTime) {
        this.survivedTime = survivedTime;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    

    @Override
    public String toString() {
        String format= String.format("%02d:%02d", Integer.parseInt(survivedTime) / 60, Integer.parseInt(survivedTime) % 60);
        return "Score{" + "playerName=" + playerName + ", survivedTime=" + format + '}';
    }
    
    
    
    
    
    
    
}
