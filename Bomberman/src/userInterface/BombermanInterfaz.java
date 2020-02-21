/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import data.Board;

/**
 *
 * @author juan david carrilllo
 */
public interface BombermanInterfaz {
    
    void bienvenida();
    void menuPrincipal();
    void Instrucciones();
    static void imprimirTablero(Board tablero){ 
        System.out.println(tablero.toString());
    }
    void mostrarPuntaje(int puntaje);
    void mensajeDerrota();
    void mensajeVictoria();
    
    
}
