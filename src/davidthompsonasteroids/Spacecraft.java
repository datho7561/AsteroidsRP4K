/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import static davidthompsonasteroids.PowerupType.*;
import java.applet.AudioClip;
import java.awt.*;

/**
 *
 * @author RealProgramming4Kids
 */
public class Spacecraft extends PhysicsSprite {
    
    public static final int ROTATION = 4;
    public static final double ACCELERATION = 0.1;
    private static final double MAX_SPEED = 5;
    private static final int RESPAWN_TIME = 100;
    private static final int SHIP_EXPLOSION_SIZE = 20;
    private static final double SAFE_SPAWN_DISTANCE = 70;
    
    public static final int MAX_SHIELD_TIME = 250;
    
    private ExplosionHandler explosions;
    public WeaponHandler weapons;
    private PowerupEffectHandler powerups;
    
    private int spawnTimeCounter;
    private boolean hasMoved;
    private int shieldTimeRemaining;
    private boolean isShielded;
    private int extraSpeed;
    
    private Polygon firingPorts = new Polygon(new int[] {0, -5, 5, 10, 10}, new int[] {-20, -10, -10, 0, 0}, 5);
    
    public int lives;
    
    public static final Polygon MY_SHAPE = new Polygon(new int[] {0, -15, 15}, new int[] {-20, 10, 10}, 3);
    
    // Non-default spacecraft
    public Spacecraft(double xpos,double ypos, double speed, double angleFacing, ExplosionHandler exh, Sound ac){
        super(xpos, ypos, speed * Math.cos(angleFacing), speed * Math.sin(angleFacing), angleFacing, MY_SHAPE);
        
        weapons = new WeaponHandler(this, ac);
        hasMoved = false;
        explosions = exh;
        shieldTimeRemaining = MAX_SHIELD_TIME;
        isShielded = false;
        extraSpeed = 0;
        lives = 5;
        powerups = new PowerupEffectHandler();
    }
    
    public Spacecraft(ExplosionHandler exh, Sound ac) {
        this(450.0, 300.0, 0.0, 0.0, exh, ac);
    }
    
    // Default spacecraft
    public Spacecraft(Sound ac){
        this(new ExplosionHandler(), ac);
    }
    
    // Spacecraft copy
    public Spacecraft(Spacecraft sc){
        super(sc);
        
        hasMoved = sc.hasMoved;
        explosions = sc.explosions;
        shieldTimeRemaining = sc.shieldTimeRemaining;
        isShielded = sc.isShielded;
        extraSpeed = sc.extraSpeed;
        lives = sc.lives;
        
        weapons = new WeaponHandler(sc.weapons);
    }
    
    
    
    public void paint(Graphics g) {
        Color ogcolor = g.getColor();
        if (!hasMoved || isShielded){
            g.setColor(Color.CYAN);
        }
        
        if (isActive() || spawnTimeCounter < RESPAWN_TIME/4) {
            super.paint(g);
        }
        
        weapons.paint(g);

        g.setColor(ogcolor);
        
    }
    
    public void updatePosition() {
        
        if (lives <= 0){
            super.kill();
        }
        
        super.updatePosition();
        
        powerups.updatePowerups();
        
        if (!isActive() && lives > 0){
            spawnTimeCounter ++;
            if (spawnTimeCounter < RESPAWN_TIME/2 && spawnTimeCounter % (1+(int)(Math.random()*10)) == 0){
                explosions.addExplosion(this.getX() - SHIP_EXPLOSION_SIZE / 2 + Math.random()*SHIP_EXPLOSION_SIZE,
                        this.getY() - SHIP_EXPLOSION_SIZE / 2 + Math.random()*SHIP_EXPLOSION_SIZE);
            }
        }
        
        // Shield
        if (isShielded){
            if (hasMoved) {
                shieldTimeRemaining--;
                if (shieldTimeRemaining == 0) {
                    isShielded = false;
                }
            }
        }
        
        weapons.update();
        
    }
    
    public void accelerate(double rate) {
        hasMoved = true;
        /*
        if (this.getSpeed() + rate > MAX_SPEED) {
            super.accelerate(MAX_SPEED - this.getSpeed());
        } else if (this.getSpeed() + rate < 0) {
            super.accelerate(-this.getSpeed());
        }else {*/
        double newRate = rate;
        for (int i = 0; i < extraSpeed; i++) {
            newRate *= 1.5;
        }
            super.accelerate(newRate);
        //}
    }
    
