/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import java.util.ArrayList;

/**
 *
 * @author RealProgramming4Kids
 */
public class Weapon {
    
    private ArrayList<Bullet> bullets;
    
    private static final int DEFAULT_FIRE_DELAY = 20;
    
    private int fireCounter;
    private int fireDelay;
    
    public Weapon(int delay, ArrayList<Bullet> bulletList) {
        fireDelay = delay;
        fireCounter = 0;
        bullets = bulletList;
    }
    public Weapon(ArrayList<Bullet> bulletList) {
        this(DEFAULT_FIRE_DELAY, bulletList);
    }
    
    public void fire(double xpos, double ypos, double speed, double facingAngle) {
         
        fire(new Bullet(xpos, ypos, speed, facingAngle));
        
    }
    
    public void fire(Bullet b) {
         
        bullets.add(b);

        fireCounter = 0;
    }
    
    public boolean ready() {
        return fireCounter > fireDelay;
    }
    
    public void update() {
        fireCounter ++;
    }
    
}
