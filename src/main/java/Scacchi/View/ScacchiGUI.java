/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scacchi.View;

/**
 *
 * @author Leonardo Rigo
 */

import Scacchi.Model.*;
import Scacchi.Control.*;
import static com.mycompany.dama_rigo_scacchi_rigo.Dama_Rigo_Scacchi_Rigo.salvaDatiPartita;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class ScacchiGUI extends JFrame implements ActionListener {
    private final Casella[][] caselle = new Casella[8][8];
    private final Scacchi partita = new Scacchi();
    private final JPanel griglia; // JPanel per contenere il GridLayout
    private final JPanel grigliaLaterale; // JPanel per l'area laterale
    private final JButton nuovaPartitaButton;
    private final JButton ritiroButton;
    private final JLabel messaggi;
    boolean partitaInCorso;

    public ScacchiGUI() {
        setTitle("Scacchi - Rigo");
        ImageIcon icona = new ImageIcon("logoScacchi.png");
        this.setIconImage(icona.getImage());
        salvaDatiPartita("Partita a Scacchi cominciata");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        partitaInCorso = true;

        // Creazione del JPanel principale con BorderLayout
        JPanel principalePanel = new JPanel();
        principalePanel.setLayout(new BoxLayout(principalePanel, BoxLayout.X_AXIS)); //disposti orizzontalmente

        griglia = new JPanel(new GridLayout(8, 8));
        griglia.setBackground(new Color(0, 150, 0)); // Imposta il colore dello sfondo
        principalePanel.add(griglia);

        // Creazione dell'area laterale con un layout appropriato
        grigliaLaterale = new JPanel(); // GridLayout con una colonna variabile
        grigliaLaterale.setLayout(new BoxLayout(grigliaLaterale, BoxLayout.Y_AXIS)); //disposti verticalmente
        //fonte:https://www.geeksforgeeks.org/java-awt-boxlayout-class/

        grigliaLaterale.setBackground(new Color(0, 150, 0)); // Imposta lo sfondo del sidePanel


        // Aggiunto uno spazio vuoto per centrare i pulsanti verticalmente
        grigliaLaterale.add(Box.createVerticalGlue());


        // Aggiunta dei pulsanti e dell'area di testo all'area laterale
        nuovaPartitaButton = new JButton("");
        nuovaPartitaButton.addActionListener(this);
        nuovaPartitaButton.setIcon(new ImageIcon("nuovaPartitaButton2.png"));
        nuovaPartitaButton.setBorder(null);
        nuovaPartitaButton.setContentAreaFilled(false);
        
        ritiroButton = new JButton("");
        ritiroButton.addActionListener(this);
        ritiroButton.setIcon(new ImageIcon("ritiroButton.png"));
        ritiroButton.setBorder(null);
        ritiroButton.setContentAreaFilled(false);
        
        messaggi = new JLabel(""); // Impostata l'area di testo
        messaggi.setFont(new Font("Arial", Font.BOLD, 14));
        messaggi.setForeground(Color.black);
        JScrollPane riquadroScorrimento = new JScrollPane(messaggi);
        riquadroScorrimento.setForeground(new Color(0,150,0));

        grigliaLaterale.add(riquadroScorrimento); // JTextArea posizionata

        grigliaLaterale.add(nuovaPartitaButton, BorderLayout.NORTH); // Pulsanti in alto
        grigliaLaterale.add(ritiroButton, BorderLayout.NORTH); // Pulsanti al centro
        
        // Impostato il colore di sfondo per l'area dei pulsanti e dell'area di testo
        JPanel pulsantiPanel = new JPanel();
        pulsantiPanel.setBackground(new Color(0, 150, 0));
        pulsantiPanel.add(nuovaPartitaButton);
        pulsantiPanel.add(ritiroButton);

        grigliaLaterale.add(pulsantiPanel);

        principalePanel.add(grigliaLaterale);

        // Aggiunto il JPanel principale al JFrame
        add(principalePanel);
        inizializzaScacchiera();
        setResizable(true); // Impostata la finestra come ridimensionabile

        pack();
        setVisible(true);
    } //fine metodo costruttore

    private void inizializzaScacchiera() {
        for (int rig = 0; rig < caselle.length; rig++) {
            for (int col = 0; col < caselle[rig].length; col++) {
                final int rigFinale = rig;
                final int colFinale = col;
                Casella casella = new Casella(rig, col);
                casella.setPreferredSize(new Dimension(50, 50)); // Dimensione fissa per il riquadro
                casella.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (partitaInCorso == false){
                          messaggi.setText("<html>" + "Premi \"Nuova Partita\" <br>per cominciare <br>una nuova partita." + "<html>");
                        }
                        else{
                        gestisciSelezioneRiquadri(rigFinale, colFinale);
                    }}
                });
                 // Modificato qui il colore di sfondo del riquadro
                casella.setBackground((rig + col) % 2 == 0 ? Color.LIGHT_GRAY : new Color(136,132,132));
                griglia.add(casella);
                caselle[rig][col] = casella;
            }
        }
        // Questo serve per inizializzare le immagini delle caselle per risolvere un problema che ho incontrato
        Casella casella = caselle[5][5];
        casella.dispatchEvent(new MouseEvent(casella, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 0, 0, 1, false));
        //simulo una pressione di una casella della matrice
        aggiornaScacchiera();
    } //fine inizializzaScacchiera()

    private void aggiornaScacchiera() {
        if (partitaInCorso == false){
            messaggi.setText("Premi \"Nuova Partita\" per cominciare una nuova partita.");
        }
        else{
        Scacchiera scacchiera = partita.getScacchiera();
        for (int rig = 0; rig < 8; rig++) {
            for (int col = 0; col < 8; col++) {
                Pezzo pezzo = scacchiera.getPezzo(rig, col);
                if (pezzo != null) {
                    // Se vengono usati i simboli Unicode:
                    String simbolo = pezzo.getSimboloPezzo();
                    Color colore = (pezzo.getColore() == ColorePezzo.WHITE) ? Color.WHITE : Color.BLACK;
                    caselle[rig][col].setSimboloPezzo(simbolo, colore);
                } else {
                    caselle[rig][col].azzeraSimboloPezzo();
                }
            }
        }
        // Aggiorna la JLabel con il turno del giocatore corrente
        ColorePezzo coloreGiocatoreCorrente = partita.getColoreGiocatoreCorrente();
        String messaggioGiocatoreCorrente = (coloreGiocatoreCorrente == ColorePezzo.WHITE) ? "<html> Tocca al giocatore bianco.<br> Fai la tua mossa.</html>" : "<html>Tocca al giocatore nero.<br> Fai la tua mossa</html/>";
        messaggi.setText(messaggioGiocatoreCorrente);
    }} //fine aggiornaScacchiera()

    private void gestisciSelezioneRiquadri(int rig, int col) {
        if (!partitaInCorso) {
            messaggi.setText("Premi \"Nuova Partita\" per cominciare una nuova partita.");
            return; // Esce dal metodo se la partita non è in corso
        }
        boolean risultatoMossa = partita.gestisciSelezioneCasella(rig, col);
        togliEvidenziature(); // Rimuovi le evidenziazioni precedenti
        if (risultatoMossa) {
            aggiornaScacchiera();
            controllaStatoPartita();
            controlloFinePartita();
        } else if (partita.pezzoSelezionato()) {
            // Evidenzia le mosse valide per il pezzo selezionato
            sottolineaMossePermesse(new Posizione(rig, col));
            sottolineaMossePermesse(new Posizione(partita.getRigaPezzoSelezionato(), partita.getColonnaPezzoSelezionato()));
        }
        aggiornaScacchiera();
    } //fine gestisciSelezioneRiquadri()


    private void controllaStatoPartita() {
        ColorePezzo giocatoreCorrente = partita.getColoreGiocatoreCorrente();
        boolean sottoScacco = partita.eSottoScacco(giocatoreCorrente);

        if (sottoScacco) {
            String var =  (giocatoreCorrente.toString() + " e' sotto scacco !");
            messaggi.setText(var);
        }
    } //fine controllaStatoPartita()

    private void sottolineaMossePermesse(Posizione posizione) {
        ArrayList<Posizione> mossePermesse = partita.getMossePermessePerPezzoA(posizione);
        for (Posizione mossa : mossePermesse) {
            Casella casella;
            casella = caselle[mossa.getRig()][mossa.getColonna()];
            casella.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3)); // Imposta il bordo verde
        }
    } //fine sottolineaMossePermesse()


    private void togliEvidenziature() {
        for (int rig = 0; rig < 8; rig++) {
            for (int col = 0; col < 8; col++) {
                Casella casella = caselle[rig][col];
                casella.setBorder(null); // Rimuovi il bordo
                caselle[rig][col].setBackground((rig + col) % 2 == 0 ? Color.LIGHT_GRAY : new Color(136, 132, 132));
            }
        }
    } //fine togliEvidenziature()

    private void resetGioco() {
        ritiroButton.setEnabled(true);
        partitaInCorso = true;
        partita.resetGioco();
        aggiornaScacchiera();
    } //fine resetGioco()

    private void controlloFinePartita() {
        if (partita.eScaccoMatto(partita.getColoreGiocatoreCorrente())) {
            partitaInCorso = false;
            ritiroButton.setEnabled(false);
            messaggi.setText("Scacco matto ! Vuoi giocare ancora ? Per farlo, premi il pulsante nuova partita");
            salvaDatiPartita("Scacco matto, fine partita di Scacchi");
        }
    } //fine controlloFinePartita()

    private void gameOverRitiro() {
      //if(partitaInCorso){
          if ((partita.getColoreGiocatoreCorrente()) == ColorePezzo.WHITE) {
              messaggi.setText("BIANCO si ritira.");
              salvaDatiPartita("BIANCO si ritira. NERO vince la partita di Scacchi");
              ritiroButton.setEnabled(false);
              partitaInCorso = false;
          }
          else if ((partita.getColoreGiocatoreCorrente()) == ColorePezzo.BLACK) {
              messaggi.setText("NERO si ritira.");
              salvaDatiPartita("NERO si ritira. BIANCO vince la partita di Scacchi");
              ritiroButton.setEnabled(false);
              partitaInCorso = false;
          }
    } //fine gameOverRitiro()
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        Object src = evt.getSource();
        if (src == nuovaPartitaButton)
            resetGioco();
        else if (src == ritiroButton)
                gameOverRitiro();
    } //fine actionPerformed()
    
  public static void main(String[] args) {
      SwingUtilities.invokeLater(ScacchiGUI::new);
  } //fine main()
} //fine classe ScacchiGUI