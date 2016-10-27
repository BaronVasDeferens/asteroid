
import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
/**
 *
 * @author skot
 */
public class SoundClip {

    private String fileName;
    private File soundFile;

    // playAgainBeforeFinishing: will play the sound again even if the first iteration wasn't finished
    private boolean playAgainBeforeFinishing = false;


    private AudioInputStream sound;
    private DataLine.Info info;
    public Clip clip = null;
    Line line;
    AudioInputStream ais;

    SoundClip(String filename, boolean playAgain) {

        fileName = "sounds/" + filename;
        soundFile = new File(fileName);
        playAgainBeforeFinishing = playAgain;

        try {

            URL soundURL = getClass().getClassLoader().getResource(fileName);
            Line.Info linfo = new Line.Info(Clip.class);
            line = AudioSystem.getLine(linfo);

            InputStream is = getClass().getResourceAsStream(fileName);
            ais = AudioSystem.getAudioInputStream(is);
            DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);

        }
        catch (UnsupportedAudioFileException e)
        {
            System.out.println(e.toString());
        }
        catch (IOException e)
        {
            System.out.println(e.toString());
        }
        catch (LineUnavailableException e)
        {
            System.out.println(e.toString());
        }
    }

    public void play() {
        if (clip != null)
        {
            // If this is a play-all-the-way-before -restarting clip, wait for it to finish
            if (playAgainBeforeFinishing == true) {
                if  (clip.isRunning() == false) {
                    clip.setFramePosition(0);
                    clip.loop(0);
                }
            }

            else {
                clip.setFramePosition(0);
                clip.loop(0);
            }

        }
    }

    public void loop(int times)
    {
        if (clip != null)
            clip.loop(times);
    }

    public void stop() {
        if (clip != null)
            clip.stop();
    }

}
