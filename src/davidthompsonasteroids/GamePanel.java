/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import javax.swing.JPanel;

/**
 *
 * @author D
 */
public class GamePanel extends JPanel {
    
    public GamePanel() {
        super();
        this.add(new Asteroids());
    }
    
}
