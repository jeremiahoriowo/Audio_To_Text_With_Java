/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.goxr3plus.sphinx5mavenexample.application;

/**
 *
 * @author JERRY
 */
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;

public class SoundPlayer 
{
    private AudioFormat format;
    private byte[] samples;
    /**
     * 
     * @param filename le lien vers le fichier song (URL ou absolute path)
     */
    public SoundPlayer(String filename)
    {
        try
        {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            format = stream.getFormat();
            samples = getSamples(stream);
        }
        catch (UnsupportedAudioFileException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
    }

    public byte[] getSamples()
    {
        return samples;
    }

    public byte[] getSamples(AudioInputStream stream)
    {
        int length = (int)(stream.getFrameLength() * format.getFrameSize());
        byte[] samples = new byte[length];
        DataInputStream in = new DataInputStream(stream);
        try
        {
            in.readFully(samples);
        }
        catch (IOException e){e.printStackTrace();}
        return samples;
    }

    public void play(InputStream source)
    {
        int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate() / 10);
        byte[] buffer = new byte[bufferSize];
        SourceDataLine line;
        try
        {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine)AudioSystem.getLine(info);
            line.open(format, bufferSize);
        }
        catch (LineUnavailableException e)
        {
            e.printStackTrace();
            return;
        }
        line.start();

        try
        {
            int numBytesRead = 0;
            while (numBytesRead != -1)
            {
                numBytesRead = source.read(buffer, 0, buffer.length);
                if (numBytesRead != -1)
                    line.write(buffer, 0, numBytesRead);
            }
        }
        catch (IOException e){e.printStackTrace();}

        line.drain();
        line.close();
    }
}
