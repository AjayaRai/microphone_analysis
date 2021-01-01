package com.GUI;

import com.GUI.Waveform_Y_Axis;
import com.Pair;
import com.Tests.VolumeTests;
import com.Volume;
import com.GUI.Waveform;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.GUI.Waveform_Y_Axis.MIDDLE;

public class WaveformGraph extends JPanel {
    public WaveformGraph(String path) {
        setLayout(null);
        //setLayout(new BorderLayout());
        combine(path);
        setVisible(true);
    }

    public Volume createVolume(String path) {
        try {
            return VolumeTests.createVolume(path);
        }
        catch (IOException e) {
            System.out.println("[ERR]: " + e.getMessage() + ": Failed to create volume.");
        }
        return null;
    }

    public Waveform createGraph(String path, JPanel container){
        try {
            Waveform_Y_Axis yAxis = new Waveform_Y_Axis(createVolume(path), 600, MIDDLE);
            Waveform waveform = new Waveform(yAxis);
            JScrollPane scroll = new JScrollPane(waveform, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            add(scroll);
            //container.setSize(950, 600);
            //container.setVisible(true);
            return waveform;
        }
        catch (Exception e) {
            System.out.println("[ERR]: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void combine(String path){
        JPanel panel = new JPanel();
        Waveform graph = createGraph(path, panel);
        //add(graph.getYAxis(), BorderLayout.WEST);
        //add(graph, BorderLayout.CENTER);
        graph.setVisible(true);
    }
}