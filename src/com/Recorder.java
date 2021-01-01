package com;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Recorder{
    private TargetDataLine dataLine;
    private boolean stopped = false;
    private final int seconds;
    private AudioInputStream ais;
    private final File AudioFile = new File(System.getProperty("user.dir") + "\\Test.wav");
    private final AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private final AudioFormat format;
    private Line.Info lineInfo;
    private byte[] recordingData;

    private static class Info {
        String string; Line.Info info;
        public Info(String string, Line.Info info){
            this.string = string; this.info=info;
        }
        public Line.Info getInfo() {return info;}
        public String getString() {return string;}
    }


    public File getPathToWavFile(){return AudioFile;}

    public byte[] getByteArray(){return recordingData;}

    private void addMicrophoneInfos(ArrayList<Info> strings, Line.Info[] lineInfos){

    }

    private ArrayList<Info> getListOfMicrophones(){

        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        ArrayList <Info>strings = new ArrayList<>();

        for (Mixer.Info info: mixerInfos){
            Mixer m = AudioSystem.getMixer(info);
            Line.Info[] lineInfos = m.getTargetLineInfo();
            for (Line.Info lineInfo:lineInfos) {
                if (lineInfos.length >= 1 && lineInfos[0].getLineClass().equals(TargetDataLine.class)) {
                    strings.add(new Info(info.getName(), lineInfo));
                }
            }
        }
        return strings;
    }


    private String showOptionPane(JFrame frame, String[] strings){
        String input = (String) JOptionPane.showInputDialog(frame,
                "Please choose a microphone",
                "Microphone selector:",
                JOptionPane.QUESTION_MESSAGE,
                null,
                strings,
                strings[0]);
        return input;
    }

    private String[] getMicrophoneList(){
        ArrayList<Info> infoList = getListOfMicrophones();
        String[] returnString = new String[infoList.size()];
        for (int i = 0; i < infoList.size(); i++) {
            returnString[i] = infoList.get(i).getString();
        }
        return returnString;

    }

    public void chooseMicrophone(JFrame frame){

        String [] strings = getMicrophoneList();
        String input = showOptionPane(frame, strings);
        for (Info i : getListOfMicrophones()){
            if (i.string.equals(input)){
                this.lineInfo =i.getInfo();
            }
        }
    }


    public Recorder() {
        this(0);
    }
    public Recorder(int seconds){
        this.seconds = seconds;
        format = getAudioFormat();
        lineInfo = new DataLine.Info(TargetDataLine.class,format);
        try {
            System.out.println("Trying to create a file at " + AudioFile.getAbsolutePath() + "...");
            if (AudioFile.createNewFile()) {
                System.out.println("Created a new " + AudioFile.getName() + " file.");
            }
            else {
                System.out.println("File already exists.");
            }
        }
        catch (IOException e) {
            System.out.println("Was not able to create the new file.");
            System.out.print("Reason: ");
            e.printStackTrace();
        }
    }

    public AudioFormat getAudioFormat() {
        float sampleRate = 176400;
        int sampleSizeInBits = 16;
        int channels = 2;
        return new AudioFormat(sampleRate, sampleSizeInBits,
                channels, true, true);
    }

    private void stopRecording() throws IOException {
        stopped = true;
        ais.close();
    }

    private final Thread timeRecording = new Thread(new Runnable() {
        @Override
        public void run() {
            try { Thread.sleep(seconds * 1000); }
            catch (InterruptedException e) { e.printStackTrace();}
            try { stopRecording();}
            catch (IOException e) { e.printStackTrace(); }
        }
    });

    private AudioInputStream initialiseAudioStream() {
        if (!AudioSystem.isLineSupported(new DataLine.Info(TargetDataLine.class, format))) {
            System.out.println("Line not supported");
            System.exit(0);
        }

        try {
            try { dataLine = (TargetDataLine) AudioSystem.getLine(lineInfo); }
            catch (LineUnavailableException e) { e.printStackTrace();}
            dataLine.open(format);
            ais = new AudioInputStream(dataLine);
        }
        catch (LineUnavailableException e) { e.printStackTrace(); }

        return ais;
    }


    public void recordVoice(){
        ais = initialiseAudioStream();

        int numBytesRead;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        recordingData = new byte[dataLine.getBufferSize()/5];
        try {
            System.out.println("Starting Recording");
            dataLine.start();
            timeRecording.start();
            while (!stopped) {
                numBytesRead = dataLine.read(recordingData, 0, recordingData.length);
                stream.write(recordingData, 0, numBytesRead);
                AudioSystem.write(ais, fileType, AudioFile);
            }
            timeRecording.join();

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finishing Recording");
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Recorder recorder = new Recorder(7);

        JFrame Frame = new JFrame("Select Microphone Input:");
        Frame.setSize(500, 500);
        Frame.setLocationRelativeTo(null);
        Frame.setVisible(true);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        recorder.chooseMicrophone(Frame);
        recorder.recordVoice();

    }

    public String getFileName() {
        return AudioFile.getName();
    }
    public String getFilePath() {
        return AudioFile.getAbsolutePath();
    }
}