    public void fire() {
        Point p = this.rotatePoint(new Point(MY_SHAPE.xpoints[0],MY_SHAPE.ypoints[0]));
        weapons.fire();
        
        hasMoved = true;
    }

    public boolean kill(){
        if (isActive() && hasMoved && !isShielded) {
            super.kill();
            explosions.addExplosion(this.getX(), this.getY());
            spawnTimeCounter = 0;
            return true;
        }
        return false;
    }
    
    public boolean respawn(){
        
        if (!isActive() && spawnTimeCounter > RESPAWN_TIME) {
            set(450.0, 300.0, 0.0, 0.0, 0.0);
            shieldTimeRemaining = MAX_SHIELD_TIME;
            hasMoved = false;
            lives --;
            return true;
        }
        return false;
    }
    
    public void rotate(int degrees) {
        super.rotate(degrees);
        hasMoved = true;
    }
    
    public double getFacingAngle() {
        return super.getFacingAngle() - Math.PI / 2;
    }
    
    public int getShieldTime() {
        return shieldTimeRemaining;
    }
    
    public boolean hasSlowPowerup() {
        return powerups.hasPowerup(SLOW);
    }
    
    public boolean isShielded() {
        return isShielded;
    }
    
    public void setShield(boolean shieldOn) {
        if (isActive()){
            if (shieldOn) {
                hasMoved = true;
                if (shieldTimeRemaining > 0){
                    isShielded = true;
                }
            } else {
                isShielded = false;
            }
        }
    }
    
    public void applyPowerup(PowerupType pt) {
        powerups.applyPowerup(pt);
    }
    
    public Point getFiringPort(int portNum) {
        
        return rotatePoint(new Point(firingPorts.xpoints[portNum], firingPorts.ypoints[portNum]));
        
    }
    
    public int numFiringPorts() {
        return firingPorts.npoints;
    }
    
    public boolean safeToSpawnAsteroid(double asterX, double asterY) {
        return SAFE_SPAWN_DISTANCE < Math.sqrt(Math.pow((asterX - getX()),2) + Math.pow((asterY - getY()),2));
    }
    
    private class PowerupEffectHandler {
        
        private static final int MAX_POWERUP_TIME = 350;
        
        private int[] powerupTimeRemaining;
        private boolean[] isActivePowerups;
        
        public PowerupEffectHandler() {
            powerupTimeRemaining = new int[PowerupType.values().length];
            isActivePowerups = new boolean[PowerupType.values().length];
            for(Boolean b: isActivePowerups){
                b = false;
            }
        }
        
        public void applyPowerup(PowerupType pt) {
            
            powerupTimeRemaining[pt.ordinal()] = MAX_POWERUP_TIME;
            isActivePowerups[pt.ordinal()] = true;
            
            switch(pt){
            case SHIELD:
                shieldTimeRemaining += 100;
                if (shieldTimeRemaining > MAX_SHIELD_TIME) {
                    shieldTimeRemaining = MAX_SHIELD_TIME;
                }
            break;
            case FAST:
                extraSpeed ++;
                break;
            case EXTRA_LIFE:
                lives ++;
                break;
            case FAST_FIRING:
                weapons.setMachineGun();
                break;
            case CONE_WEAPON:
                weapons.setShotgun();
                break;
            case TELEPORT:
                setPosition(Math.random() * 900, Math.random() * 600);
                break;
            case ADD_MISSILE:
                weapons.addMissileWeapon();
            case ADD_LASER:
                weapons.addRegularWeapon();
            default:
                break;
                
            }
        }
        
        public void updatePowerups() {
            for(int i = 0; i < powerupTimeRemaining.length; i++) {
                if (isActivePowerups[i]){
                    powerupTimeRemaining[i]--;
                    if (powerupTimeRemaining[i] == 0){
                        removePowerup(PowerupType.values()[i]);
                    }
                }
            }
        }
        
        public void removePowerup(PowerupType pt) {
            isActivePowerups[pt.ordinal()] = false;
            powerupTimeRemaining[pt.ordinal()] = 0;
            
            switch(pt){
            case FAST:
                extraSpeed = 0;
                break;
            case FAST_FIRING:
                weapons.setDefaultWeapon();
                break;
            case CONE_WEAPON:
                weapons.setDefaultWeapon();
                break;
            default:
                break;
            }
        }
        
        public boolean hasPowerup(PowerupType pt) {
            return isActivePowerups[pt.ordinal()];
        }
        
    }
    
}
