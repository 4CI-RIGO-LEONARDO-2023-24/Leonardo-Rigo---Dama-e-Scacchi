/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scacchi.Control;

/**
 *
 * @author Leonardo Rigo
 */

import Scacchi.Model.*;
import Scacchi.View.*;
import java.util.ArrayList;
import java.util.List;

public class Scacchi {
  private Scacchiera scacchiera;
  private boolean turnoBianco = true; // Bianco inizia la partita

  public Scacchi() {
      //questo è permesso soltanto se la partita non è ancora attiva
      this.scacchiera = new Scacchiera();
  }

  public Scacchiera getScacchiera() {
      return this.scacchiera;
  }

  public void resetGioco() {
      this.scacchiera = new Scacchiera();
      this.turnoBianco = true;
  }

  public ColorePezzo getColoreGiocatoreCorrente() {
      return turnoBianco ? ColorePezzo.WHITE : ColorePezzo.BLACK;
  }

  private Posizione posizioneSelezionata;

  public boolean pezzoSelezionato() {
      return posizioneSelezionata != null;
  }

  public boolean gestisciSelezioneCasella(int rig, int col) {
      if (posizioneSelezionata == null) {
          Pezzo pezzoSelezionato = scacchiera.getPezzo(rig, col);
          if (pezzoSelezionato != null
                  && pezzoSelezionato.getColore() == (turnoBianco ? ColorePezzo.WHITE : ColorePezzo.BLACK)) {
              posizioneSelezionata = new Posizione(rig, col);
              return false;
          }
      } else {
          boolean mossaFatta = faiMossa(posizioneSelezionata, new Posizione(rig, col));
          posizioneSelezionata = null;
          return mossaFatta;
      }
      return false;
  }

  public boolean faiMossa(Posizione inizio, Posizione end) {
      Pezzo pezzoInMovimento = scacchiera.getPezzo(inizio.getRig(), inizio.getColonna());
      if (pezzoInMovimento == null || pezzoInMovimento.getColore() != (turnoBianco ? ColorePezzo.WHITE : ColorePezzo.BLACK)) {
          return false;
      }
      
      if (pezzoInMovimento.eMossaValida(end, scacchiera.getScacchiera())) {
          scacchiera.muoviPezzo(inizio, end);
          turnoBianco = !turnoBianco;
          return true;
      }
      return false;
  }

  public boolean eSottoScacco(ColorePezzo coloreRe) {
      Posizione posizioneRe = trovaPosizioneRe(coloreRe);
      for (int rig = 0; rig < scacchiera.getScacchiera().length; rig++) {
          for (int col = 0; col < scacchiera.getScacchiera()[rig].length; col++) {
              Pezzo pezzo = scacchiera.getPezzo(rig, col);
              if (pezzo != null && pezzo.getColore() != coloreRe) {
                  if (pezzo.eMossaValida(posizioneRe, scacchiera.getScacchiera())) {
                      return true;
                  }
              }
          }
      }
      return false;
  }

  private Posizione trovaPosizioneRe(ColorePezzo colore) {
      for (int rig = 0; rig < scacchiera.getScacchiera().length; rig++) {
          for (int col = 0; col < scacchiera.getScacchiera()[rig].length; col++) {
              Pezzo pezzo = scacchiera.getPezzo(rig, col);
              if (pezzo instanceof Re && pezzo.getColore() == colore) {
                  return new Posizione(rig, col);
              }
          }
      }
      throw new RuntimeException("Re non trovato, il che non dovrebbe succedere.");
  }

  public boolean eScaccoMatto(ColorePezzo coloreRe) {
      if (!eSottoScacco(coloreRe)) {
          return false;
      }

      Posizione posizioneRe = trovaPosizioneRe(coloreRe);
      Re re = (Re) scacchiera.getPezzo(posizioneRe.getRig(), posizioneRe.getColonna());

      for (int righeOffset = -1; righeOffset <= 1; righeOffset++) {
          for (int colOffset = -1; colOffset <= 1; colOffset++) {
              if (righeOffset == 0 && colOffset == 0) {
                  continue;
              }
              Posizione nuovaPosizione = new Posizione(posizioneRe.getRig() + righeOffset,
                      posizioneRe.getColonna() + colOffset);

              if (ePosizioneSullaScacchiera(nuovaPosizione) && re.eMossaValida(nuovaPosizione, scacchiera.getScacchiera())
                      && !sarebbeSottoScaccoDopoMossa(coloreRe, posizioneRe, nuovaPosizione)) {
                  return false;
              }
          }
      }
      return true;
  }

  private boolean ePosizioneSullaScacchiera(Posizione posizione) {
      return posizione.getRig() >= 0 && posizione.getRig() < scacchiera.getScacchiera().length &&
              posizione.getColonna() >= 0 && posizione.getColonna() < scacchiera.getScacchiera()[0].length;
  }

  private boolean sarebbeSottoScaccoDopoMossa(ColorePezzo coloreRe, Posizione from, Posizione to) {
      Pezzo temp = scacchiera.getPezzo(to.getRig(), to.getColonna());
      scacchiera.setPezzo(to.getRig(), to.getColonna(), scacchiera.getPezzo(from.getRig(), from.getColonna()));
      scacchiera.setPezzo(from.getRig(), from.getColonna(), null);

      boolean sottoScacco = eSottoScacco(coloreRe);

      scacchiera.setPezzo(from.getRig(), from.getColonna(), scacchiera.getPezzo(to.getRig(), to.getColonna()));
      scacchiera.setPezzo(to.getRig(), to.getColonna(), temp);

      return sottoScacco;
  }

