/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scacchi.Model;

/**
 *
 * @author Leonardo Rigo
 */
public class Torre extends Pezzo {
    public Torre(ColorePezzo colore, Posizione posizione) {
        super(colore, posizione);
    } //fine metodo costruttore
    
    @Override
    public String getSimboloPezzo() {
        return "\u265C";
    }

    @Override
    public boolean eMossaValida(Posizione nuovaPosizione, Pezzo[][] scacchiera) {
        if (posizione.getRig() == nuovaPosizione.getRig()) {
            int colonnaInizio = Math.min(posizione.getColonna(), nuovaPosizione.getColonna()) + 1;
            int colonnaFine = Math.max(posizione.getColonna(), nuovaPosizione.getColonna());
            for (int colonna = colonnaInizio; colonna < colonnaFine; colonna++) {
                if (scacchiera[posizione.getRig()][colonna] != null) {
                    return false;
                }
            }
        } else if (posizione.getColonna() == nuovaPosizione.getColonna()) {
            int rigInizio = Math.min(posizione.getRig(), nuovaPosizione.getRig()) + 1;
            int rigFine = Math.max(posizione.getRig(), nuovaPosizione.getRig());
            for (int rig = rigInizio; rig < rigFine; rig++) {
                if (scacchiera[rig][posizione.getColonna()] != null) {
                    return false;
                }
            }
        } else {
            return false;
        }

        Pezzo pezzoDestinazione = scacchiera[nuovaPosizione.getRig()][nuovaPosizione.getColonna()];
        if (pezzoDestinazione == null) {
            return true;
        } else if (pezzoDestinazione.getColore() != this.getColore()) {
            // Controllo aggiuntivo per evitare che la torre si muova sulla casella del re avversario
              if (pezzoDestinazione instanceof Re) {
              return false;
              }
            return true;
        }

        return false;
    } //fine eMossaValida()
}
