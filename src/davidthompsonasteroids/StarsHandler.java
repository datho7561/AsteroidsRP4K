/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.*;

/**
 *
 * @author RealProgramming4Kids
 */
public class StarsHandler {
    
    private static final Polygon MY_SHAPE = new Polygon(new int[] {0,1,1,0}, new int[] {0,0,1,1}, 4);
    private static final Color STAR_COLOUR = new Color(255, 255, 255);
    private static final int MIN_STARS = 100;
    private static final int MAX_STARS = 150;
    private static final int LOWER_BRIGHTNESS_RANGE = 100;
    private ArrayList<Star> stars;
    
    public StarsHandler () {
        this(MIN_STARS + (int)(Math.random() * (MAX_STARS - MIN_STARS)));
    }
    
    public StarsHandler(int numStars) {
        stars = new ArrayList();
        for(int i = 0; i < numStars; i++) {
            stars.add(new Star());
        }
    }
    
    public void updatePosition() {
        for(Star s: stars){
            s.updatePosition();
        }
    }
    
    public void paint(Graphics g) {
        
        Color oldc = g.getColor();
        for (Star s: stars) {
            s.paint(g);
        }
        g.setColor(oldc);
    }
    
    
    
    private class Star extends VectorSprite {
    
        private int fadeCounter;
        private int direction;

        public Star() {
            super(Math.random()*900, Math.random()*600, MY_SHAPE);
            fadeCounter = (int)(Math.random()*256);
            direction = 1;
        }

        public void updatePosition() {
            fadeCounter += 2 * direction;
            if (fadeCounter > 255){
                fadeCounter = 255 * 2 - fadeCounter;
                direction *= -1;
            } else if(fadeCounter < 0) {
                fadeCounter *= -1;
                direction *= -1;
            } 
            super.updatePosition();
        }

        public void paint(Graphics g){

            g.setColor(new Color(STAR_COLOUR.getRed(), STAR_COLOUR.getGreen(), STAR_COLOUR.getBlue(), (int)(Math.sin(fadeCounter/128)*127) + 127));
            
            super.paint(g);

        }
    
    }
    
}
