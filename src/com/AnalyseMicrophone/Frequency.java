package com.AnalyseMicrophone;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import javax.sound.sampled.*;


public class Frequency {

    public static Map<Double, Double> freq(){
        final int EXTERNAL_BUFFER_SIZE = 1500000;
        File Recording = new File("D:\\Uni\\ce320-08\\sandbox\\ar17052\\Sources\\one_two_three.wav");

        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(Recording);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        AudioFormat audFormat = audioInputStream.getFormat();

        SourceDataLine	line = null;
        DataLine.Info	info = new DataLine.Info(SourceDataLine.class, audFormat);
        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //line.start();

        int numBytesRead = 0;
        byte[]	soundArray = new byte[EXTERNAL_BUFFER_SIZE];
        while (numBytesRead != -1) {
            try {
                numBytesRead = audioInputStream.read(soundArray, 0, soundArray.length);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (numBytesRead >= 0) {
                int numBytesWrote = line.write(soundArray, 0, numBytesRead);
            }

        }

        line.drain();
        //line.close();

        float sample_rate = audFormat.getSampleRate();
        //System.out.println("sample rate = "+sample_rate);

        float soundLength = Frequency.getSoundLength(audioInputStream.getFrameLength(), audFormat.getFrameRate());
        //System.out.println("soundLength = "+soundLength);

        int distancePoints = (int) Frequency.getDistancePoints( soundLength,  sample_rate);
        //System.out.println("distancePoints = "+distancePoints);

        float intervalSeconds = Frequency.getintervalSeconds(soundLength, distancePoints);
        //System.out.println("intervalSeconds = "+intervalSeconds);

        boolean isBigEndian = audFormat.isBigEndian();
        int x[] = new int[distancePoints];

        for (int i = 0; i < distancePoints*2; i+=2) {
            int b1 = soundArray[i];
            int b2 = soundArray[i + 1];
            if (b1 < 0) {
                b1 += 0x100;
            }
            if (b2 < 0) {
                b2 += 0x100;
            }
            int value;

            if (!isBigEndian) value = (b1 << 8) + b2;
            else value = b1 + (b2 << 8);
            x[i/2] = value;
        }
        double f[] = new double[distancePoints/2];
        Map<Double, Double> results = new TreeMap<>();
        System.out.println(distancePoints/2);

        for (int j = 0; j < distancePoints/2; j+=20) { //every 20th data == 5%

            double firstSummation = 0;
            double secondSummation = 0;

            for (int k = 0; k < distancePoints; k++) {
                double twoPInjk = ((2 * Math.PI) / distancePoints) * (j * k);
                firstSummation +=  x[k] * Math.cos(twoPInjk);
                secondSummation += x[k] * Math.sin(twoPInjk);
            }
            f[j] = Math.abs( Math.sqrt(Math.pow(firstSummation,2) +
                    Math.pow(secondSummation,2)) );

            double amplitude = 2 * f[j]/distancePoints;
            double frequency = j * intervalSeconds / soundLength * sample_rate;
            //System.out.println("frequency = "+frequency+", amp = "+amplitude);

            results.put(frequency, amplitude);

        }

        return results;
    }

    public static float getDistancePoints(float soundLength, float sample_rate){
        return (soundLength * sample_rate) / 2;
    }


    public static float getintervalSeconds(float soundLength, float distancePoints){
        return soundLength / distancePoints;
    }


    public static float getSoundLength(long frameLength, float frameRate) {
        return frameLength / frameRate;
    }


    public static void main(String[] args) {
        freq();
    }
}