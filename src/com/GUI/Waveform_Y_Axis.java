package com.GUI;
import com.Volume;

import javax.swing.*;
import java.awt.*;

public class Waveform_Y_Axis extends JPanel {
    private Volume volume;
    private double intCap;
    private int height;
    private int width;
    private int gap;
    private int maximum;
    private int Y_increment;
    private int alignment;
    public int middle;
    public int borderSize;
    public static final int TOP = 0;
    public static final int MIDDLE = 1;


    public Waveform_Y_Axis(Volume volume, int height, int alignment){
        this.volume = volume;
        this.height = height-50;
        this.borderSize = 85;
        this.middle = Math.max((height* (alignment))/2, 0);
        this.alignment = alignment;
        this.width = 50;
        this.intCap = volume.getAudioAttributes()[4];
        this.gap = 5*(alignment+1);
        this.maximum = (int) (intCap + (gap - (intCap % gap)));
        System.out.println(intCap);
        this.Y_increment = this.height/(maximum/gap);
        this.setPreferredSize(new Dimension(width, height));

    }

    public int getBorderSize() {
        return borderSize;
    }

    public int getMiddle() {
        return middle;
    }

    public int getAlignment() {
        return alignment;
    }

    public int getY_increment() {
        return Y_increment;
    }

    public void paintComponent(Graphics g){
        g.drawLine(width, 0, width, height+50);
        FontMetrics fontMetrics = g.getFontMetrics();
        //System.out.println(maximum/gap);
        for (int i = (maximum/gap); i>=0; i-=1){
            String number = Integer.toString(gap*((maximum/gap)-i));
            //System.out.println("1+  "+number);
            String number2 = Integer.toString(gap*i);
            //System.out.println("2+  "+number2);

            g.drawLine(width, (i*Y_increment/(alignment+1)+7), width-5, (i*Y_increment/(alignment+1)+7));
            g.drawString(number, 40-(fontMetrics.stringWidth(number)), (i*Y_increment/(alignment+1))+14);

            if(alignment == MIDDLE){
                g.drawLine(width, middle+(i*Y_increment/(alignment+1)-20), width-5, middle+(i*Y_increment/(alignment+1)-20));
                g.drawString(number2, 40-(fontMetrics.stringWidth(number2)), middle+(i*Y_increment/(alignment+1)-13));
            }

        }
    }

    public Volume getVolume() {
        return volume;
    }
}
