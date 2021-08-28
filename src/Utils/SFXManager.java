package Utils;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SFXManager {
    private static SFXManager instance;

    private SFXManager(){

    }

    public static SFXManager getInstance() {
        if(instance==null){
            instance = new SFXManager();
        }
        return instance;
    }

    public void playSound(String clipPath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(clipPath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}