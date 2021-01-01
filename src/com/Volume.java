
package com;

import javax.sound.sampled.AudioFormat;
import java.util.ArrayList;

import com.AnalyseSilence.Calculation;

public class Volume {
    private byte[] recording;
    private int[] unsignedRecording;
    private AudioFormat properties;

    public Volume(AudioFormat properties, byte[] recording) {
        this.properties = properties;
        this.recording = recording;
        unsignedRecording = new int[recording.length];
        for (int i = 0; i < recording.length; i += 1) {
            unsignedRecording[i] = (byte) Math.abs(recording[i]);
        }
    }

    public byte[] getRecording() {
        return recording;
    }

    public int[] getUnsignedRecording() {
        return unsignedRecording;
    }

    public AudioFormat getProperties() {
        return properties;
    }
    
    public double signalToNoise() {
        AudioFormat.Encoding encoding = properties.getEncoding();
        float samplingRate = properties.getSampleRate();
        int sampleSize = properties.getSampleSizeInBits();
        double intCap = Math.pow(2, sampleSize - 1);
        double signalToNoise = Calculation.average(unsignedRecording) / Math.sqrt(Calculation.variance(unsignedRecording));
        System.out.println(encoding.toString() + ", " + recording.length + " bits, " + (recording.length / samplingRate) + " seconds, " + (int) intCap + " integer cap, " + signalToNoise + " signal-to-noise ratio");
        return signalToNoise;
    }

    public double[] getAudioAttributes(){
        int channels = properties.getChannels();
        boolean bigEndian = properties.isBigEndian();
        float samplingRate = properties.getSampleRate();
        int sampleSize = properties.getSampleSizeInBits();
        double intCap = 128;
        double seconds = recording.length / samplingRate;
        return new double[]{channels, (bigEndian?1:0), samplingRate, sampleSize, intCap, seconds};
    }

    public static AudioFormat getAudioFormat() {
        float sampleRate = 176400 * 4;
        int sampleSizeInBits = 16;
        int channels = 2;
        return new AudioFormat(sampleRate, sampleSizeInBits,
                channels, true, true);
    }

    public Pair<ArrayList<EditablePair<Integer, Integer>>, ArrayList<EditablePair<Integer, Integer>>> getHighlightRanges(int speaking, int silence, int squeeze, int smudge) {
        System.out.println("getHighlightRanges(" + speaking + ", " + silence + ", " + squeeze + ", " + smudge + ")");
        ArrayList<Integer> sp = new ArrayList<>(), sl = new ArrayList<>();
        for (int i = 0; i < unsignedRecording.length; i += squeeze) {
            if (unsignedRecording[i] >= speaking) {
                sp.add(i);
                System.out.println(i + " : " + unsignedRecording[i] + " speaking");
            }
            else if (unsignedRecording[i] <= silence) {
                sl.add(i);
                System.out.println(i + " : " + unsignedRecording[i] + " silence");
            }
        }
        ArrayList<EditablePair<Integer, Integer>> speakingRanges = new ArrayList<>();
        ArrayList<EditablePair<Integer, Integer>> silentRanges = new ArrayList<>();
        if (unsignedRecording[0] >= speaking) {
            speakingRanges.add(new EditablePair<>(0, -1));
        }
        for (int i = 0; i < sp.size() - 1; i += 1) {
            createContiguousPair(i, squeeze, smudge, sp, speakingRanges);
        }
        //speakingRanges.remove(speakingRanges.size() - 1);
        System.out.println(speakingRanges + " : " + unsignedRecording.length);
        System.out.println(sp + " : " + unsignedRecording.length);
        return new Pair<>(speakingRanges, silentRanges);
    }

    private boolean createContiguousPair(int i, int squeeze, int smudge, ArrayList<Integer> points, ArrayList<EditablePair<Integer, Integer>> ranges) {
        if (points.get(i + 1) - points.get(i) > squeeze * smudge) {
            if (ranges.size() > 0) {
                ranges.get(ranges.size() - 1).setValue(points.get(i));
            }
            ranges.add(new EditablePair<>(points.get(i + 1), -1));
            points.remove(points.get(i));
            points.add(i, null);
            return false;
        }
        return true;
    }
}