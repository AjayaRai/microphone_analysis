package com.GUI;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;

public class FrequencyYAxis extends JPanel{
    public Map<Double, Double> data;
    private int height;
    private int width;
    private int border;
    public int stepY;
    public int stepX;
    public int gapY;
    private int max;
    private int squeeze;



    public FrequencyYAxis(Map<Double, Double> data, int height, int width, int squeeze){
        this.border = 50;   //distance between axis and edge of frame
        this.gapY = 1000;   //interval in which axis counts
        this.data = data;
        this.height = height;
        this.width = width;
        this.squeeze = squeeze;


        int big = largest(data);
        this.max = big+(gapY-(big%gapY));


        this.stepX = (int) (Math.ceil(width-border)/data.size()/squeeze);
        this.stepY = height/(max/gapY);
    }

    static int largest(Map<Double, Double> data){
        int largest = 0;

        for (Double key : data.keySet())
        {
            if (data.get(key)>largest)
            {
                largest = Integer.parseInt(String.valueOf(Math.round(data.get(key))));
            }
        }
        return largest;
    }

    public void paintComponent(Graphics g){
        g.drawLine(border, 0, border, height);
        FontMetrics fontMetrics = g.getFontMetrics();
        for (int i = (max/gapY); i>=0; i-=1){
            String number = Integer.toString(gapY*((max/gapY)-i));
            g.drawLine(border, i*stepY, border-5, i*stepY);
            g.drawString(number, border-10-(fontMetrics.stringWidth(number)), i*stepY);
        }
    }
}
