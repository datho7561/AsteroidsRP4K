/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import java.awt.Polygon;
import java.util.ArrayList;

/**
 *
 * @author RealProgramming4Kids
 */
public class Asteroid extends PhysicsSprite {
    
    public static final int ROTATION = (int) (5 * Math.random() + 1);
    public static final double MAX_ASTEROID_SPEED = 5;
    public static final int MAX_SIZE = 3;
    
    private static final Polygon MY_SHAPE = new Polygon(new int[] {30,5,-25,-17,20}, new int[] {3,35,10,-15,-35}, 5);
   
    private int size;
    
    public Asteroid () {
        this(makeAsteroid());
    }
    
    public Asteroid (Asteroid a) {
        super (a);
        this.size = a.size;
    }
    
    public Asteroid (double xpos, double ypos, double xSpeed, double ySpeed, double angle, int asterSize, Polygon shape) {
        super(xpos, ypos, xSpeed, ySpeed, angle, shape);
        this.size = asterSize;
    }
    
    public Asteroid (double xpos, double ypos, double angle, int size){
        this(makeAsteroid(xpos, ypos, angle, size));
    }
        
    public Asteroid(double xpos, double ypos) {
        this(makeAsteroid(xpos, ypos));
    }
    
    private static Asteroid makeAsteroid () {  
        return makeAsteroid(Math.random() * 900, Math.random() * 600, Math.random()* Math.PI * 2, MAX_SIZE);     
    }
    
    private static Asteroid makeAsteroid (double xpos, double ypos) {  
        return makeAsteroid(xpos, ypos, Math.random()* Math.PI * 2, MAX_SIZE);     
    }
    
    private static Asteroid makeAsteroid (double xpos, double ypos, double angle, int size){
        double speed = Math.random() * MAX_ASTEROID_SPEED;
        
        Polygon shape = new Polygon();
        
        for (int i = 0; i < MY_SHAPE.npoints; i++){
            shape.addPoint((int)(MY_SHAPE.xpoints[i]*size/(double)MAX_SIZE), (int)(MY_SHAPE.ypoints[i]*size/(double)MAX_SIZE));
        }
        return new Asteroid(xpos, ypos,  speed * Math.cos(angle - Math.PI / 2), speed * Math.sin(angle - Math.PI / 2), angle, size, shape);
    }
    
    public void updatePosition () {
        autoRotation();
        super.updatePosition();
    }
    
    public void autoRotation () {
        rotate(ROTATION);
    }
    
    public ArrayList<Asteroid> split(double splitAngle, int numberOfSplits){

        ArrayList<Asteroid> newRocks = new ArrayList();
        if (this.size == 1){
            return newRocks;
        }
        
        double angleRange = Math.PI*2/numberOfSplits;
        for (int i = 0; i < numberOfSplits; i++) {
            
            double rockAngle = splitAngle + i * angleRange + Math.random() * angleRange;
            newRocks.add(new Asteroid(this.getX(), this.getY(), rockAngle, this.size-1));
            
            //
        }
        
        return newRocks;
    }
    
    public int getSize() {
        return this.size;
    }
    
}
