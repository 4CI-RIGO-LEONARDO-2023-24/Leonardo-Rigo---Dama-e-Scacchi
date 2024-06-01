/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dama.View;

/**
 *
 * @author Leonardo Rigo
 */
import Dama.Control.DatiDamiera;
import Dama.Model.DamaMosse;
import static com.mycompany.dama_rigo_scacchi_rigo.Dama_Rigo_Scacchi_Rigo.salvaDatiPartita; //serve per il metodo del salvataggio su csv

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

    /** Questo panel mostra una damiera di dimensione 160 per 160 pixel con
     * un margine nero di 2 pixel. Si assume quindi che la dimensione del panel
     * sia impostata ad esattamente 164 per 164 pixel. Questa classe permette ai
     * giocatori di giocare a dama permettemdo l'interazione col programma e mostra a video la damiera.
     */
    public class Damiera extends JPanel implements ActionListener, MouseListener {
        
        //Dichiarazione variabili

        DatiDamiera datiDamiera; // I dati della damiera sono qui.
                            //    damiera, inoltre, genera
                            //    liste di mosse permesse.

        boolean partitaInCorso; // C'e' una partita in corso ?

        int giocatoreCorrente;      // A chi tocca ora ?  I valori possibili
                                //    sono DatiDamiera.ROSSO e DatiDamiera.NERO.

        int rigaSelezionata, colonnaSelezionata;   // Se il giocatore corrente ha scelto un pezzo da
                                        //     muovere, queste variabili indicano riga e colonna
                                        //     contenenti quel pezzo. Se non è stato selezionato
                                        //     alcun pezzo, allora rigaSelezionata e' -1.

        DamaMosse[] mossePermesse;  // Un array contenente le mosse permesse
                                    //    per il giocatore corrente.
        
        JButton nuovaPartitaButton;  // Button per cominciare nuova partita.
    
        JButton ritiroButton;   // Button che un giocatore puo'usare per finire 
                                    // la partita ritirandosi.

        JLabel messaggio;  // Label per mostrare messaggi all'utente.

        /**
         * Costruttore.  Crea i buttons e la label.
         * Ascolta click sui buttons, crea la damiera e
         * comincia la partita.
         */
        public Damiera() {
            setBackground(Color.BLACK);
            addMouseListener(this);
            ritiroButton = new JButton("");
            ritiroButton.addActionListener(this);
            ritiroButton.setIcon(new ImageIcon("ritiroButton.png"));
            ritiroButton.setBorder(null);
            ritiroButton.setContentAreaFilled(false);
            nuovaPartitaButton = new JButton("Nuova Partita");
            nuovaPartitaButton.addActionListener(this);
            messaggio = new JLabel("",JLabel.CENTER);
            messaggio.setFont(new  Font("Arial", Font.BOLD, 14));
            messaggio.setForeground(Color.green);
            datiDamiera = new DatiDamiera();
            nuovaPartita();
        } //fine metodo costruttore

        /**
         * Risponde al click dell'utente su uno dei due bottoni.
         */
        @Override
        public void actionPerformed(ActionEvent evt) {
            Object src = evt.getSource();
            if (src == nuovaPartitaButton)
                nuovaPartita();
            else if (src == ritiroButton)
                ritiro();
        } //fine actionPerformed()
        
        /**
         * Comincia nuova partita
         */
        public void nuovaPartita() {
            if (partitaInCorso == true) {
                    // Non dovrebbe essere permesso, ma si controlla per sicurezza.
                messaggio.setText("Finisci la partita prima !");
                return;
            }
            salvaDatiPartita("Partita a Dama cominciata");
            datiDamiera.impostaPartita();   // Imposta i pezzi.
            giocatoreCorrente = DatiDamiera.ROSSO;   // ROSSO e'il primo a muoversi.
            mossePermesse = datiDamiera.getMossePermesse(DatiDamiera.ROSSO);  // Recupera le mosse permesse dal giocatore rosso.
            rigaSelezionata = -1;   // ROSSO non ha ancora scelto un pezzo da muovere.
            messaggio.setText("Rosso: Fai la tua mossa.");
            partitaInCorso = true;
            nuovaPartitaButton.setEnabled(false);
            ritiroButton.setEnabled(true);
            repaint(); //aggiorna la grafica
        } //fine nuovaPartita()
        
        /**
         * Il giocatore corrente si ritira.La partita finisce, e l'avversario vince.
         */
        public void ritiro() {
            if (partitaInCorso == false) {  // Dovrebbe essere impossibile.
                messaggio.setText("Non si sta giocando alcuna partita !");
                return;
            }
            if (giocatoreCorrente == DatiDamiera.ROSSO)
                finePartita("ROSSO si ritira. NERO vince");
            else
                finePartita("NERO si ritira. ROSSO vince");
        } //fine metodo ritiro()
        
        public void impostaBottoniEdEtichetta(JButton nuovaPartitaButton, JButton ritiroButton, JLabel messaggio) {
        this.nuovaPartitaButton = nuovaPartitaButton;
        this.ritiroButton = ritiroButton;
        this.messaggio = messaggio;
        } //fine impostaBottoniEdEtichetta()

        /**
         * La partita finisce. Il parametro, str, e' mostrato come un messaggio
         * all'utente. Gli stati dei bottoni sono sistemati, quindi i giocatori
         * possono cominciare una nuova partita. Questo metodo e' chiamato quando la partita
         * finisce in un momento qualsiasi in questa classe.
         */
        public void finePartita(String str) {
            messaggio.setText(str);
            salvaDatiPartita(str + " la partita di Dama");
            partitaInCorso = false;
            nuovaPartitaButton.setEnabled(true);
            ritiroButton.setEnabled(false);
        }//fine metodo finePartita()
        
        /**
         * Risponde al click dell'utente sulla damiera. Se non ci sono partite in corso, mostra 
         * un messaggio di errore. Altrimenti, cerca la riga e la colonna che l'utente 
         * ha premuto e chiama clickRiquadro() per gestirla.
         */
       
        @Override
        public void mousePressed(MouseEvent evt) {
            if (partitaInCorso == false)
                messaggio.setText("Premi \"Nuova Partita\" per cominciare una nuova partita.");
            else {
                int col = (evt.getX() - 2) / 61;
                int rig = (evt.getY() - 2) / 61;
                if (col >= 0 && col < 8 && rig >= 0 && rig < 8)
                    clickRiquadro(rig,col);
            }
        } //fine mousePressed()

        @Override
        public void mouseReleased(MouseEvent evt) { }
        @Override
        public void mouseClicked(MouseEvent evt) { }
        @Override
        public void mouseEntered(MouseEvent evt) { }
        @Override
        public void mouseExited(MouseEvent evt) { }


        /**
         * Questo metodo e' chiamato da mousePressed() quando un giocatore preme su un riquadro
         * nella riga e nella colonna specificata. E' stato gia' controllato
         * che la partita e' in corso.
         */
        public void clickRiquadro(int rig, int col) {

            /* Se il giocatore ha cliccato su uno dei pezzi che il giocatore
               puo' muovere, segna questa riga e colonna come selezionata e ritorna.
               (Questo potrebbe cambiare una precedente selezione).
               Resetta il messaggio, nel caso fosse gia' a video un  messaggio di errore.
            */

            for (int i = 0; i < mossePermesse.length; i++)
                if (mossePermesse[i].rigaPartenza == rig && mossePermesse[i].colonnaPartenza == col) {
                    rigaSelezionata = rig;
                    colonnaSelezionata = col;
                    if (giocatoreCorrente == DatiDamiera.ROSSO)
                        messaggio.setText("ROSSO:  Fai la tua mossa.");
                    else
                        messaggio.setText("NERO:  Fai la tua mossa.");
                    repaint();  //serve per aggiornare la grafica
                    return;
                }

            /* Se nessun pezzo viene scelto per essere mosso, l'utente prima deve
             scegliere un pezzo. Mostra un messaggio di errore e ritorna. */

            if (rigaSelezionata < 0) {
                messaggio.setText("Scegli un pezzo che puoi muovere.");
                return;
            }

            /* Se l'utente seleziona un riquadro in cui il pezzo scelto 
               puo' essere mosso, allora viene eseguita la mossa e ritorna. */

            for (int i = 0; i < mossePermesse.length; i++)
                if (mossePermesse[i].rigaPartenza == rigaSelezionata && mossePermesse[i].colonnaPartenza == colonnaSelezionata
                && mossePermesse[i].rigaDestinazione == rig && mossePermesse[i].colonnaDestinazione == col) {
                    lanciaEseguiMossa(mossePermesse[i]);
                    return;
                }

            /* Se arriviamo a questo punto, e' stato selezionato un pezzo, ed il riquadro che
             l'utente ha appena selezionato non e' uno in cui il pezzo puo' essere mosso.
             Viene mostrato un messaggio di errore. */

            messaggio.setText("<html>" + "Selezione non valida.<br>Seleziona un riquadro in cui puoi muoverti." + "</html>"); 
            //ho aggiunto i tag html per mettere la newline (fonte:https://stackoverflow.com/questions/1090098/newline-in-jlabel)

        }  // fine clickRiquadro()

        /**
         * Questo metodo e'chiamato quando il giocatore corrente ha scelto che mossa
         * fare. Esegue la mossa, e poi o finisce o continua la partita in base alla situazione.
         */
        public void lanciaEseguiMossa(DamaMosse mossa) {

            datiDamiera.eseguiMossa(mossa);

            /* Se la mossa era una presa, e'possibile che il giocatore debba farne
             un'altra. Cerca prese permesse partendo dal riquadro in cui
             il giocatore si e' appena mosso. ISe ce ne fosse almeno una, la presa
             va obbligatoriamente effettuata.Lo stesso giocatore continua a muoversi.
             */

            if (mossa.ePresa()) { //se l'ultima mossa era una presa, si controlla se va eseguita un'altra presa
                mossePermesse = datiDamiera.getPresePermesseDa(giocatoreCorrente,mossa.rigaDestinazione,mossa.colonnaDestinazione);
                if (mossePermesse != null) {
                    if (giocatoreCorrente == DatiDamiera.ROSSO)
                        messaggio.setText("ROSSO: Devi mangiare ancora");
                    else
                        messaggio.setText("NERO: Devi mangiare ancora.");
                    rigaSelezionata = mossa.rigaDestinazione;  // Dato che solo un pezzo puo'essere mosso, lo seleziona.
                    colonnaSelezionata = mossa.colonnaDestinazione;
                    repaint(); //aggiorna la grafica
                    return;
                }
            }

            /* Il turno del giocatoreCorrente e' finito, quindi passa all'altro giocatore.
             Recupera le mosse permesse di quel giocatore. Se il giocatore invece non puo' fare
             alcuna mossa, allora la partita finisce. */

            if (giocatoreCorrente == DatiDamiera.ROSSO) {
                giocatoreCorrente = DatiDamiera.NERO;
                mossePermesse = datiDamiera.getMossePermesse(giocatoreCorrente);
                if (mossePermesse == null)
                    finePartita("NERO non ha piu' pedine, Rosso ha vinto");
                else if (mossePermesse[0].ePresa())
                    messaggio.setText("NERO:  Fai la tua mossa. Devi mangiare.");
                else
                    messaggio.setText("NERO: Fai la tua mossa.");
            }
            else {
                giocatoreCorrente = DatiDamiera.ROSSO;
                mossePermesse = datiDamiera.getMossePermesse(giocatoreCorrente);
                if (mossePermesse == null)
                    finePartita("ROSSO non ha piu' pedine.  NERO vince");
                else if (mossePermesse[0].ePresa())
                    messaggio.setText("ROSSO:  Fai la tua mossa.  Devi mangiare.");
                else
                    messaggio.setText("ROSSO: Fai la tua mossa.");
            }

            /* Imposta rigaSelezionata = -1 per indicare che il giocatore non ha
               ancora scelto un pezzo da muovere. */

            rigaSelezionata = -1;

            /* Come cortesia verso l'utente, se tutte le mosse permesse usano lo stesso pezzo, allora
               seleziona quel pezzo automaticamente così l'utente non dovra'
               premerci sopra per selezionarlo.*/

            if (mossePermesse != null) {
                boolean stessoRiquadroDiPartenza = true;
                for (int i = 1; i < mossePermesse.length; i++)
                    if (mossePermesse[i].rigaPartenza != mossePermesse[0].rigaPartenza
                    || mossePermesse[i].colonnaPartenza != mossePermesse[0].colonnaPartenza) {
                        stessoRiquadroDiPartenza = false;
                        break;
                    }
                if (stessoRiquadroDiPartenza) {
                    rigaSelezionata = mossePermesse[0].rigaPartenza;
                    colonnaSelezionata = mossePermesse[0].colonnaPartenza;
                }
            }

            /* Si assicura che la damiera sia ridisegnata nel suo nuovo stato. */

            repaint();

        }  // fine metodo lanciaEseguiMossa();

        /**
         * Disegna uno schema di colori per la damiera in grigio e grigio chiaro,
         * inoltre disegna i pezzi.
         * Se una partita e' in corso, evidenzia le mosse permesse.
         */
        public void paintComponent(Graphics g) {
            
            /* Attiva l'antialiasing per ottenere ovali piu' definiti. */
            //https://www.html.it/pag/15124/le-funzioni-paint-repaint-e-update-la-classe-graphics-e-graphics2d/
            //https://www.tabnine.com/code/java/methods/java.awt.Graphics/fillOval
            
            Graphics2D g2 = (Graphics2D)g; //estende la classe Graphics
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            ///Attiva l'antialiasing per ottenere ovali piu' definiti. fonte:https://stackoverflow.com/questions/1094539/how-to-draw-a-decent-looking-circle-in-java

            /* Disegna un confine nero di due pixel around agli angoli del riquadro. */

            g.setColor(Color.BLACK);
            g.drawRect(0,0,getSize().width-1,getSize().height-1);
            g.drawRect(1,1,getSize().width-3,getSize().height-3);

            //fonte drawRect:void - drawRect(int x, int y, int width, int height) Disegna il contorno del rettangolo specificato.
            
            /* Disegna i riquadri della damiera ed i pezzi. */

            for (int rig = 0; rig < 8; rig++) {
                for (int col = 0; col < 8; col++) {
                    if ( rig % 2 == col % 2 ) //alternando a 2 a 2 colora lo sfondo
                        g.setColor(Color.LIGHT_GRAY);  //delle caselle
                    else
                        g.setColor(Color.GRAY);
                    g.fillRect(3 + col*61, 2 + rig*61, 61, 61);
                    switch (datiDamiera.pezzoIn(rig,col)) {
                    case DatiDamiera.ROSSO:
                        g.setColor(Color.RED);
                        g.fillOval(4 + col*61, 4 + rig*61, 58, 58);
                        break;
                    case DatiDamiera.NERO:
                        g.setColor(Color.BLACK);
                        g.fillOval(4 + col*61, 4 + rig*61, 58, 58);
                        break;
                    case DatiDamiera.RE_ROSSO:
                        g.setColor(Color.RED);
                        g.fillOval(4 + col*61, 4 + rig*61, 58, 58);
                        g.setColor(Color.WHITE);
                        g.drawString("R", 7 + col*58, 16 + rig*58);
                        break;
                    case DatiDamiera.RE_NERO:
                        g.setColor(Color.BLACK);
                        g.fillOval(4 + col*58, 4 + rig*58, 58, 58);
                        g.setColor(Color.WHITE);
                        g.drawString("R", 7 + col*58, 16 + rig*58);
                        break;
                    }
                }
            }

            /* Se una partita e'in corso, evidenzia le mosse permesse.
               mossePermesse non e' mai null mentre c'e' una partita in corso. */      

            if (partitaInCorso) {
                /* Prima di tutto, disegna un confine azzurro di 2 pixel attorno ai pezzi che possono essere mossi. */
                g.setColor(Color.cyan);
                for (int i = 0; i < mossePermesse.length; i++) {
                    g.drawRect(2 + mossePermesse[i].colonnaPartenza*61, 2 + mossePermesse[i].rigaPartenza*61, 60, 60);
                    g.drawRect(2 + mossePermesse[i].colonnaPartenza*61, 2 + mossePermesse[i].rigaPartenza*61, 60, 60);
                }
                /* Se un pezzo viene selezionato per il movimento (cioe' se rigaSelezionata >= 0),
                   allora disegna un confine bianco di 2 pixel attorno a quel pezzo e disegna confini verdi 
                   attorno ogni riquadro verso cui il pezzo puo' essere mosso. */
                if (rigaSelezionata >= 0) {
                    g.setColor(Color.white);
                    g.drawRect(2 + colonnaSelezionata*61, 2 + rigaSelezionata*61, 60, 60);
                    g.drawRect(2 + colonnaSelezionata*61, 3 + rigaSelezionata*61, 60, 60);
                    g.setColor(Color.green);
                    for (int i = 0; i < mossePermesse.length; i++) {
                        if (mossePermesse[i].colonnaPartenza == colonnaSelezionata && mossePermesse[i].rigaPartenza == rigaSelezionata) {
                            g.drawRect(2 + mossePermesse[i].colonnaDestinazione*61, 2 + mossePermesse[i].rigaDestinazione*61, 60, 60);
                            g.drawRect(3 + mossePermesse[i].colonnaDestinazione*61, 3 + mossePermesse[i].rigaDestinazione*61, 58, 58);
                        }
                    }
                }
            }

        }  // fine paintComponent()

    }  // fine class Damiera
