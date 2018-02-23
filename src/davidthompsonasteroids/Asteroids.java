/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author RealProgramming4Kids
 */
public class Asteroids extends JPanel implements ActionListener, KeyListener {

    // the framerate timer
    Timer framerate;
    
    Spacecraft ship;
    HighScoresDisplay hsd;
    
    ExplosionHandler explosions;
    StarsHandler stars;
    PowerupHandler powerups;
    ProgressBar shieldRemaining;
    AsteroidHandler rocks;
    
    LinkedList<String> highScoresList;
    
    //sounds
    Sound laser, shipHit, asteroidHit, thruster;
    
    boolean upKeyPressed;
    boolean downKeyPressed;
    boolean leftKeyPressed;
    boolean rightKeyPressed;
    boolean spaceBarPressed;
    boolean shiftKeyPressed;
    
    boolean shieldStatusChanged;
    
    boolean isOddFrame;
    
    boolean showHighScores;
    
    int score;
    
    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    public void init() {
        reset();
    }
    
    public Asteroids() {
        init();
        start();
    }
    
    // Does all the setting. Called when the program starts and when 'r' is released
    public void reset() {
        
        this.setSize(900,600);
        this.addKeyListener(this);
        
        // Applet only
        laser = new Sound("laser80.wav");
        shipHit = new Sound("explode0.wav");
        asteroidHit = new Sound("explode1.wav");
        thruster = new Sound("thruster.wav");
        
        rocks = new AsteroidHandler();
        explosions = new ExplosionHandler();
        stars = new StarsHandler();
        powerups = new PowerupHandler();
        ship = new Spacecraft(explosions, laser);
        
        shieldRemaining = new ProgressBar(735, 10, 1);
        
        upKeyPressed = false;
        downKeyPressed = false;
        rightKeyPressed = false;
        leftKeyPressed = false;
        spaceBarPressed = false;
        shiftKeyPressed = false;
        score = 0;
        isOddFrame = false;
        
        showHighScores = false;
        
        framerate = new Timer(20, this);
    }
    
    // --------------------------------------------------------------//
    // Code for controls
    
