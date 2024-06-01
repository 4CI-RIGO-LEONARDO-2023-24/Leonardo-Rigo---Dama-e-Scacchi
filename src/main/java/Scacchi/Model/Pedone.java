/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scacchi.Model;

/**
 *
 * @author Leonardo Rigo
 */
public class Pedone extends Pezzo {
    public Pedone(ColorePezzo colore, Posizione posizione) {
        super(colore, posizione);
    } //fine metodo costruttore

    @Override
    public String getSimboloPezzo() {
        return "\u265F";
    }
    @Override
    public boolean eMossaValida(Posizione nuovaPosizione, Pezzo[][] board) {
        int direzioneAvanzamento = colore == ColorePezzo.WHITE ? -1 : 1;
        int rigDiff = (nuovaPosizione.getRig() - posizione.getRig()) * direzioneAvanzamento;
        int colDiff = nuovaPosizione.getColonna() - posizione.getColonna();

        if (colDiff == 0 && rigDiff == 1 && board[nuovaPosizione.getRig()][nuovaPosizione.getColonna()] == null) {
            return true;
        }

        boolean isStartingPosition = (colore == ColorePezzo.WHITE && posizione.getRig() == 6) ||
                (colore == ColorePezzo.BLACK && posizione.getRig() == 1);
        if (colDiff == 0 && rigDiff == 2 && isStartingPosition
                && board[nuovaPosizione.getRig()][nuovaPosizione.getColonna()] == null) {
            int rigaIntermedia = posizione.getRig() + direzioneAvanzamento;
            if (board[rigaIntermedia][posizione.getColonna()] == null) {
                return true;
            }
        }

        if (Math.abs(colDiff) == 1 && rigDiff == 1 && board[nuovaPosizione.getRig()][nuovaPosizione.getColonna()] != null &&
                board[nuovaPosizione.getRig()][nuovaPosizione.getColonna()].colore != this.colore) {
            return true;
        }
        return false;
    } //fine eMossaValida()
}