/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dama;

import Dama.View.Damiera;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Leonardo Rigo
 *
 *
 * Questo panel permette a due giocatori di giocare a dama uno contro l'altro.
 * Rosso comincia sempre la partita. Se un giocatore puo' effettuare una presa
 * verso un pezzo dell'avversario, allora il giocatore deve fare la mossa. 
 * Quando il giocatore non puo' fare piu' mosse, o vengono presi tutti i pezzi
 * di un giocatore, il gioco finisce mostrando chi ha vinto.
 * 
 * Questa classe ha un main() che permette di avviare il gioco come applicazione stand-alone
 */
public class DamaMain extends JPanel {

    /**
     * La classe rende possibile avviare il gioco come applicazione.
     * Apre una finestra che mostra un panel ;
     * Il programma termina quando l'utente chiude la finestra.
     */
    
    
    //------------------Dichiarazione attributi
    

    private final JButton nuovaPartitaButton;  // Button per cominciare nuova partita.
    
    private final JButton ritiroButton;   // Button che un giocatore puo'usare per finire 
                                    // la partita ritirandosi.

    private JLabel messaggio;  // Label per mostrare messaggi all'utente.
    

    /**
     * Il costruttore crea la Damiera (che in turno crea e gestisce
     * i buttons e la label messaggio), aggiunge tutti i componenti, ed imposta
     * i confini dei componenti.  (Questa e' l'unica cosa che viene fatta nella
     * classe DamaMain.)
     */
    public DamaMain() {
        
        Damiera damiera = new Damiera();  // Nota: Il costruttore della
                                    //   damiera crea anche i buttons
                                    //   e la label.
        add(damiera);
        //Inizializzazione pulsanti nel costruttore
        nuovaPartitaButton = new JButton("Nuova Partita");
        nuovaPartitaButton.setIcon(new ImageIcon("nuovaPartitaButton2.png"));
        nuovaPartitaButton.setBorder(null);
        nuovaPartitaButton.setContentAreaFilled(false);
        nuovaPartitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Chiamata al metodo nuovaPartita() della classe Damiera
            damiera.nuovaPartita();
        }
        }); // fine actionPerformed()
        // Imposta il font e il colore del testo
        ritiroButton = new JButton("");
        ritiroButton.setIcon(new ImageIcon("ritiroButton.png"));
        ritiroButton.setBorder(null);
        ritiroButton.setContentAreaFilled(false);
        ritiroButton.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                damiera.ritiro();
            }
        }); //fine actionPerformed()
        
        messaggio = new JLabel("");
        messaggio = new JLabel("",JLabel.CENTER);
        messaggio.setFont(new Font("Arial", Font.BOLD, 14));
        messaggio.setForeground(Color.black);
        messaggio.setBounds(495, 120, 310, 200);
        messaggio.setOpaque(true); // Abilita la proprietà di opacità per il colore di sfondo
        messaggio.setBackground(Color.white);
        setLayout(null);  // Layout impostato manualmente.
        setPreferredSize(new Dimension(800,600) );
        setBackground(new Color(0,150,0));  // Background verde scuro.

        /* aggiunge i componenti al panel. */
        add(nuovaPartitaButton);
        add(ritiroButton);
        add(messaggio);
        
        /* Imposta dimensione e posizione di ogni componente chiamando il
         metodo setBounds(). */

        damiera.setBounds(0,0,493,493); 
        nuovaPartitaButton.setBounds(467, 320, 150, 150);
        ritiroButton.setBounds(605, 310, 175, 150);
        
        // Passaggio dei riferimenti dei componenti alla classe Damiera
        damiera.impostaBottoniEdEtichetta(nuovaPartitaButton, ritiroButton, messaggio);

    } // fine metodo costruttore
         
    /**
    * Comincia nuova partita
    */
    public static void main(String[] args) {
        JFrame finestra = new JFrame("Dama - Rigo");
        ImageIcon icona = new ImageIcon("logoDama.png");
        finestra.setIconImage(icona.getImage());
        DamaMain contenuto = new DamaMain();
        finestra.setContentPane(contenuto); //inserisce nella finestra il panel
        finestra.pack(); //permette di dimensionare il contenuto della finestra in base alle preferenze
        Dimension dimensioneSchermo = Toolkit.getDefaultToolkit().getScreenSize(); //imposto dimensione schermo
        finestra.setLocation( (dimensioneSchermo.width - finestra.getWidth())/2, //definisce posizione iniziale della finestra
                (dimensioneSchermo.height - finestra.getHeight())/2 );
        finestra.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); //permette la chiusura della finestra
        finestra.setResizable(false);  //non si può ridimensionare a finestra
        finestra.setVisible(true); //necessario per vedere la finestra
    } //fine main()
} //fine classe DamaMain

