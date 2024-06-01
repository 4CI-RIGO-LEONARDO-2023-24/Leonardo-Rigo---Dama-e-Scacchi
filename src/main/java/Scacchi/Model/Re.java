/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scacchi.Model;

/**
 *
 * @author Leonardo Rigo
 */
public class Re extends Pezzo {
    public Re(ColorePezzo colore, Posizione posizione) {
        super(colore, posizione);
    } //fine metodo costruttore

    @Override
  public String getSimboloPezzo(){
      return "\u265A";
  }
  
  @Override
  public boolean eMossaValida(Posizione nuovaPosizione, Pezzo[][] board) {
      int rigDiff = Math.abs(posizione.getRig() - nuovaPosizione.getRig()); //valore assoluto (non conta se il movimento
      int colDiff = Math.abs(posizione.getColonna() - nuovaPosizione.getColonna()); // è verso l'alto o verso il basso)

      boolean eMossaUnaPos = rigDiff <= 1 && colDiff <= 1 && !(rigDiff == 0 && colDiff == 0);

      if (!eMossaUnaPos) {
          return false;
      }

      Pezzo pezzoDestinazione = board[nuovaPosizione.getRig()][nuovaPosizione.getColonna()];
      // Controllo aggiuntivo per evitare che il pedone si muova sulla casella del re avversario
        if (pezzoDestinazione instanceof Re && pezzoDestinazione.getColore() != this.colore) {
        return false;
    }
      return pezzoDestinazione == null || pezzoDestinazione.getColore() != this.getColore();
  } //fine eMossaValida()
} //fine classe Re