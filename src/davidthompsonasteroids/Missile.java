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
public class Missile extends Bullet{
    
    private static final int MISSILE_LIFETIME = 200;
    private static final Polygon MY_SHAPE = new Polygon(new int[] {-2, 2, 2, -2}, new int[] {-1, -1, 1, 1}, 4);
    
    public Missile (double xpos, double ypos, double rSpeed, double angleRad){
        super(xpos, ypos, rSpeed, angleRad, MY_SHAPE, MISSILE_LIFETIME);
    }
    
    public void paint(Graphics g) {
        Color oldc = g.getColor();
        g.setColor(Color.BLUE);
        super.paint(g);
        g.setColor(oldc);
    }
    
}
