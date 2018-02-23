/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

/**
 *
 * @author RealProgramming4Kids
 */
public class Powerup extends VectorSprite {
    
    private PowerupType type;
    
    public enum PowerupType {
        SHIELD, SLOW, FAST, EXTRA_LIFE, FAST_FIRING, CONE_WEAPON
    }
    
    public Powerup() {
        this(PowerupType.values()[(int)(PowerupType.values().length * Math.random())]);
    }
    
    public Powerup(PowerupType pt) {
        type = pt;
    }
    
    
    
}
