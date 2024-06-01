/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scacchi.View;

/**
 *
 * @author Leonardo Rigo
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Casella extends JButton {
    private final int rig;
    private final int col;

    public Casella(int rig, int col) {
        this.rig = rig;
        this.col = col;
        inizializzaButton();
    } //fine metodo costruttore

    private void inizializzaButton() {
        setPreferredSize(new Dimension(40, 40));

        if ((rig + col) % 2 == 0) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(new Color(205, 133, 63));
        }
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setFont(new Font("Serif", Font.BOLD, 36));
    } // fine inizializzaButton()

    public void setSimboloPezzo(String simbolo, Color colore) {
        this.setText(simbolo);
        this.setForeground(colore);
    } //fine setSimboloPezzo()

    public void azzeraSimboloPezzo() {
        this.setText("");
    } //fine azzeraSimboloPezzo()
} //fine classe Casella
