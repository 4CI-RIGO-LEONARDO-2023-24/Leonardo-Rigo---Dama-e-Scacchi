/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scacchi.Model;

/**
 *
 * @author Leonardo Rigo
 */
public class Cavallo extends Pezzo {
    public Cavallo(ColorePezzo colore, Posizione posizione) {
        super(colore, posizione);
    } //fine metodo costruttore
    
    @Override
    public String getSimboloPezzo() {
        return "\u265E";
    }

    @Override
    public boolean eMossaValida(Posizione nuovaPosizione, Pezzo[][] scacchiera) {
        if (nuovaPosizione.equals(this.posizione)) {
            return false;
        }

        int rigDiff = Math.abs(this.posizione.getRig() - nuovaPosizione.getRig());
        int colDiff = Math.abs(this.posizione.getColonna() - nuovaPosizione.getColonna());

        boolean eMossalValida = (rigDiff == 2 && colDiff == 1) || (rigDiff == 1 && colDiff == 2);

        if (!eMossalValida) {
            return false;
        }

        Pezzo pezzoObiettivo = scacchiera[nuovaPosizione.getRig()][nuovaPosizione.getColonna()];
        if (pezzoObiettivo == null) {
            return true;
        } else {
          // Controllo aggiuntivo per evitare che la pedina possa muoversi sulla casella del re avversario
          if (pezzoObiettivo instanceof Re) {
              return false;
          }
            return pezzoObiettivo.getColore() != this.getColore();
        }
    } //fine eMossaValida()
} //fine classe Cavallo