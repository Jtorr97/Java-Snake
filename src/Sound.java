//---------------------------------------------------------------------------------
// 
// Sound.java
//

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound
{
    public enum Music
    {
        LEVEL_THEME("sounds/maintheme.wav");

        private Clip clip;

        Music(String musicFileName)
        {
            try {
                // Use URL (instead of File) to read from disk and JAR.
                URL url = this.getClass().getClassLoader().getResource(musicFileName);

                // Set up an audio input stream piped from the sound file.
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);

                // Get a clip resource.
                clip = AudioSystem.getClip();

                // Open audio clip and load samples from the audio input stream.
                clip.open(audioInputStream);
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        public void play()
        {
            if(clip.isRunning())
                    clip.stop();
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
        }

        public void stop()
        {
            clip.stop();
        }
    }

    public enum SoundEffect
    {
        NEW_HIGH_SCORE("sounds/newhighscore.wav"),
        GAME_OVER("sounds/gameovertheme.wav"),
        COLLISION("sounds/sfx_sounds_negative1.wav"),
        POINT_SCORED("sounds/sfx_sounds_fanfare2.wav"),
        GAME_START("sounds/sfx_sounds_button2.wav"),
        GAME_RESTART("sounds/sfx_sounds_button5.wav");

        // Each sound effect has its own clip, loaded with its own sound file.
        private Clip clip;

        // Constructor to construct each element of the enum with its own sound file.
        SoundEffect(String soundFileName)
        {
            try {
                // Use URL (instead of File) to read from disk and JAR.
                URL url = this.getClass().getClassLoader().getResource(soundFileName);

                // Set up an audio input stream piped from the sound file.
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);

                // Get a clip resource.
                clip = AudioSystem.getClip();

                // Open audio clip and load samples from the audio input stream.
                clip.open(audioInputStream);
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        // Play or Re-play the sound effect from the beginning, by rewinding.
        public void play()
        {
            if (clip.isRunning())
                    clip.stop();
                clip.setFramePosition(0);
                clip.start();
        }

        public void stop()
        {
            clip.stop();
        }
    }
}
