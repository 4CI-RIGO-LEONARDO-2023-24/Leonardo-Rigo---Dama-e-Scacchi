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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Scacchiera extends JPanel implements ActionListener, MouseListener {
    private final Pezzo[][] scacchiera;

    public Scacchiera() {
        this.scacchiera = new Pezzo[8][8]; // La scacchiera ha dimensioni 8x8
        setBackground(Color.BLACK);
        addMouseListener(this);
        JButton ritiroButton = new JButton("Ritiro");
        ritiroButton.addActionListener(this);
        inserimentoPezzi();
    } //fine metodo costruttore

    public Pezzo[][] getScacchiera() {
        return scacchiera;
    } //fine getScacchiera()

    public Pezzo getPezzo(int rig, int colonna) {
        return scacchiera[rig][colonna];
    } //fine getPezzo()

    public void setPezzo(int rig, int colonna, Pezzo pezzo) {
        scacchiera[rig][colonna] = pezzo;
        if (pezzo != null) {
            pezzo.setPosizione(new Posizione(rig, colonna));
        }
    } //fine setPezzo()

    private void inserimentoPezzi() {
        // Inserimento Torri
        scacchiera[0][0] = new Torre(ColorePezzo.BLACK, new Posizione(0, 0));
        scacchiera[0][7] = new Torre(ColorePezzo.BLACK, new Posizione(0, 7));
        scacchiera[7][0] = new Torre(ColorePezzo.WHITE, new Posizione(7, 0));
        scacchiera[7][7] = new Torre(ColorePezzo.WHITE, new Posizione(7, 7));
        // Inserimento Cavalli
        scacchiera[0][1] = new Cavallo(ColorePezzo.BLACK, new Posizione(0, 1));
        scacchiera[0][6] = new Cavallo(ColorePezzo.BLACK, new Posizione(0, 6));
        scacchiera[7][1] = new Cavallo(ColorePezzo.WHITE, new Posizione(7, 1));
        scacchiera[7][6] = new Cavallo(ColorePezzo.WHITE, new Posizione(7, 6));
        // Inserimento Alfieri
        scacchiera[0][2] = new Alfiere(ColorePezzo.BLACK, new Posizione(0, 2));
        scacchiera[0][5] = new Alfiere(ColorePezzo.BLACK, new Posizione(0, 5));
        scacchiera[7][2] = new Alfiere(ColorePezzo.WHITE, new Posizione(7, 2));
        scacchiera[7][5] = new Alfiere(ColorePezzo.WHITE, new Posizione(7, 5));
        // Inserimento Regine
        scacchiera[0][3] = new Regina(ColorePezzo.BLACK, new Posizione(0, 3));
        scacchiera[7][3] = new Regina(ColorePezzo.WHITE, new Posizione(7, 3));
        // Inserimento Re
        scacchiera[0][4] = new Re(ColorePezzo.BLACK, new Posizione(0, 4));
        scacchiera[7][4] = new Re(ColorePezzo.WHITE, new Posizione(7, 4));
        // Inserimento Pedoni
        for (int i = 0; i < 8; i++) {
            scacchiera[1][i] = new Pedone(ColorePezzo.BLACK, new Posizione(1, i));
            scacchiera[6][i] = new Pedone(ColorePezzo.WHITE, new Posizione(6, i));
        }
    } //fine inserimentoPezzi()

    public void muoviPezzo(Posizione inizio, Posizione fine) {
        if (scacchiera[inizio.getRig()][inizio.getColonna()] != null &&
                scacchiera[inizio.getRig()][inizio.getColonna()].eMossaValida(fine, scacchiera)) {

            scacchiera[fine.getRig()][fine.getColonna()] = scacchiera[inizio.getRig()][inizio.getColonna()];
            scacchiera[fine.getRig()][fine.getColonna()].setPosizione(fine);
            scacchiera[inizio.getRig()][inizio.getColonna()] = null;
        }
    } //fine muoviPezzo()

      @Override
      public void actionPerformed(ActionEvent e) {
          throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
      }

      @Override
      public void mouseClicked(MouseEvent e) {
          throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
      }

      @Override
      public void mousePressed(MouseEvent e) {
          throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
      }

      @Override
      public void mouseReleased(MouseEvent e) {
          throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
      }

      @Override
      public void mouseEntered(MouseEvent e) {
          throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
      }

      @Override
      public void mouseExited(MouseEvent e) {
          throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
      }
} //fine classe Scacchiera
