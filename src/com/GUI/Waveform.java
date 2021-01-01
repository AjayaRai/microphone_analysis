package com.GUI;

import com.*;

import javax.sound.sampled.AudioFormat;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Waveform extends JPanel {
    private byte[] recording;
    private AudioFormat properties;
    private Waveform_Y_Axis waveform_y_axis;
    private int channels;
    private boolean bigEndian;
    private float samplingRate;
    private int sampleSize;
    private double intCap;
    private double seconds;
    private int maximum;
    private double gap;
    private double xIncrement;
    private int squeeze;
    private int middle;

    private int width;
    private int  height;
    private int border;
    private Point[] coords;
    private Pair<Pair<Color, Color>, ArrayList<EditablePair<Integer, Integer>>> speaking;
    private Pair<Pair<Color, Color>, ArrayList<EditablePair<Integer, Integer>>> silence;

    public Waveform(Waveform_Y_Axis waveform_y_axis) throws NullPointerException {
        Volume volume = waveform_y_axis.getVolume();
        this.recording = volume.getRecording();
        this.properties = volume.getProperties();
        this.waveform_y_axis = waveform_y_axis;
        double[] attributes = volume.getAudioAttributes();
        this.channels = (int) attributes[0];
        this.bigEndian = attributes[1] == 1;
        this.samplingRate = (float) attributes[2];
        this.sampleSize = (int) attributes[3];
        this.intCap = attributes[4];
        this.seconds = attributes[5];
        this.width = 950;
        this.height = 645;
        this.border = waveform_y_axis.getBorderSize();
        this.middle = waveform_y_axis.getMiddle() - (border / 4);
        this.gap = 1;
        this.squeeze = 1600;
        this.maximum = (int) (seconds + (gap - (seconds % gap)));
        this.xIncrement = recording.length / (maximum / gap * squeeze);
        this.coords = coords();
        Pair<ArrayList<EditablePair<Integer, Integer>>,
                     ArrayList<EditablePair<Integer, Integer>>> ranges = volume.getHighlightRanges(80, 30, squeeze, 30);
        this.speaking = new Pair<>(getHighlightColorPair(Color.GREEN), ranges.getKey());
        this.silence = new Pair<>(getHighlightColorPair(Color.RED), ranges.getValue());
        System.out.println(speaking);
        System.out.println(silence);
        setPreferredSize(new Dimension((recording.length / squeeze) + 30, height));
        setSize((int) getPreferredSize().getWidth(), (int) getPreferredSize().getHeight());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //drawHighlight(g, silence.getKey().getKey(), silence.getKey().getValue(), 0, 255);
        drawSpeaking(g);
        drawSilence(g);
        Point c1, c2;
        for (int i = 0; i < (recording.length - 1) / squeeze; i++) {
            c1 = coords[i * squeeze];
            c2 = coords[(i + 1) * squeeze];
            g.drawLine(i, middle + c1.y, i + 1, middle + c2.y);
        }
        drawAxis(g);
    }

    private Point[] coords() {
        int YIncrement = waveform_y_axis.getY_increment() / 5;
        coords = new Point[recording.length];
        for (int i = 0; i < recording.length; i++) {
            coords[i] = new Point(i, ((recording[i] * YIncrement) / 8) * (2 - waveform_y_axis.getAlignment() + 1));
        }
        return coords;
    }

    public void drawHighlight(Graphics g, Color c, Color tc, int min, int max) {
        g.setColor(tc);
        g.fillRect(min, 0, max - min, height - border - 1);
        g.setColor(c);
        g.drawRect(min, 0, max - min, height - border - 1);
        g.setColor(Color.BLACK);
    }

    private void drawHighlightRanges(Graphics g, Pair<Pair<Color, Color>, ArrayList<EditablePair<Integer, Integer>>> ranges){
        Pair<Color, Color> colours = ranges.getKey();
        int max;
        for (EditablePair<Integer, Integer> range : ranges.getValue()) {
            if (range.getValue() == -1) {
                max = recording.length;
            }
            else {
                max = range.getValue();
            }
            drawHighlight(g, colours.getKey(), colours.getValue(), range.getKey() / squeeze, max / squeeze);
        }
    }

    public void drawSpeaking(Graphics g) {
        drawHighlightRanges(g, speaking);
    }
    public void drawSilence(Graphics g) {
        drawHighlightRanges(g, silence);
    }

    public void drawAxis(Graphics g) {
        g.drawLine(0, height - border, getWidth(), height - border);
        for (int i = (int) (maximum / gap); i > 0; i -= 1) {
            g.drawString(Integer.toString((int) (gap * i)), (int) (i * xIncrement),height - border + 18);
        }
        g.setColor(Color.gray);
        g.drawLine(0, middle, getWidth(), middle);
        g.setColor(Color.black);
    }

    public Waveform_Y_Axis getYAxis() {
        return waveform_y_axis;
    }

    public Color getTransparentColor(Color c, double a) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) (a * 255));
    }

    private Pair<Color, Color> getHighlightColorPair(Color c) {
        return new Pair<>(getTransparentColor(c, 0.7), getTransparentColor(c, 0.45));
    }

    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        getYAxis().setVisible(aFlag);
    }
}
