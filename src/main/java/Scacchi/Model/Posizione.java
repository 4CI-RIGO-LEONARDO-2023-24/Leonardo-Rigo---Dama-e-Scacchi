/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scacchi.Model;

/**
 *
 * @author Leonardo Rigo
 */
public class Posizione {
    private int rig;
    private int colonna;

    public Posizione(int rig, int colonna) {
        this.rig = rig;
        this.colonna = colonna;
    } // fine metodo costruttore

    public int getRig() {
        return rig;
    } //fine getRig()

    public int getColonna() {
        return colonna;
    } //fine getColonna()
} // fine classe Posizione