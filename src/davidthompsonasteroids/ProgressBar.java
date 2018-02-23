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
public class ProgressBar {
    
    private VectorSprite outline;
    private VectorSprite progressBar;
    private Color colour;
    
    private static final Polygon BAR_SHAPE = new Polygon(new int[] {0, 150, 150, 0}, new int[] {0, 0, 20, 20}, 4);
    private static final Color DEFAULT_COLOUR = Color.CYAN;
    
    public ProgressBar(int xpos, int ypos, double fillPercent) {
        this(xpos, ypos, fillPercent, DEFAULT_COLOUR);
    }
    
    public ProgressBar(int xpos, int ypos, double fillPercent, Color c) {
        outline = new VectorSprite(xpos, ypos, BAR_SHAPE);
        progressBar = new VectorSprite(xpos, ypos, calculateProgressBarShape(fillPercent), true);
        colour = c;
    }
    
    private Polygon calculateProgressBarShape(double fillPercent) {
        Polygon newShape = new Polygon();
        for (int i = 0; i < BAR_SHAPE.npoints; i++) {
            newShape.addPoint((int)(BAR_SHAPE.xpoints[i] * fillPercent),BAR_SHAPE.ypoints[i]);
        }
        return newShape;
    }
    
    public void setFill(double fillPercent) {
        progressBar.setShape(calculateProgressBarShape(fillPercent));
        progressBar.updatePosition();
        outline.updatePosition();
    }
    
    public void paint(Graphics g) {
        Color oldc = g.getColor();
        g.setColor(colour);
        progressBar.paint(g);
        g.setColor(Color.WHITE);
        outline.paint(g);
        g.setColor(oldc);
    }
    
}


