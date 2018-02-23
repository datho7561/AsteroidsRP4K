/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.LinkedList;

/**
 *
 * @author RealProgramming4Kids
 */
public class HighScoresDisplay extends VectorSprite {
    
    private LinkedList<String> scores;
    private int playerScore;
    private int playerScoreIndex;
    
    private static final int WIDTH = 300;
    private static final int HEIGHT = 450;
    private static final int TEXT_SHIFT_LEFT = 15;
    private static final int TEXT_LINE_SPACING = 5;
    private static final int GAMEOVER_TEXT_SIZE = 36;
    private static final Polygon MY_SHAPE = new Polygon(new int[] {0, WIDTH, WIDTH, 0}, new int[] {0, 0, HEIGHT, HEIGHT}, 4);
    
    public HighScoresDisplay(LinkedList<String> newScores, int score) {
        this(450, 300, newScores, score);
    }
    
    public HighScoresDisplay(double xpos, double ypos, LinkedList<String> newScores, int score) {
        super(xpos - WIDTH/2, ypos - HEIGHT/2, MY_SHAPE, true);
        scores = newScores;
        playerScore = score;
        
        playerScoreIndex = -1;
        
        for(int i = 0; i < scores.size(); i++) {
            if (Integer.parseInt(scores.get(i)) == playerScore){
                playerScoreIndex = i;
            }
        }
    }
    
    public void paint(Graphics g) {
        // store current font and colour
        Color oldc = g.getColor();
        Font currentFont = g.getFont();
        
        // Paint backdrop
        g.setColor(new Color(40,30,40));
        super.paint(g);
        
        // Paint border
        g.setColor(new Color(225,155,225));
        for (int i = 0; i < 3; i++){
            g.drawRect((int)getX() + i, (int)getY() + i, WIDTH, HEIGHT);
        }
        
        // Paint gameover
        g.setColor(new Color(225,155,225));
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("GAME OVER", (int)(getX() + TEXT_SHIFT_LEFT), (int)(getY() + GAMEOVER_TEXT_SIZE));
        
        //Adjust font
        
        Font newFont = new Font("Consolas", Font.PLAIN, 25); 
        g.setFont(newFont);
        
        // Paint highscores
        g.setColor(Color.CYAN);
        g.drawString("Highscores:",(int)(getX() + TEXT_SHIFT_LEFT), (int)(getY() + GAMEOVER_TEXT_SIZE + (g.getFont().getSize() + TEXT_LINE_SPACING)));
        for (int i = 0; i < scores.size(); i++) {
            if (i == playerScoreIndex) {
                double rand = Math.random();
                if(System.currentTimeMillis() % 1000 < 500){
                    g.setColor(Color.BLUE);
                }
            }
            g.drawString((i+1)+".   "+scores.get(i), (int)(getX() + TEXT_SHIFT_LEFT), (int)(getY() + GAMEOVER_TEXT_SIZE + (g.getFont().getSize() + TEXT_LINE_SPACING) * (i+2)));
            if (i == playerScoreIndex) {
                g.setColor(Color.CYAN);
            }
        }
        g.setColor(new Color(225,155,225));
        g.drawString("Your score:  "+playerScore,  (int)(getX() + TEXT_SHIFT_LEFT), (int)(getY() + GAMEOVER_TEXT_SIZE + (g.getFont().getSize() + TEXT_LINE_SPACING) * (scores.size()+2)));
        g.drawString("Press 'r' to restart",  (int)(getX() + TEXT_SHIFT_LEFT), (int)(getY() + GAMEOVER_TEXT_SIZE + (g.getFont().getSize() + TEXT_LINE_SPACING) * (scores.size()+3)));
        // Reset font and colour
        g.setFont(currentFont);
        g.setColor(oldc);
    }
    
}
