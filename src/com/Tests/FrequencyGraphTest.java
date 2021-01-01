package com.Tests;

import com.AnalyseMicrophone.Frequency;
import com.GUI.FrequencyGraph;
import com.GUI.FrequencyYAxis;
import com.GUI.GUI;

import javax.swing.*;
import java.awt.*;

public class FrequencyGraphTest {
    public static JFrame createFrame(int width, int height, int x, int y) {
        JFrame frame = new JFrame();
        //frame.setLayout(null);
        frame.setSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(GUI.EXIT_ON_CLOSE);
        frame.setTitle("Waveform Test");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocation(new Point(x, y));
        return frame;
    }
    public static void frequencyYAxisTest(int height, int width){
        JFrame frame = createFrame(width, height, 0, 0);
        FrequencyYAxis yAxis = new FrequencyYAxis(Frequency.freq(), height, width, 10);
        frame.add(yAxis);
    }

    public static void frequencyTest(int height, int width){
        FrequencyGraph g = new FrequencyGraph(height, width);
        JFrame frame = createFrame(width, height, 850, 0);
        JFrame frame2 = createFrame(100, 1000, 800, 0);
        JScrollPane scroll = new JScrollPane(g, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.add(scroll);
        frame2.add(g.YAxis);
        frame.repaint();
        frame.revalidate();
    }

    public static void main(String[] args) {

        //frequencyYAxisTest(600, 100);
        frequencyTest(1000, 1000);

    }
}