    public void keyTyped(KeyEvent e) {
        
    }
    // move the line according to the arrow key preessed
    public void keyPressed(KeyEvent e) {
        
        // if the user presses the right arrow key,
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            
            rightKeyPressed = true;
            
        } // if the user presses the left key 
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            
            leftKeyPressed = true;
            
        } // if the user presses the up key
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if(!upKeyPressed) {
                thruster.loop();
            }
            upKeyPressed = true;
            
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(!downKeyPressed) {
                thruster.loop();
            }
            downKeyPressed = true;
            
            
        } //if the user presses the space key
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            
            spaceBarPressed = true;
            
        } // if the user pressed the shift key
        else if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT && e.getKeyCode() == KeyEvent.VK_SHIFT){
            shiftKeyPressed = true;
            shieldStatusChanged = true;
        }
        
    }
    public void keyReleased(KeyEvent e) {
    
        // if the user releases the right arrow key,
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            
            rightKeyPressed = false;
            
        } // if the user releases the left key 
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            
            leftKeyPressed = false;
            
        } // if the user releases the up key
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            
            upKeyPressed = false;
            thruster.stop();
            
        } // if the user releases the down key
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            
            downKeyPressed = false;
            thruster.stop();
            
        } //if the user releases the space key
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            
            spaceBarPressed = false;
            
        } // if the user pressed the shift key
        else if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT && e.getKeyCode() == KeyEvent.VK_SHIFT){
            shiftKeyPressed = false;
            shieldStatusChanged = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_R) {
            reset();
        }
        
    }
    // Checks which keys are pressed andadjust the spaceship movement
    public void checkKeys() {
        
        if (upKeyPressed){
            ship.accelerate(Spacecraft.ACCELERATION);
        }
        if (downKeyPressed) {
            ship.accelerate(-Spacecraft.ACCELERATION);
        }
        if (leftKeyPressed) {
            ship.rotate(-Spacecraft.ROTATION);
        }
        if (rightKeyPressed) {
            ship.rotate(Spacecraft.ROTATION);
        }
        if (spaceBarPressed) {
            ship.fire();
        }
        if (shieldStatusChanged) {
            ship.setShield(shiftKeyPressed);
            shieldStatusChanged = false;
        }
        
        
    }
    // --------------------------------------------------------------//  
    
    // Code for drawing graphics to the screen     
    public void paint(Graphics g){
        
        g.setFont(new Font("Consolas", Font.PLAIN, 24));
        
        // draw everything to the offscreeen image buffer
        
        // draw black background        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 900, 600);
        
        // draws the stars
        stars.paint(g);
        
        // makes what we draw green
        g.setColor(Color.green);
        
        // drawing all the objects
        ship.paint(g);
        
        rocks.paint(g);
        
        powerups.paint(g);
        
        explosions.drawDebris(g);
        
        g.drawString("Lives: " + ship.lives, 10, 30);
        g.drawString("Score: "+ score, 400, 30);
        shieldRemaining.paint(g);
        
        if (showHighScores) {
            hsd.updatePosition();
            hsd.paint(g);
        }
        
    }
    public void update(Graphics g){
        this.paint(g);
    }
    
    // --------------------------------------------------------------//
    
    // The framerate refresh
    public void actionPerformed(ActionEvent ae) {
        
        stars.updatePosition();
        
        if (!ship.isActive()){
            if (ship.lives > 0){
                if (ship.respawn()) {
                    shieldStatusChanged = true;
                    if (ship.lives == 0) { 
                        highScoresList = readHighScore(score);
                        writeHighScore(highScoresList);
                        hsd = new HighScoresDisplay(highScoresList, score);
                    }
                }
            }
        }
        
        if (ship.isActive()){
            checkKeys();
        }
        
        // update the positions of all the objects
        ship.updatePosition();
        
        shieldRemaining.setFill(ship.getShieldTime()/(double)Spacecraft.MAX_SHIELD_TIME);
        
        if (!ship.hasSlowPowerup() || isOddFrame) {
            
            rocks.update(ship);
            
            explosions.updateDebris();
        }
        
        checkCollisions();
        
        if (score < 0) score = 0;
        
        isOddFrame = !isOddFrame;
        
        this.repaint();
    }
    public void start() {
        framerate.start();
    }
    public void stop() {
        framerate.stop();
    }
    
    // --------------------------------------------------------------//
    
    

    public void checkCollisions () {
        
        powerups.checkCollisions(ship);
        
        // asteroids and ship or bullets and ship
        // split asteroids are added to the asteroid list
        rocks.collisions(ship);

    }
    
    // --------------------------------------------------------------//
    
    public void destroy() {
        
    }
    
    public LinkedList<String> readHighScore(int score) {
        
        LinkedList<String> oldScores = new LinkedList();
        
        try {
            // load scores
            oldScores = new LinkedList(Files.readAllLines(Paths.get("highscores.txt"),
                    StandardCharsets.UTF_8));
        } catch (IOException ioe) {
            for (int i = 0; i < 10; i++) {
                oldScores.add("0");
            }
        }
        
        
        // reorder scores
        
        for (int i = 0; i < oldScores.size(); i++) {
            
                if (score > Integer.parseInt(oldScores.get(i))) {
                    oldScores.add(i, Integer.toString(score));
                    oldScores.removeLast();
                    break;
                }
            
        }
        
        showHighScores = true;
        
        return oldScores;
    }
    
    public void writeHighScore(LinkedList<String> scores){
        
        try {
            Files.write(Paths.get("highscores.txt"), scores, StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException ex) {
            System.out.println("oeairg");
        }
        
    }
    
    private class AsteroidHandler {
        
        public static final int MAX_SPAWN_TIME = 500;
        public static final int MIN_SPAWN_TIME = 25;
        int spawnCounter;
        int lastSpawn;
        ArrayList<Asteroid> asteroidList;
        
        public AsteroidHandler() {
            spawnCounter = MAX_SPAWN_TIME;
            lastSpawn = spawnCounter;
            
            asteroidList = new ArrayList();
            
            // initial asteroid spawn
            for (int i = 0; i < 5; i++){
                asteroidList.add(new Asteroid());
            }
            
        }
        
        public void addRocks(Spacecraft sc) {
            
            spawnCounter --;
            
            if (!showHighScores && spawnCounter <= 0) {
                double newx,newy;                    
                    newx = Math.random() * 900;
                    newy = Math.random() * 600;
                if (sc.safeToSpawnAsteroid(newx, newy)){
                    asteroidList.add(new Asteroid(newx, newy));
                    spawnCounter = lastSpawn - (int)(Math.random() * 100);
                    if (spawnCounter < MIN_SPAWN_TIME){
                        spawnCounter = MIN_SPAWN_TIME;
                    }
                    lastSpawn = spawnCounter;
                }
                
                
            }
        }
        
        public void update(Spacecraft sc) {
            
            addRocks(sc);
            for (Asteroid a : asteroidList) {
                a.updatePosition();
            }
        }
        
        public void collisions(Spacecraft ship) {
            
            ArrayList<Asteroid> newRocks = new ArrayList();
            for (Asteroid rock : asteroidList) {
                if (rock.isActive() && rock.collision(ship)) {
                    if (!ship.isShielded()){
                        if(ship.isActive() && ship.kill()) {
                            explosions.addExplosion(ship.getX(), ship.getY(),500,1000);
                            score -= 100;
                            shipHit.play();
                        }
                    } else {
                        newRocks.addAll(killRock(rock, ship.getFacingAngle() - Math.PI/2));
                    }
                }
            }

            for (Asteroid r : asteroidList) {
                if (r.isActive()) {
                    Bullet b = ship.weapons.bulletCollision(r);
                    if (b != null) {
                        newRocks.addAll(killRock(r, b.getFacingAngle() - Math.PI/2));
                        b.kill();
                    }

                }
            }
            for (Asteroid a : newRocks){
                asteroidList.add(a);
            }
            
            flushList();
            
        }
        
        private void flushList(){
        
            Iterator<Asteroid> aIter = asteroidList.iterator();

            while(aIter.hasNext()){
                if (!aIter.next().isActive()) {
                    aIter.remove();
                }
            }
        }
        
        public void paint(Graphics g) {
            for (Asteroid a : asteroidList){
                a.paint(g);
            }
        }
        
        public ArrayList<Asteroid> killRock(Asteroid r, double splitAngle) {
            r.kill();

            explosions.addExplosion(r.getX(), r.getY());
            powerups.spawnPowerup(r.getX(), r.getY(), powerups.roll());

            asteroidHit.play();

            if (r.getSize() == 1) score += 50;
            return r.split(splitAngle, 3);
        }
        
    }

}
