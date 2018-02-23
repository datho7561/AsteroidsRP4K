/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package davidthompsonasteroids;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 *
 * @author D
 */
public class Sound {
    
    private String filepath;
    private AudioClip clip;
    
    public Sound(String fp) {
        filepath = fp;
        try {
            clip = Applet.newAudioClip(Asteroids.class.getResource(filepath));
        } catch (Exception e) {
            System.out.println("Sound '" + filepath + "' was unable to be located and/or played.");
        }
    }
    
    public void play() {
        clip.play();
    }
    
    public void stop() {
        clip.stop();
    }
    
    public void loop() {
        clip.loop();
    }
    
}
