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
public class VectorSprite {
    
    private double x, y; //position
    
    private Polygon shape;
    private Polygon vsprite;
    private boolean filled;
    
    public VectorSprite() {
        this(0, 0, new Polygon());
    } 
    
    public VectorSprite(double xpos, double ypos, Polygon s){
        this(xpos, ypos, s, false);
    }
    
    public VectorSprite(double xpos, double ypos, Polygon s, boolean isFilled){
        x = xpos;
        y = ypos;
        shape = copyShape(s);
        vsprite = copyShape(s);
        filled = isFilled;
    }
    
    public static Polygon copyShape(Polygon shape) {
        Polygon copy = new Polygon();
        
        for(int i = 0; i < shape.npoints; i++){
            copy.addPoint(shape.xpoints[i], shape.ypoints[i]);
        }
        
        return copy;
    }
    
    public void paint(Graphics g){
        if (filled){
            g.fillPolygon(vsprite);
        } else {
        g.drawPolygon(vsprite);
        }
    }
    
    public void updatePosition() {
        // apply displacement
        for (int i = 0; i < shape.npoints; i++){
            vsprite.xpoints[i] = (int)x + shape.xpoints[i];
            vsprite.ypoints[i] = (int)y + shape.ypoints[i];
        }
        vsprite.invalidate();
    }
    
    public void setShape(Polygon newShape) {
        shape = copyShape(newShape);
    }
    
    public boolean collision (VectorSprite other) {
        
        for (int i = 0; i < other.vsprite.npoints; i++) {
            if (this.vsprite.contains(other.vsprite.xpoints[i], other.vsprite.ypoints[i])) {
                return true;
            }
        }
        
        for (int i = 0; i < this.vsprite.npoints; i++) {
            if (other.vsprite.contains(this.vsprite.xpoints[i], this.vsprite.ypoints[i])) {
                return true;
            }
        }
        
        return false;
        
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setPosition(double newx, double newy) {
        x = newx;
        y = newy;
        wraparound();
    }
    
    private void wraparound(){
        
        if (x > 900){
            x %= 900;
        } else if (x < 0) {
            x = 900 + x % 900;
        }
        if (y > 600) {
            y %= 600;
        } else if(y < 0) {
            y = 600 + y % 600;
        }
        
    }
    
}
