/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author RealProgramming4Kids
 */
public class MissileWeapon extends Weapon {
    
    private ArrayList<Missile> missiles;
    
    private static final int MISSILE_FIRE_RATE = 45;
    
    public MissileWeapon(ArrayList<Bullet> bulletList) {
        super(MISSILE_FIRE_RATE, bulletList);
        missiles = new ArrayList();
    }
    
    public void fire(double xpos, double ypos, double speed, double facingAngle) {
        
        Missile m = new Missile(xpos, ypos, speed, facingAngle);
        missiles.add(m);
        super.fire(m);
        
    }
    
    public void update() {
        super.update();
        
        //ArrayList<Missile> tempList = new ArrayList();
        
        Iterator<Missile> mIter = missiles.iterator();
        while(mIter.hasNext()) {
            Missile m = mIter.next();
            if (!m.isActive()){
                for(int i = 0; i < 50; i++) {
                    /*Missile qw = new Missile(m.getX(), m.getY(), m.getSpeed() * 2 * Math.random(), Math.random() * Math.PI * 2);
                    super.fire(qw);
                    tempList.add(qw);*/
                    super.fire(new Bullet(m.getX(), m.getY(), m.getSpeed() * 2 * Math.random(), Math.random() * Math.PI * 2, (int)(Math.random() * 10)));
                }
                mIter.remove();
            }
        }
        //missiles.addAll(tempList);
    }
    
}
