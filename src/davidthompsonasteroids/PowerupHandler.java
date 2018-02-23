/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author RealProgramming4Kids
 */
public class PowerupHandler {
    
    private static final Polygon POWERUP_SHAPE = new Polygon(new int[] {16, 8, -8, -16, -8, 8}, new int[] {0, 14, 14, 0, -14, -14}, 6);
    private static final double BASE_CHANCE = .10;
    
    private String[] powerupLetters;
    
    private PowerupType[] powerupTypes;
    private double chance;
    private ArrayList<Powerup> powerups;
    
    public PowerupHandler() {
        this(BASE_CHANCE);
    }
    
    public PowerupHandler(double spawnChance) {
        chance = spawnChance;
        powerups = new ArrayList();
        powerupTypes = new PowerupType[] {PowerupType.SHIELD, PowerupType.SLOW,
            PowerupType.FAST, PowerupType.EXTRA_LIFE, PowerupType.FAST_FIRING,
            PowerupType.CONE_WEAPON, PowerupType.TELEPORT, PowerupType.ADD_MISSILE,
            PowerupType.ADD_LASER};
        powerupLetters = new String[] {"+S", "<<", ">>", "+L", "MG", "SG", "??", "+M", "+L"};
        
    }
    
    public void spawnPowerup(double xpos, double ypos, boolean shouldSpawn) {
        if (shouldSpawn){
            spawnPowerup(xpos, ypos);
        }
    }
    
    public void spawnPowerup(double xpos, double ypos) {
        int ptNum = (int)(Math.random() * PowerupType.values().length);
        
        
        Powerup spawnedPowerup = new Powerup(powerupTypes[ptNum], xpos, ypos, powerupLetters[ptNum]);
        spawnedPowerup.updatePosition();
        powerups.add(spawnedPowerup);
    }
    
    public boolean roll() {
        return Math.random() < chance;
    }
    
    public void paint(Graphics g) {
        Color oldc = g.getColor();
        g.setColor(Color.YELLOW);
        for(Powerup p: powerups){
            
            p.paint(g);
        }
        g.setColor(oldc);
    }
    
    public void checkCollisions(Spacecraft sc) {
        
        for (Powerup pw : powerups) {
            
            if (sc.isActive() && sc.collision(pw)) {
                pw.isActive = false;
                sc.applyPowerup(pw.type);
            }
            pw.update();
        }
        refreshList();
        
    }
    
    private void refreshList() {
        Iterator<Powerup> pwIter = powerups.iterator();
        
        while(pwIter.hasNext()){
            if (!pwIter.next().isActive) {
                pwIter.remove();
            }
        }
    }
    
    
    
    private class Powerup extends VectorSprite {
    
        public PowerupType type;
        public boolean isActive;
        private int lifetimeCounter;
        private String text;
        
        private static final int NORMAL_LIFETIME = 400;

        public Powerup(String powerupText) {
            this(Math.random() * 900, Math.random() * 600, powerupText);
        }

        public Powerup(double xpos, double ypos, String powerupText) {
            this(PowerupType.values()[(int)(PowerupType.values().length * Math.random())], xpos, ypos, powerupText);
        }
        
        public Powerup(PowerupType pt, double xpos, double ypos, String powerupText) {
            super(xpos, ypos, POWERUP_SHAPE, true);
            type = pt;
            isActive = true;
            lifetimeCounter = NORMAL_LIFETIME;
            text = powerupText;
        }
        
        public void paint(Graphics g) {
            if (type == PowerupType.TELEPORT || type == PowerupType.FAST) {
                g.setColor(Color.magenta);
            }
            super.paint(g);
            g.setColor(Color.black);
            g.drawString(text, (int)(getX() - 8), (int)(getY() + 5));
            g.setColor(Color.YELLOW);
        }
        
        public void update() {
            lifetimeCounter--;
            if (lifetimeCounter < 1) {
                isActive = false;
            }
        }
        
    }
    
}
