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
public class ShotgunWeapon extends Weapon {
    
    private static final double DEFAULT_SPRAY_CONE = Math.PI/4;
    private static final int DEFAULT_SHOTS = 10;
    
    private int numShots;
    private double coneSprayAngle;
    
    public ShotgunWeapon(ArrayList<Bullet> bulletList) {
        this(DEFAULT_SHOTS, DEFAULT_SPRAY_CONE, bulletList);
    }
    
    public ShotgunWeapon (int shots, double spreadAngle, ArrayList<Bullet> bulletList) {
        super(bulletList);
        numShots = shots;
        coneSprayAngle = spreadAngle;
    }
    
    public void fire(double xpos, double ypos, double speed, double facingAngle) {
                
        for (int i = 0; i < numShots; i++) {
            super.fire(xpos , ypos, speed, facingAngle - coneSprayAngle / 2 + Math.random() * coneSprayAngle);
        }

    }
    
}
