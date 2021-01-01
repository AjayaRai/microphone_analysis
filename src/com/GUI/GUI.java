package com.GUI;

import com.OutputAnalysis.OutputAnalysis;
import com.Recorder;
import com.Tests.VolumeTests;

import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import static com.GUI.Waveform_Y_Axis.MIDDLE;
import static com.Tests.FrequencyGraphTest.frequencyTest;


public class GUI extends javax.swing.JFrame {

    static JFrame frame;
    static GridBagConstraints grid = new GridBagConstraints();
    private static File pathToWavFile;
    private static Recorder recorder = new Recorder();

    public static JFrame createFrame(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ignored){}
        frame = new JFrame();
        frame.setDefaultCloseOperation(GUI.EXIT_ON_CLOSE);
        frame.setTitle("ChoppyMic");
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //frame.setResizable(false);
        return frame;
    }

    public static void Revalidate(){
        frame.repaint();
        frame.revalidate();
    }

    public static void Delay(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void displayInstruction(JPanel panel, JLabel label, String instructionTest, int time, int countdown){
        label.setText(instructionTest);
        Revalidate();
        Countdown(panel, countdown);
        Delay(time);
    }

    public static void Countdown(JPanel container, int count) {
        JPanel countdown = new JPanel(new GridBagLayout());
        grid.gridy = 1;
        container.add(countdown, grid);
        JLabel current_number = new JLabel();
        countdown.add(current_number);
        while(count>0){
            current_number.setText(String.valueOf(count));
            Revalidate();
            Delay(1000);
            count--;
        }
        container.remove(countdown);
        Revalidate();
    }

    public static void initialOpening() {
        JPanel initialPanel = new JPanel(new GridBagLayout());
        frame.add(initialPanel);
        grid.gridx = grid.gridy = 0;
        JLabel titleLabel = new JLabel("ChoppyMic");
        ImageIcon icon = new ImageIcon("D:\\Uni\\ce320-08\\trunk\\data\\Logo.jpeg");
        JLabel logo = new JLabel(icon);
        initialPanel.add(titleLabel,grid);
        grid.gridy=1;
        initialPanel.add(logo,grid);
        frame.revalidate();
        Delay(3000);
        frame.remove(initialPanel);
        homePage(false, frame);
    }

    public static void homePage(boolean isFirstRecording, JFrame frame) {
        JPanel homepagePanel = new JPanel(new GridBagLayout());
        grid.gridx = grid.gridy = 0;
        frame.add(homepagePanel);
        JButton startBtn = new JButton("Start Recording");
        if(isFirstRecording==true)
            startBtn.setText("Rerecord");
        startBtn.setBackground(Color.red);
        startBtn.setOpaque(true);
        startBtn.setBorderPainted(false);

        homepagePanel.add(startBtn, grid);
        JButton analyseBtn = new JButton("Analyse");
        analyseBtn.setBackground(Color.white);
        analyseBtn.setOpaque(true);
        analyseBtn.setBorderPainted(false);
        grid.gridy=1;
        homepagePanel.add(analyseBtn, grid);
        Revalidate();

        AtomicInteger buttonState = new AtomicInteger();
        startBtn.addActionListener(e -> {
            buttonState.set(1);
        });

        analyseBtn.addActionListener(e -> {
            buttonState.set(2);
        });
        while (true)
        {
            if(buttonState.get()==1)
            {
                frame.remove(homepagePanel);
                frame.repaint();
                recordingInstructions();
                buttonState.set(0);
                break;
            } else if (buttonState.get() == 2) {

                OutputAnalysis outputAnalysis = new OutputAnalysis();
                frame.remove(homepagePanel);
                //frame.setLayout(new GridLayout(2,1));
                frame.add(outputAnalysis.getAnalyseSilencePanel());
                //frame.pack();
                Revalidate();
                //frame.setSize(1000,1000);

                //frame.setLayout(null);


/*
                try {
                    Waveform waveform = new Waveform(new Waveform_Y_Axis(VolumeTests.createVolume(recorder.getFilePath()), 600, MIDDLE));
                    waveform.setVisible(true);
                    waveform.setBounds(0, 0, waveform.getWidth(), waveform.getHeight());

                    JScrollPane scroll = new JScrollPane(waveform, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                    frame.add(waveform.getYAxis());
                    frame.add(scroll);

                }
                catch (Exception e) {
                    System.out.println("[ERR]: " + e.getMessage());
                    e.printStackTrace();
                }
                Revalidate();

*/
                com.Tests.WaveformTests.WaveformAndAxesTest();

                frequencyTest(1000, 1000);
                buttonState.set(0);
            }

            }
            analyseBtn.setEnabled(isFirstRecording);
    }

    public static JPanel OnScreenInstructions(){
        JPanel guidePanel = new JPanel(new GridBagLayout());
        JLabel[] instruction = new JLabel[5];
        instruction[0] = new JLabel("Do not speak or make noise unless asked to");
        instruction[1] = new JLabel("On the screen a series of words and sounds will be displayed");
        instruction[2] = new JLabel("if a word is displayed say the word once");
        instruction[3] = new JLabel("If a sound is displayed make the sound until told to stop");
        instruction[4] = new JLabel("Read out loud only the text in green");
        for(int i=0;i<instruction.length; i++)
        {
            instruction[i].setFont(instruction[i].getFont().deriveFont(18f));
            grid.gridy=i;
            guidePanel.add(instruction[i],grid);
        }
        Revalidate();
        return guidePanel;
    }

    public static void recordingInstructions() {
        JPanel instructionsPanel = new JPanel(new GridBagLayout());
        JPanel container = new JPanel(new GridBagLayout());
        JLabel instructionLabel = new JLabel();
        frame.add(container);
        recorder = new Recorder(23);
        recorder.chooseMicrophone(frame);

        Thread recordThread = new Thread() {
            @Override
            public void run() {
                recorder.recordVoice();
                pathToWavFile = recorder.getPathToWavFile();
            }
        };

        grid.gridx=0;
        grid.gridy=0;
        Revalidate();
        container.add(instructionsPanel, grid);

        JPanel rulesForUser = OnScreenInstructions();
        container.add(rulesForUser);
        Revalidate();
        Delay(4000);
        container.remove(rulesForUser);

        recordThread.start();
        grid.gridy = 0;
        instructionsPanel.add(instructionLabel);
        instructionLabel.setFont(instructionLabel.getFont().deriveFont(32f));
        instructionLabel.setForeground(Color.red);

        displayInstruction(container, instructionLabel, "Stay silent", 3000, 3);
        instructionLabel.setFont(instructionLabel.getFont().deriveFont(24f));
        instructionLabel.setForeground(Color.green);


        displayInstruction(container, instructionLabel, "Apple", 3000, 3);
        displayInstruction(container, instructionLabel, "Banana", 3000, 3);
        displayInstruction(container, instructionLabel, "Popsicle", 3000, 3);
        //instructionLabel.setText("Stop");
        Revalidate();
        //Delay(3000);
        recordThread.stop();
        instructionsPanel.remove(instructionLabel);
        container.remove(instructionsPanel);
        frame.remove(container);
        boolean isFirstRecording = true;
        homePage(isFirstRecording,frame);
        Revalidate();
    }

    public static void main(String[] args){
        JFrame frame = createFrame();
        frame.revalidate();
        initialOpening();
    }
}