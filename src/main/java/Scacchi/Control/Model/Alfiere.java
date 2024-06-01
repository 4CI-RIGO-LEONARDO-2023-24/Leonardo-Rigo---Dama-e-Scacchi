/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scacchi.Model;
/**
 *
 * @author Leonardo Rigo
 */
public class Alfiere extends Pezzo {
  public Alfiere(ColorePezzo color, Posizione position) {
      super(color, position);
  }
  
  @Override
  public String getSimboloPezzo() {
      return("\u265D");
  }

  @Override
  public boolean eMossaValida(Posizione nuovaPosizione, Pezzo[][] scacchiera) {
      int rigDiff = Math.abs(posizione.getRig() - nuovaPosizione.getRig());
      int colDiff = Math.abs(posizione.getColonna() - nuovaPosizione.getColonna());

      if (rigDiff != colDiff) {
          return false;
      }

      int rigPasso = nuovaPosizione.getRig() > posizione.getRig() ? 1 : -1;
      int colPasso = nuovaPosizione.getColonna() > posizione.getColonna() ? 1 : -1;
      int passi = rigDiff - 1;

      for (int i = 1; i <= passi; i++) {
          if (scacchiera[posizione.getRig() + i * rigPasso][posizione.getColonna() + i * colPasso] != null) {
              return false;
          }
      }

      Pezzo pezzoDestinazione = scacchiera[nuovaPosizione.getRig()][nuovaPosizione.getColonna()];
      if (pezzoDestinazione == null) {
          return true;
      } else if (pezzoDestinazione.getColore() != this.getColore()) {
          // Controllo aggiuntivo per evitare che il pedone si muova sulla casella del re avversario
        if (pezzoDestinazione instanceof Re) {
        return false;
        }
          return true;
      }

      return false;
  }
}
