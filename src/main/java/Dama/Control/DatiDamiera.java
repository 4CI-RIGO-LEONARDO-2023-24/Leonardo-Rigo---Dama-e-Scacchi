/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dama.Control;

import Dama.Model.DamaMosse;
import Dama.View.Damiera;
import java.util.ArrayList;

/**
 *
 * @author Leonardo Rigo
 *
 *
     * Un oggetto di questa classe gestisce le informazioni di una partita di dama.
     * E'noto che tipo di pezzo sta in ogni riquadro della damiera.
     * ROSSO si muove verso l'"alto" della damiera (cioe' il numero di riga decresce),
     * mentre NERO si muove verso il "basso" della damiera (cioe' il numero di riga number aumenta).
     * I metodi sono fatti per ritornare liste di mosse disponibili.
     */
    public class DatiDamiera {

        /*  Le seguenti costanti rappresentano i possibili contenuti di un
            riquadro sulla damiera. Le costanti ROSSO e NERO rappresentano anche
            i giocatori nel gioco. */

        public static final int
                    EMPTY = 0,
                    ROSSO = 1,
                    RE_ROSSO = 2,
                    NERO = 3,
                    RE_NERO = 4;

        int[][] damiera;  // damiera[r][c] sono i contenuti della riga r, colonna c.  

        /**
         * Costruttore.  Crea la damiera e la imposta per una nuova partita.
         */
        public DatiDamiera() {
            damiera = new int[8][8];
            impostaPartita();
        }

        /**
         * Imposta la damiera i pezzi nelle posizioni iniziali per cominciare
         * la partita. I pezzi possono solo essere trovati in riquadri
         * che soddisfano rig % 2 == col % 2.  All'inizio della partita,
         * tutti i riquadri nelle prime tre righe contengono riquadri neri
         * e tutti i riquadri nelle ultime tre righe contengono riquadri rossi.
         */
        public void impostaPartita() {
            for (int rig = 0; rig < 8; rig++) {
                for (int col = 0; col < 8; col++) {
                    if ( rig % 2 == col % 2 ) {
                        if (rig < 3)
                            damiera[rig][col] = NERO;
                        else if (rig > 4)
                            damiera[rig][col] = ROSSO;
                        else
                            damiera[rig][col] = EMPTY;
                    }
                    else {
                        damiera[rig][col] = EMPTY;
                    }
                }
            }
        }  // fine impostaPartita()

        /**
         * Ritorna il contenuto del riquadro nella riga e nella colonna specificata.
         */
        public int pezzoIn(int rig, int col) {
            return damiera[rig][col];
        } //fine pezzoIn()

        /**
         * Esegue la mossa specificata. Si assume che la mossa non sia vuota
         * e che la mossa che rappresenta sia valida.
         */
        public void eseguiMossa(DamaMosse mossa) {
            eseguiMossa(mossa.rigaPartenza, mossa.colonnaPartenza, mossa.rigaDestinazione, mossa.colonnaDestinazione);
        } //fine eseguiMossa()

        /**
         * Esegue la mossa da (rigaPartenza,colonnaPartenza) a (rigaDestinazione,colonnaDestinazione).
         * Si assume che questa mossa sia permessa.  Se la mossa e' una presa, il
         * pezzo preso e' rimosso dalla damiera.  Se un pezzo si muove verso l'ultima riga
         * dal lato opposto della damiera (quello dell'avversario), il pezzo 
         * diventa un re.
         */
        public void eseguiMossa(int rigaPartenza, int colonnaPartenza, int rigaDestinazione, int colonnaDestinazione) {
            damiera[rigaDestinazione][colonnaDestinazione] = damiera[rigaPartenza][colonnaPartenza];
            damiera[rigaPartenza][colonnaPartenza] = EMPTY;
            if (rigaPartenza - rigaDestinazione == 2 || rigaPartenza - rigaDestinazione == -2) {
                // La mossa e' una presa. Rimuove il pezzo preso dalla damiera.
                int presaRiga = (rigaPartenza + rigaDestinazione) / 2;  // Riga del pezzo preso.(Si trova tra la riga di destinazione e quella di partenza)
                int presaColonna = (colonnaPartenza + colonnaDestinazione) / 2;  // Colonna del pezzo preso.(Si trova tra la riga di destinazione e quella di partenza)
                /*if(damiera[presaRiga][presaColonna] == ROSSO)
                    //Damiera.contaRosso -= 1;
                else
                   // Damiera.contaNero -= 1;*/
                damiera[presaRiga][presaColonna] = EMPTY; //l'elemento preso viene eliminato
            }
            // controlli per vedere se l'elemento diventa Re
            if (rigaDestinazione == 0 && damiera[rigaDestinazione][colonnaDestinazione] == ROSSO)
                damiera[rigaDestinazione][colonnaDestinazione] = RE_ROSSO;
            if (rigaDestinazione == 7 && damiera[rigaDestinazione][colonnaDestinazione] == NERO)
                damiera[rigaDestinazione][colonnaDestinazione] = RE_NERO;
        } //fine eseguiMossa()

        /**
         * Restituisce un array contenente tutte le mosse permesse
         * per il giocatore specificato sulla damiera corrente. Se il giocatore
         * non ha mosse a disposizione, null e' ritornato. Il valore del giocatore
         * dovrebbe essere una delle costanti ROSSO o NERO; altrimenti, null
         * e' ritornato. Se il valore di ritorno non e' nullo, consiste
         * interamente di mosse di salto o interamente di mosse semplici, ma se
         * il giocatore puo' saltare, solo i salti sono permessi,
         * non anche le mosse "normali".
         */
        public DamaMosse[] getMossePermesse(int giocatore) {

            if (giocatore != ROSSO && giocatore != NERO) //condizione teoricamente impossibile
                return null;

            int giocatoreRe;  // rappresenta un Re appartenente al giocatore.
            if (giocatore == ROSSO)
                giocatoreRe = RE_ROSSO;
            else
                giocatoreRe = RE_NERO;

            ArrayList<DamaMosse> mosse = new ArrayList<DamaMosse>();  // Le mosse verranno salvate in questo ArrayList.

            /*  Inizialmente, cerca ogni possibile presa. Controlla anche ogni riquadro sulla damiera.
             Se in un riquadro controllato c'e' uno dei pezzi del giocatore che deve muoversi, cerca ogni possibile
             salto in ognuna delle quattro direzioni a partire da quel riquadro. Se c'e' 
             una mossa di salto nella direzione che viene controllata, la mette nell' ArrayList delle mosse.
             */

            for (int rig = 0; rig < 8; rig++) {
                for (int col = 0; col < 8; col++) {
                    if (damiera[rig][col] == giocatore || damiera[rig][col] == giocatoreRe) {
                        if (puoPrendere(giocatore, rig, col, rig+1, col+1, rig+2, col+2))
                            mosse.add(new DamaMosse(rig, col, rig+2, col+2));
                        if (puoPrendere(giocatore, rig, col, rig-1, col+1, rig-2, col+2))
                            mosse.add(new DamaMosse(rig, col, rig-2, col+2));
                        if (puoPrendere(giocatore, rig, col, rig+1, col-1, rig+2, col-2))
                            mosse.add(new DamaMosse(rig, col, rig+2, col-2));
                        if (puoPrendere(giocatore, rig, col, rig-1, col-1, rig-2, col-2))
                            mosse.add(new DamaMosse(rig, col, rig-2, col-2));
                    }
                }
            }

            /*  Se qualsiasi mossa di salto viene trovata, allora l'utente deve saltare, quindi non vengono
             aggiunte mosse regolari. Invece, se non vengono trovate prese, cerca
             qualsiasi mossa regolare. Controlla ogni riquadro della damiera.
             Se un riquadro contiene uno dei pezzi del giocatore corrente, cerca una possibile mossa
             in ognuna delle quattro direzioni da quel riquadro. Se c'Ã¨ una mossa 
             permessa in quella direzione, la mette nell'ArrayList delle mosse.
             */

            if (mosse.isEmpty()) {
                for (int rig = 0; rig < 8; rig++) {
                    for (int col = 0; col < 8; col++) {
                        if (damiera[rig][col] == giocatore || damiera[rig][col] == giocatoreRe) {
                            if (puoMuoversi(giocatore,rig,col,rig+1,col+1))
                                mosse.add(new DamaMosse(rig,col,rig+1,col+1));
                            if (puoMuoversi(giocatore,rig,col,rig-1,col+1))
                                mosse.add(new DamaMosse(rig,col,rig-1,col+1));
                            if (puoMuoversi(giocatore,rig,col,rig+1,col-1))
                                mosse.add(new DamaMosse(rig,col,rig+1,col-1));
                            if (puoMuoversi(giocatore,rig,col,rig-1,col-1))
                                mosse.add(new DamaMosse(rig,col,rig-1,col-1));
                        }
                    }
                }
            }

            /* Se vengono trovate mosse permesse, crea un array grande abbastanza
               da contenere tutte le mosse permesse, copia le
               mosse permesse dall'ArrayList nell'array, e ritorna l'array.
               Altrimenti, se non vengono trovate mosse permesse, ritorna null*/

            if (!mosse.isEmpty()) {
                DamaMosse[] mosseArray = new DamaMosse[mosse.size()];
                for (int i = 0; i < mosse.size(); i++)
                    mosseArray[i] = mosse.get(i);
                return mosseArray;
            } else return null;

        }  // fine getMossePermesse()

        /**
         * Ritorna una lista delle prese permesse che il giocatore corrente puo'
         * fare partendo dalla riga e dalla colonna specificate. Se nessun
         * salto e' possibile, null viene ritornato. La logica e'simile a quella
         * del metodo getMossePermesse().
         */
        public DamaMosse[] getPresePermesseDa(int giocatore, int rig, int col) {
            if (giocatore != ROSSO && giocatore != NERO)
                return null;
            int giocatoreRe;  // La costante rappresenta un Re appartenente al giocatore.
            if (giocatore == ROSSO)
                giocatoreRe = RE_ROSSO;
            else
                giocatoreRe = RE_NERO;
            ArrayList<DamaMosse> mosse = new ArrayList<DamaMosse>();  // Le mosse permesse verranno salvate in questo ArrayList.
            if (damiera[rig][col] == giocatore || damiera[rig][col] == giocatoreRe) {
                if (puoPrendere(giocatore, rig, col, rig+1, col+1, rig+2, col+2))
                    mosse.add(new DamaMosse(rig, col, rig+2, col+2));
                if (puoPrendere(giocatore, rig, col, rig-1, col+1, rig-2, col+2))
                    mosse.add(new DamaMosse(rig, col, rig-2, col+2));
                if (puoPrendere(giocatore, rig, col, rig+1, col-1, rig+2, col-2))
                    mosse.add(new DamaMosse(rig, col, rig+2, col-2));
                if (puoPrendere(giocatore, rig, col, rig-1, col-1, rig-2, col-2))
                    mosse.add(new DamaMosse(rig, col, rig-2, col-2));
            }
            if (!mosse.isEmpty()) {
                DamaMosse[] mosseArray = new DamaMosse[mosse.size()];
                for (int i = 0; i < mosse.size(); i++)
                    mosseArray[i] = mosse.get(i);
                return mosseArray;
            } else return null;
        }  // fine getMossePermesseDa()

        /**
         * Questo metodo e' chiamato dai due metodi precedenti per controllare 
         * se il giocatore corrente puo' muoversi da (r1,c1) a (r3,c3). Si assume
         * che il giocatore abbia un pezzo in (r1,c1), che (r3,c3)
         * sia distante 2 righe e 2 colonne da (r1,c1) e che
         * (r2,c2) sia il riquadro compreso tra (r1,c1) e (r3,c3).
         */
        public boolean puoPrendere(int giocatore, int r1, int c1, int r2, int c2, int r3, int c3) {

            if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
                return false;  // (r3,c3) e' fuori dalla damiera.

            if (damiera[r3][c3] != EMPTY)
                return false;  // (r3,c3) contiene gia' un pezzo.

            if (giocatore == ROSSO) {
                if (damiera[r1][c1] == ROSSO && r3 > r1)
                    return false;  // Il pezzo rosso normale (non il Re rosso) puo'solo muoversi verso l'alto.
                if (damiera[r2][c2] != NERO && damiera[r2][c2] != RE_NERO)
                    return false;  // Non ci sono pezzi neri da prendere.
                return true;  // La presa e' permessa.
            }
            else {
                if (damiera[r1][c1] == NERO && r3 < r1)
                    return false;  // Il pezzo nero normale (non il Re nero) puo'solo muoversi verso l'alto.
                if (damiera[r2][c2] != ROSSO && damiera[r2][c2] != RE_ROSSO)
                    return false;  // Non ci sono pezzi rossi da prendere.
                return true;  // La presa e'permessa.
            }

        }  // fine metodo puoPrendere()

        /**
         * Questo e' chiamato dal metodo getMossePermesse() per determinare se
         * il giocatore puo'muoversi da (r1,c1) a (r2,c2). Si assume che 
         * (r1,r2) contiene uno dei pezzi dei giocatori e che (r2,c2) e' un pezzo vicino
         */
        public boolean puoMuoversi(int giocatore, int r1, int c1, int r2, int c2) {

            if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
                return false;  // (r2,c2) e' fuori dalla damiera.

            if (damiera[r2][c2] != EMPTY)
                return false;  // (r2,c2) contiene gia' un pezzo.

            if (giocatore == ROSSO) {
                if (damiera[r1][c1] == ROSSO && r2 > r1)
                    return false;  // Il pezzo rosso normale puo' solo muoversi verso il basso.
                return true;  // La mossa e' permessa.
            }
            else {
                if (damiera[r1][c1] == NERO && r2 < r1)
                    return false;  // Il pezzo nero normale puo' solo muoversi verso l'alto.
                return true;  // La mossa e' permessa.
            }

        }  // fine metodo puoMuoversi()

} // fine classe DatiDamiera
 
