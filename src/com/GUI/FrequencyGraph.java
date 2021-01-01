package com.GUI;

import com.AnalyseMicrophone.Frequency;

import javax.swing.*;
import java.awt.*;

public class FrequencyGraph extends JPanel {
    private int width;
    private int height;
    private int stepX;
    private int border;
    public FrequencyYAxis YAxis;
    private Point[] coords;


    public FrequencyGraph(int height, int width){
        this.YAxis = new FrequencyYAxis(Frequency.freq(), height, 100, 10);
        this.height = height;
        this.width = width;
        this.stepX = YAxis.stepX;
        this.border = 50;
        coords = getCoords();
        /*
        for (Point i:coords){
            System.out.println(i.x +" "+i.y);
        }*/
        setPreferredSize(new Dimension(YAxis.data.size(), height));

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Point c;
        for (int i = 0; i < (YAxis.data.size()); i++) {
            c=coords[i];
            g.drawLine(c.x, height-border, c.x, height-border-c.y);
        }
    }

    private Point[]getCoords(){
        coords = new Point[YAxis.data.size()];
        int count = 0;
        for (Double key: YAxis.data.keySet()){
            coords[count] = new Point(Integer.parseInt(String.valueOf(Math.round(key))), Integer.parseInt(String.valueOf(Math.round(YAxis.data.get(key)*(0.25)))));//need to fix 1
            count++;
        }
        return coords;
    }

}
