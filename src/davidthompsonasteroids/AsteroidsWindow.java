/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author D
 */
public class AsteroidsWindow extends JFrame implements KeyListener {

    static AsteroidsWindow aw;
    static Asteroids asg;
    
    public static void main(String[] args) {
        aw = new AsteroidsWindow();
        asg = new Asteroids();
        
        aw.add(asg);
        aw.setSize(asg.getSize());
        
        aw.setVisible(true);
    }
    
    public AsteroidsWindow() {
        super("Asteroids");
        
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(this);
        
        
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        asg.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        asg.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        asg.keyReleased(e);
    }
    
}
