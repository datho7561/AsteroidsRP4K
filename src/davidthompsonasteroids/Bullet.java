/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import java.awt.*;

/**
 *
 * @author RealProgramming4Kids
 */
public class Bullet extends PhysicsSprite {
    
    public static final double BULLET_SPEED = 4;
    
    public static final int LIFETIME = 100;
    private int timeToLive;
    
    private static final Polygon MY_SHAPE = new Polygon(new int[]{0, 3}, new int[]{0, 0}, 2);
    
    public Bullet (){
        super(makeBullet(0,0,0,0));
        timeToLive = LIFETIME;
    }
    
    public Bullet (double xpos, double ypos, double rSpeed, double angleRad){
        super(makeBullet(xpos, ypos, rSpeed, angleRad));
        timeToLive = LIFETIME;
    }
    
    public Bullet (double xpos, double ypos, double rSpeed, double angleRad, int lifetime){
        super(makeBullet(xpos, ypos, rSpeed, angleRad));
        timeToLive = lifetime;
    }
    
    public Bullet(double xpos, double ypos, double xSpeed, double ySpeed, double angle, Polygon shape){
        this (LIFETIME, xpos, ypos, xSpeed, ySpeed, angle, shape);
    }
    
    public Bullet(int life, double xpos, double ypos, double xSpeed, double ySpeed, double angle, Polygon shape){
        super(xpos, ypos, xSpeed, ySpeed, angle, shape);
        timeToLive = life;
    }
    
    public Bullet(double xpos, double ypos, double rSpeed, double angle, Polygon shape){
        super(makeBullet(xpos, ypos, rSpeed, angle, shape));
        timeToLive = LIFETIME;
    }
    
    public Bullet(double xpos, double ypos, double rSpeed, double angle, Polygon shape, int lifetime){
        super(makeBullet(xpos, ypos, rSpeed, angle, shape));
        timeToLive = lifetime;
    }
    
    public Bullet(Bullet b){
        super(b);
        timeToLive = b.timeToLive;
    }
    
    private static Bullet makeBullet(double xpos, double ypos, double rSpeed, double angle) {
        
        return makeBullet(xpos, ypos, rSpeed, angle, MY_SHAPE);
    }
    
    private static Bullet makeBullet(double xpos, double ypos, double rSpeed, double angle, Polygon shape) {
        
        double xSpeed = (BULLET_SPEED + rSpeed) * Math.cos(angle);
        double ySpeed = (BULLET_SPEED + rSpeed) * Math.sin(angle);
        
        return new Bullet (xpos, ypos, xSpeed, ySpeed, angle, shape);
    }
    
    public void updatePosition(){
        
        super.updatePosition();
        if(isActive()) {
            timeToLive--;
            
            if(timeToLive<=0) {
                kill();
            }
        }
        
        
    }
}
