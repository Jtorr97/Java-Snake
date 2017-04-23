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
        LEVEL_THEME("sounds/wyver9_Fast Level.wav");

        // Nested class for specifying volume
        public enum Volume
        {
            MUTE, PLAYING
        }

        public static Volume volume = Volume.PLAYING;

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
                gainControl.setValue(-20.0f); // Reduce volume by 20 decibels.
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        public void play()
        {
            if(volume != Music.Volume.MUTE) {
                if(clip.isRunning())
                    clip.stop();
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            }
        }
    }
    public enum SoundEffect
    {
        COLLISION("sounds/sfx_sounds_negative1.wav"),
        SCOREPOINT("sounds/sfx_sounds_fanfare2.wav"),
        GAMESTART("sounds/sfx_sounds_button2.wav"),
        GAMERESTART("sounds/sfx_sounds_button5.wav");

        // Nested class for specifying volume
        public enum Volume
        {
            MUTE, PLAYING
        }

        public static Volume volume = Volume.PLAYING;

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
                gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        // Play or Re-play the sound effect from the beginning, by rewinding.
        public void play()
        {
            if (volume != Volume.MUTE) {
                if (clip.isRunning())
                    clip.stop();   // Stop the player if it is still running
                clip.setFramePosition(0); // rewind to the beginning
                clip.start();     // Start playing
            }
        }

        // Optional static method to pre-load all the sound files.
        static void init()
        {
            values(); // calls the constructor for all the elements
        }
    }
}