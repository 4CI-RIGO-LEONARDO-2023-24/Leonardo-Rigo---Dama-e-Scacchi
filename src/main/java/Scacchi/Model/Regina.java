/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scacchi.Model;

/**
 *
 * @author Leonardo Rigo
 */
public class Regina extends Pezzo {
    public Regina(ColorePezzo colore, Posizione position) {
        super(colore, position);
    } //fine metodo costruttore
    
    @Override
    public String getSimboloPezzo() {
        return "\u265B";
    }

    @Override
    public boolean eMossaValida(Posizione nuovaPosizione, Pezzo[][] scacchiera) {
        if (nuovaPosizione.equals(this.posizione)) {
            return false;
        }

        int rigDiff = Math.abs(nuovaPosizione.getRig() - this.posizione.getRig());
        int colDiff = Math.abs(nuovaPosizione.getColonna() - this.posizione.getColonna());

        boolean lineaRetta = this.posizione.getRig() == nuovaPosizione.getRig() //controlla se il
                || this.posizione.getColonna() == nuovaPosizione.getColonna(); //movimento è in linea retta

        boolean diagonale = rigDiff == colDiff;

        if (!lineaRetta && !diagonale) {
            return false;
        }

        int rigDirezione = Integer.compare(nuovaPosizione.getRig(), this.posizione.getRig());
        int colDirection = Integer.compare(nuovaPosizione.getColonna(), this.posizione.getColonna());

        int rigCorrente = this.posizione.getRig() + rigDirezione;
        int colCorrente = this.posizione.getColonna() + colDirection;
        while (rigCorrente != nuovaPosizione.getRig() || colCorrente != nuovaPosizione.getColonna()) {
            if (scacchiera[rigCorrente][colCorrente] != null) {
                return false;
            }
            rigCorrente += rigDirezione;
            colCorrente += colDirection;
        }

        Pezzo pezzoDestinazione = scacchiera[nuovaPosizione.getRig()][nuovaPosizione.getColonna()];
        // Controllo aggiuntivo per evitare che la regina si muova sulla casella del re avversario
      if (pezzoDestinazione instanceof Re && pezzoDestinazione.getColore() != this.getColore()) {
          return false;
      }
        return pezzoDestinazione == null || pezzoDestinazione.getColore() != this.getColore();
    } //fine eMossaValida()
} // fine classe Regina