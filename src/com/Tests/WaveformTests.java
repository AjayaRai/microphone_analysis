package com.Tests;

import com.GUI.GUI;
import com.GUI.Waveform;
import com.GUI.Waveform_Y_Axis;
import com.Volume;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.GUI.Waveform_Y_Axis.*;

public class WaveformTests {

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

    public static void WaveformAndAxesTest() {
        try {
            Waveform waveform = new Waveform(new Waveform_Y_Axis(VolumeTests.createVolume("trunk\\Test.wav"), 600, MIDDLE));
            waveform.setVisible(true);
            waveform.setBounds(0, 0, waveform.getWidth(), waveform.getHeight());
            JFrame frame = createFrame(1000, (int) waveform.getPreferredSize().getHeight(), 50, 0);
            JScrollPane scroll = new JScrollPane(waveform, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            frame.add(scroll);
            JFrame frame2 = createFrame(waveform.getYAxis().getWidth(), (int) waveform.getPreferredSize().getHeight(), 0, 0);
            //waveform.getYAxis().setBounds(0,0,waveform.getYAxis().getWidth(), waveform.getYAxis().getHeight());
            frame2.add(waveform.getYAxis()); //Need Different Layout to show graph
            frame.repaint();
            frame.revalidate();
        }
        catch (Exception e) {
            System.out.println("[ERR]: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WaveformAndAxesTest();
        //YAxisTest();
        //JFrame frame = createFrame(1000, 645);
        //frame.add(new WaveformGraph("C:\\Users\\bradg\\OneDrive\\Desktop\\Group 8\\sandbox\\ar17052\\Sources\\one_two_three.wav"));
    }
}
