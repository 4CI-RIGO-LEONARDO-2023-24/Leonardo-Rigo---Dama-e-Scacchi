/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dama.Model;

/**
 *
 * @author Leonardo Rigo
 *
     * Un oggetto DamaMosse rappresenta una mossa nel gioco della Dama.
     * Contiene la riga e la colonna del riquadro da cui viene mosso il pezzo
     * e la riga e la colonna del riquadro in cui va mosso il pezzo stesso.
     * (Questa classe non garantisce che la mossa sia permessa.)    
     */
    public class DamaMosse {
        public int rigaPartenza;  // Riga di partenza del pezzo da muovere.
        public int colonnaPartenza;  // Colonna di partenza del pezzo da muovere.
        public int rigaDestinazione;  // Riga di arrivo del pezzo da muovere.
        public int colonnaDestinazione; // Colonna di arrivo del pezzo da muovere.
        
        public DamaMosse(int r1, int c1, int r2, int c2) {
            // Costruttore. Imposta solo i valori delle variabili d'istanza.
            rigaPartenza = r1;
            colonnaPartenza = c1;
            rigaDestinazione = r2;
            colonnaDestinazione = c2;
        } //fine damaMosse()
        
        public boolean ePresa() {
                // Controlla se questa mossa sia o meno una presa. Si assume che
                // la mossa sia permessa. In una presa, il pezzo si muove di due righe
                //  (In una normale mossa, si muove solo di una riga.)
            return (rigaPartenza - rigaDestinazione == 2 || rigaPartenza - rigaDestinazione == -2);
        } //fine ePresa()
        
    }  // fine classe DamaMosse.
