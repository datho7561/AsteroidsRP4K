/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 *
 * @author RealProgramming4Kids
 */
public class Debris extends Bullet {
    
    public static final int ROTATION = (int) (5 * Math.random() + 1);
    public static final double MAX_ASTEROID_SPEED = 5;
    
    public static final int D_MAX_LIFETIME = 50;
    
    public static final Polygon MY_SHAPE = new Polygon(new int[] {1,1,-1,-1}, new int[] {1,-1,1,-1}, 4);
    
    private Color c;
    
    private static final Color[] colours = {Color.red, Color.yellow, Color.orange};
    
    public Debris (double xpos, double ypos) {
        this (makeDebris(xpos, ypos));
    }
    
    public Debris (Debris d) {
        super (d);
        c = colours[(int)(Math.random()*3)];
    }
    
    public Debris (int life, double xpos, double ypos, double xSpeed, double ySpeed, double angle) {
        super(life, xpos, ypos, xSpeed, ySpeed, angle, MY_SHAPE);
        c = colours[(int)(Math.random()*3)];
    }
    
    private static Debris makeDebris (double xpos, double ypos){
        double speed = Math.random() * MAX_ASTEROID_SPEED;
        double angle = Math.random() * 2 * Math.PI;
        int life = (int)(Math.random() * D_MAX_LIFETIME);
        
        return new Debris(life, xpos, ypos,  speed * Math.cos(angle - Math.PI / 2), speed * Math.sin(angle - Math.PI / 2), angle);
    }
    
    public void updatePosition() {
        super.updatePosition();
        this.rotate(ROTATION);
    }
    
    public void paint(Graphics g) {
        g.setColor(c);
        super.paint(g);
    }

}
