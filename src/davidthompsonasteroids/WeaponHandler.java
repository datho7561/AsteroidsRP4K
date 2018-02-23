/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import static davidthompsonasteroids.Spacecraft.MY_SHAPE;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author RealProgramming4Kids
 */
public class WeaponHandler {
    
    private Spacecraft ship;
    private ArrayList<Bullet> bullets;
    private ArrayList<Weapon> secondaryWeapons;
    private Weapon primaryWeapon;
    private Sound shootSound;
    
    public WeaponHandler(Spacecraft sc, Sound audio) {
        ship = sc;
        secondaryWeapons = new ArrayList();
        bullets = new ArrayList();
        shootSound = audio;
        primaryWeapon = new Weapon(bullets);
    }
    
    public WeaponHandler(WeaponHandler other) {
        // TODO: make a copy WeaponHandler
    }
    
    private static ArrayList<Bullet> copyBullets(ArrayList<Bullet> bl){
        
        ArrayList bullets = new ArrayList();
        
        for (Bullet b: bl){
            bullets.add(new Bullet(b));
        }
        return bullets;
    }
    
    public ArrayList<Bullet> getBullets() {
        return this.bullets;
    }
    
    public void fire() {

        if(primaryWeapon.ready()) {
            primaryWeapon.fire(ship.getFiringPort(0).x + ship.getX(), ship.getFiringPort(0).y + ship.getY(),
                    ship.getSpeed(), ship.getFacingAngle());
            shootSound.play();
        }
        
        
        for (Weapon w : secondaryWeapons) {
            
            
        }
        
        for (int i = 0; i < secondaryWeapons.size(); i++){
            if(secondaryWeapons.get(i).ready()) {
                
                secondaryWeapons.get(i).fire(ship.getFiringPort(i+1).x + ship.getX(),
                        ship.getFiringPort(i+1).x + ship.getY(),
                        ship.getSpeed(), ship.getFacingAngle());
                shootSound.play();
            }
        }
        
    }
    
    public void update() {
        
        primaryWeapon.update();
        
        for (Weapon w : secondaryWeapons) {
            w.update();
        }
        
        for (Bullet b : bullets){
            b.updatePosition();
        }
        
        Iterator<Bullet> bIter = bullets.iterator();
        while(bIter.hasNext()){
            if (!bIter.next().isActive()) {
                bIter.remove();
            }
        }
        
    }
    
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        for (Bullet b: bullets) {
            b.paint(g);
        }
    }
    
    public Bullet bulletCollision(Asteroid a) {
        for (Bullet b : bullets) {
            if (b.isActive() && b.collision(a)) {
                return b;
            }
        }
        return null;
    }
    
    public void setShotgun() {
        primaryWeapon = new ShotgunWeapon(bullets);
    }
    
    public void setMachineGun() {
        primaryWeapon = new Weapon(2, bullets);
    }
    
    public void setDefaultWeapon() {
        primaryWeapon = new Weapon(bullets);
    }
    
    public void addMissileWeapon() {
        addSecondaryWeapon(new MissileWeapon(bullets));
    }
    
    public void addRegularWeapon() {
        addSecondaryWeapon(new Weapon(bullets));
    }
    
    public void addSecondaryWeapon(Weapon w) {
        if (secondaryWeapons.size() < ship.numFiringPorts() - 1){
            secondaryWeapons.add(w);
        }
    }
    
}
