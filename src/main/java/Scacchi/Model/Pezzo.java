/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scacchi.Model;

/**
 *
 * @author Leonardo Rigo
 */
public abstract class Pezzo {
    protected Posizione posizione;
    protected ColorePezzo colore;

    public Pezzo(ColorePezzo colore, Posizione posizione) {
        this.colore = colore;
        this.posizione = posizione;
    } //fine metodo costruttore

    public ColorePezzo getColore() {
        return colore;
    } //fine getColore()

    public Posizione getPosizione() {
        return posizione;
    } //fine getPosizione()

    public void setPosizione(Posizione posizione) {
        if (posizione !=null) {
        this.posizione = posizione;
        }
    } //fine setPosizione()
    
    public String getSimboloPezzo() {
        return "";
    }

    public abstract boolean eMossaValida(Posizione nuovaPosizione, Pezzo[][] scacchiera);
} //fine classe Pezzo