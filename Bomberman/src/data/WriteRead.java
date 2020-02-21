/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

/**
 *
 * @author danie
 */
public class WriteRead {

    public static void writer(TreeMap<Integer, Score> scores) throws FileNotFoundException, IOException {
        FileOutputStream f = new FileOutputStream(new File("Scores.txt")); //archivo
        ObjectOutputStream o = new ObjectOutputStream(f); //objeto
        for (Score score : scores.values()) {
            try {
                // Write objects to file
                o.writeObject(score);
            

            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
                System.out.println("Error initializing stream");
            }

        }
        o.close();
    }
    
    public static TreeMap <Integer, Score> reader() throws FileNotFoundException,
            IOException, ClassNotFoundException {

        TreeMap <Integer, Score> scores = new TreeMap<>(Collections.reverseOrder());

        // The following call can throw a FileNotFoundException or an IOException.9
        // Since this is probably better dealt with in the calling function, 
        // reader is made to throw these exceptions instead.
        FileInputStream fi = new FileInputStream(new File("Scores.txt")); //archivo
        
        ObjectInputStream oi = new ObjectInputStream(fi);//objeto

        while (true) {
            try {
                // Read the next object from the stream. If there is none, the
                // EOFException will be thrown.
                // This call might also throw a ClassNotFoundException, which can be passed
                // up or handled here.
                Score score = (Score) oi.readObject();
                scores.put(score.getSurvivedTime(), score);
                
            } catch (EOFException e) {
                // If there are no more objects to read, return what we have.
                oi.close();
                return scores;
            } 
        }
    }

}