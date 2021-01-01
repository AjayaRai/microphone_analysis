package com.GUI;

import com.Volume;

import javax.swing.*;
import java.awt.*;

public class WaveformYAxis extends JPanel {
    private double intCap;
    private int height;
    private int width;
    private int gap;
    private int maximum;
    private int yIncrement;

    public WaveformYAxis(double intCap, int height){
        this.height = height - 50;
        this.width = 50;
        this.intCap = intCap;
        this.gap = 5;
        this.maximum = (int) (intCap + (gap - (intCap % gap)));
        this.yIncrement = height / (maximum / gap);
        setPreferredSize(new Dimension(width, this.height));
    }

    public void paintComponent(Graphics g){
        g.drawLine(width, 0, width, height);
        for (int i = (maximum/gap); i > 0; i -= 1){
            g.drawString(Integer.toString(gap * (height - i)), 0, i * yIncrement);
        }
    }
}
