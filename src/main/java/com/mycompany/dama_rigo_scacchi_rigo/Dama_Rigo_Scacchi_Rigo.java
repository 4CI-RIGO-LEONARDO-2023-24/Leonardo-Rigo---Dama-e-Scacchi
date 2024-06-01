/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dama_rigo_scacchi_rigo;
import Dama.DamaMain;
import Scacchi.View.ScacchiGUI;
import javax.swing.*;
//I seguenti import servono per la gestione del file csv
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Leonardo Rigo
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Dama_Rigo_Scacchi_Rigo extends JFrame {
    private final JButton damaButton;
    private final JButton scacchiButton;
    private final JButton accessoCSVButton;

    public Dama_Rigo_Scacchi_Rigo() {
        super("Rigo - Schermata selezione giochi");
        
        // Impostazioni della finestra
        ImageIcon icona = new ImageIcon("logoDama.png");
        this.setIconImage(icona.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 300);
        setLocationRelativeTo(null); // Posiziona la finestra al centro dello schermo
        setResizable(false); //non voglio che la finestra sia ridimensionabile

        
        // Creazione di un pannello con un colore verde opacizzato
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 150, 0, 128)); // 128 corrisponde a metà dell'opacità massima (255)
                g.fillRect(0, 0, getWidth(), getHeight());
            } //fine paintComponent()
        };
        
        // Creazione dei bottoni
        damaButton = new JButton("");
        scacchiButton = new JButton("");
        accessoCSVButton = new JButton("");
        
        //Aggiunta sfondo colorato ai bottoni
        damaButton.setIcon(new ImageIcon("logoDama.png"));
        damaButton.setBorder(null);
        damaButton.setContentAreaFilled(false);
        damaButton.setPressedIcon(new ImageIcon("logoDamaAvvio.png"));

        scacchiButton.setIcon(new ImageIcon("logoScacchi.png"));
        scacchiButton.setBorder(null);
        scacchiButton.setContentAreaFilled(false);
        scacchiButton.setPressedIcon(new ImageIcon("logoScacchiAvvio.png"));

        accessoCSVButton.setIcon(new ImageIcon("apriCSV.png"));
        accessoCSVButton.setBorder(null);
        accessoCSVButton.setContentAreaFilled(false);
        accessoCSVButton.setPressedIcon(new ImageIcon("apriCSV.png"));

        // Aggiunta dei bottoni al pannello
        panel.add(damaButton);
        panel.add(scacchiButton);
        panel.add(accessoCSVButton);
        
        
        // Aggiunta della scritta "Scegli gioco"
        JLabel titleLabel = new JLabel("Scegli il gioco che preferisci oppure consulta il file CSV");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        panel.add(titleLabel);
        
        // Aggiunta del pannello alla finestra
        add(panel);

        // Aggiunta degli ActionListener ai bottoni
        damaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            // Avvia il gioco della Dama
            DamaMain.main(new String[]{});
        }});

        scacchiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            // Avvia il gioco degli Scacchi
            ScacchiGUI.main(new String[]{});
        }});
        
        accessoCSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File fileCSV = new File("trace.csv");
                    if (fileCSV.exists()) {
                        Desktop.getDesktop().open(fileCSV);
                    } else {
                        JOptionPane.showMessageDialog(null, "File CSV non trovato.");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Errore durante l'apertura del file CSV: " + ex.getMessage());
                }
            }
        });
    } // fine metodo costruttore

    public static void salvaDatiPartita(String messaggio) { //metodo per salvare informazioni sulle partite in un file CSV
        String nomePC = ottieniNomePC();
        String sistemaOperativo = ottieniSistemaOperativo();
        String utente = ottieniUtente();
        String timestamp = ottieniTimeStamp();
        String timestampFormattato = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        String[] datiPartita = {timestamp, timestampFormattato, nomePC, sistemaOperativo, utente, messaggio};
        String nomeFile = "trace.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile, true))) {
            writer.println(String.join(",", datiPartita));
        } catch (IOException e) {
        }
    }
    
    public static String ottieniTimeStamp(){
            long timestamp = System.currentTimeMillis();
            return String.valueOf(timestamp);
    }

    public static String ottieniNomePC() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Nome PC non disponibile";
        }
    }

    public static String ottieniSistemaOperativo() {
        return System.getProperty("os.name") + " " + System.getProperty("os.version");
    }

    public static String ottieniUtente() {
        return System.getProperty("user.name");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dama_Rigo_Scacchi_Rigo selezioneGUI = new Dama_Rigo_Scacchi_Rigo();
            selezioneGUI.setVisible(true); // Mostra la finestra di selezione
        });
    } //fine main()
} //fine classe Dama_Rigo_Scacchi_Rigo