  public ArrayList<Posizione> getMossePermessePerPezzoA(Posizione posizione) {
      Pezzo pezzoSelezionato = scacchiera.getPezzo(posizione.getRig(), posizione.getColonna());
      if (pezzoSelezionato == null)
          return new ArrayList<>();

      ArrayList<Posizione> mossePermesse = new ArrayList<>();
      switch (pezzoSelezionato.getClass().getSimpleName()) { //legge il nome della classe e lo associa al caso
          case "Pedone":
              aggiungiMossePedone(posizione, pezzoSelezionato.getColore(), mossePermesse);
              break;
          case "Torre":
              aggiungiMosseInLinea(posizione, new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }, mossePermesse);
              break;
          case "Cavallo":
              aggiungiMosseSingole(posizione, new int[][] { { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }, { 1, 2 }, { -1, 2 },
                      { 1, -2 }, { -1, -2 } }, mossePermesse);
              break;
          case "Alfiere":
              aggiungiMosseInLinea(posizione, new int[][] { { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } }, mossePermesse);
              break;
          case "Regina":
              aggiungiMosseInLinea(posizione, new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { -1, -1 },
                      { 1, -1 }, { -1, 1 } }, mossePermesse);
              break;
          case "Re":
              aggiungiMosseSingole(posizione, new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { -1, -1 },
                      { 1, -1 }, { -1, 1 } }, mossePermesse);
              break;
      }
      return mossePermesse;
  }

  private void aggiungiMosseInLinea(Posizione posizione, int[][] direzioni, List<Posizione> mossePermesse) {
      for (int[] d : direzioni) {
          Posizione nuovaPos = new Posizione(posizione.getRig() + d[0], posizione.getColonna() + d[1]);
          while (ePosizioneSullaScacchiera(nuovaPos)) {
              if (scacchiera.getPezzo(nuovaPos.getRig(), nuovaPos.getColonna()) == null) {
                  mossePermesse.add(new Posizione(nuovaPos.getRig(), nuovaPos.getColonna()));
                  nuovaPos = new Posizione(nuovaPos.getRig() + d[0], nuovaPos.getColonna() + d[1]);
              } else {
                  if (scacchiera.getPezzo(nuovaPos.getRig(), nuovaPos.getColonna()).getColore() != scacchiera
                          .getPezzo(posizione.getRig(), posizione.getColonna()).getColore()) {
                      mossePermesse.add(nuovaPos);
                  }
                  break;
              }
          }
      }
  }

  private void aggiungiMosseSingole(Posizione posizione, int[][] mosse, List<Posizione> mossePermesse) {
      for (int[] mossa : mosse) {
          Posizione nuovaPos = new Posizione(posizione.getRig() + mossa[0], posizione.getColonna() + mossa[1]);
          if (ePosizioneSullaScacchiera(nuovaPos) && (scacchiera.getPezzo(nuovaPos.getRig(), nuovaPos.getColonna()) == null ||
                  scacchiera.getPezzo(nuovaPos.getRig(), nuovaPos.getColonna()).getColore() != scacchiera
                          .getPezzo(posizione.getRig(), posizione.getColonna()).getColore())) {
              mossePermesse.add(nuovaPos);
          }
      }
  }
  //!!
  public int getRigaPezzoSelezionato() {
    return posizioneSelezionata != null ? posizioneSelezionata.getRig() : -1;
}

public int getColonnaPezzoSelezionato() {
    return posizioneSelezionata != null ? posizioneSelezionata.getColonna() : -1;
}


  private void aggiungiMossePedone(Posizione posizione, ColorePezzo colore, List<Posizione> mossePermesse) {
      int direzione = colore == ColorePezzo.WHITE ? -1 : 1;
      Posizione nuovaPos = new Posizione(posizione.getRig() + direzione, posizione.getColonna());
      if (ePosizioneSullaScacchiera(nuovaPos) && scacchiera.getPezzo(nuovaPos.getRig(), nuovaPos.getColonna()) == null) {
          mossePermesse.add(nuovaPos);
      }

      if ((colore == ColorePezzo.WHITE && posizione.getRig() == 6)
              || (colore == ColorePezzo.BLACK && posizione.getRig() == 1)) {
          nuovaPos = new Posizione(posizione.getRig() + 2 * direzione, posizione.getColonna());
          Posizione posIntermedia = new Posizione(posizione.getRig() + direzione, posizione.getColonna());
          if (ePosizioneSullaScacchiera(nuovaPos) && scacchiera.getPezzo(nuovaPos.getRig(), nuovaPos.getColonna()) == null
                  && scacchiera.getPezzo(posIntermedia.getRig(), posIntermedia.getColonna()) == null) {
              mossePermesse.add(nuovaPos);
          }
      }

      int[] cattureColonne = { posizione.getColonna() - 1, posizione.getColonna() + 1 };
      for (int col : cattureColonne) {
          nuovaPos = new Posizione(posizione.getRig() + direzione, col);
          if (ePosizioneSullaScacchiera(nuovaPos) && scacchiera.getPezzo(nuovaPos.getRig(), nuovaPos.getColonna()) != null &&
                  scacchiera.getPezzo(nuovaPos.getRig(), nuovaPos.getColonna()).getColore() != colore) {
              mossePermesse.add(nuovaPos);
          }
      }
  }
}
