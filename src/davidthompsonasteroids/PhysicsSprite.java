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
public class PhysicsSprite extends VectorSprite {
    
    public static final double TAU = Math.PI*2;
    
    private double xVelocity, yVelocity; // speed
    private double rotationAngle; // direction
    
    private boolean isActive;
     
    private Polygon shape;
    
    // Blank vector sprite constructor
    public PhysicsSprite(){
        
        this(0, 0, 0, 0, 0, new Polygon());
        
    }
    
    // Filled vector sprite constructor
    public PhysicsSprite(double xpos, double ypos, double xSpeed, double ySpeed, double rotateAngle, Polygon myShape) {
        
        super(xpos, ypos, myShape);
        xVelocity = xSpeed;
        yVelocity = ySpeed; // speed
        rotationAngle = rotateAngle; // direction
    
        isActive = true;
     
        shape = copyShape(myShape);
        
    }
    
    // Vector sprite copy
    public PhysicsSprite (PhysicsSprite vs)
    {
        super(vs.getX(), vs.getY(), vs.shape);
        xVelocity = vs.xVelocity;
        yVelocity = vs.yVelocity; // speed
        rotationAngle = vs.rotationAngle; // direction
    
        isActive = vs.isActive;
     
        shape = copyShape(vs.shape);
    }
    
    
    
    public Point rotatePoint(Point p) {
        //double tempx = p.x - this.x;
        //double tempy = p.y - this.y;
        
        int outX = (int)Math.round(p.x*Math.cos(rotationAngle) - p.y*Math.sin(rotationAngle));
        int outY = (int)Math.round(p.x*Math.sin(rotationAngle) + p.y*Math.cos(rotationAngle));
        
        return new Point(outX, outY);
    }
    
    public void updatePosition(){
        // displacement
        setPosition(getX() + xVelocity, getY() + yVelocity);
        
        // rotation
        Polygon rotatedShape = new Polygon();
        for (int i = 0; i < shape.npoints; i++) {
            
            Point rotatedPoint = rotatePoint(new Point(shape.xpoints[i],shape.ypoints[i]));
            rotatedShape.addPoint(rotatedPoint.x,rotatedPoint.y);

        }
        
        super.setShape(rotatedShape);
        super.updatePosition();
    }
    
    public void rotate(int degrees) {
        rotationAngle += degrees*TAU/360;
    }
    
    public void accelerate(double rate) {
        
        xVelocity += rate * Math.cos(rotationAngle - Math.PI / 2);
        yVelocity += rate * Math.sin(rotationAngle - Math.PI / 2);
    }
    
    public double getSpeed()
    {
        double oldSpeed = Math.sqrt(xVelocity*xVelocity + yVelocity*yVelocity);
        //double oldAngle = Math.atan2(xVelocity, -yVelocity);
        //double newAngle = rotationAngle - Math.PI / 2 - oldAngle;
        return oldSpeed ;//* Math.cos(newAngle);
    }
    
    public boolean kill () {
        isActive = false;
        return true;
    }
    
    public void set(double xpos, double ypos, double xSpeed, double ySpeed, double rotateAngle){
        this.setPosition(xpos, ypos);
        xVelocity = xSpeed;
        yVelocity = ySpeed;
        rotationAngle = rotateAngle;
        isActive = true;
    }
    
    public double getFacingAngle() {
        return rotationAngle;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    
    
}
