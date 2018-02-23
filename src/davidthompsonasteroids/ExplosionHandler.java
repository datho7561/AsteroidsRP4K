/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

/**
 *
 * @author RealProgramming4Kids
 */
public class ExplosionHandler {
    
    private ArrayList<Debris> explosionDebris;
    
    private static final int MAX_DEBRIS = 90;
    private static final int MIN_DEBRIS = 40;
    
    public ExplosionHandler(){
        explosionDebris = new ArrayList();
    }
    
    public void addExplosion() {
        this.addExplosion(Math.random() * 900, Math.random() * 600);
    }
    
    public void addExplosion(double x, double y)
    {
        int debrisQty = (int)(Math.random()*(MAX_DEBRIS - MIN_DEBRIS)) + MIN_DEBRIS;
        for (int i = 0; i < debrisQty; i++) {
            explosionDebris.add(new Debris(x, y));
        }
    }
    
    public void addExplosion(double x, double y, int minDebris, int maxDebris)
    {
        int debrisQty = (int)(Math.random()*(maxDebris - minDebris)) + minDebris;
        for (int i = 0; i < debrisQty; i++) {
            explosionDebris.add(new Debris(x, y));
        }
    }
    
    public void updateDebris() {
        for (Debris d : explosionDebris) {
            d.updatePosition();
        }
        killDebris();
    }
    
    public void drawDebris(Graphics g) {
        Color oldc = g.getColor();
        for (Debris d : explosionDebris) {
            d.paint(g);
        }
        g.setColor(oldc);
    }
    
    private void killDebris() {
        Iterator<Debris> dIter = explosionDebris.iterator();
        
        while(dIter.hasNext()){
            if (!dIter.next().isActive()) {
                dIter.remove();
            }
        }
    }
    
}
