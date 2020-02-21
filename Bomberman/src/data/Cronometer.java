/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Tani
 */
public class Cronometer {
    
    private Timer timer = new Timer(); 
    private String format;

    int seconds =0;

    public Timer getTimer() {
        return timer;
    }

    public int getSegundos() {
        return seconds;
    }

    public TimerTask getTask() {
        return task;
    }

    public String getFormat() {
        return format;
    }
    
    
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            seconds++;
            format= String.format("%02d:%02d", seconds / 60, seconds % 60);
            
        }
    };
        
    
    public void start(){
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }
 //public static void main(String[] args){
   // Cronometro1 cronometro = new Cronometro1();
    //cronometro.start();
    //}
    
    }
    

