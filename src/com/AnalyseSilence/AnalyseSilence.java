package com.AnalyseSilence;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

public class AnalyseSilence {
    Calculation calculation = new Calculation();
    String userAudioFilePath;

    public AnalyseSilence(String userAudioFilePath) {
        this.userAudioFilePath = userAudioFilePath;
    }

    public double sdOfGoodMic(int[] goodMicByteArray){
        return Calculation.standardDeviation(goodMicByteArray);
    }

    public double sdOfBadMic(int[] badMicByteArray){
        return Calculation.standardDeviation(badMicByteArray);
    }

    public double sdOfUserMic(int[] userMicByteArray){
        return Calculation.standardDeviation(userMicByteArray);
    }

    public double average(double sdOfGoodMic, double sdBadMic){
        return (sdOfGoodMic + sdBadMic) / 2;
    }

    public int evaluationOfUserMic(int[] goodMicByteArray, int[] badMicByteArray, int[] userMicByteArray) {
        double userMicStandardDevi = sdOfUserMic(userMicByteArray);
        double avgSD = average( sdOfGoodMic(goodMicByteArray), sdOfBadMic(badMicByteArray) );
        
        if(userMicStandardDevi < avgSD) {
            return 10;
        }
        else if(userMicStandardDevi > avgSD) {
            return 0;
        }
        else {
            return 5;
        }
    }

    public int[] convertWAVtoBytes(String wavFilePathName) {
        try {
            File file =new File(wavFilePathName);
            byte[] bytes = Files.readAllBytes(file.toPath());
            int[] bytesToInt = new int[bytes.length];

            // loop through the bytes[] and add each element to bytesToInt[]
            for (int i =0 ;i < bytes.length; i++) {
                bytesToInt[i] = bytes[i];
            }
            int theFirstThree = bytesToInt.length/8;
            int[] threeSecSilence = new int[theFirstThree];

            for(int i = 0; i < theFirstThree; i++) {
                threeSecSilence[i] = bytesToInt[i];
            }

            return threeSecSilence;

        }
        catch (Exception e) {

        }
        // If you remove the code below it will break
        int[] temporary = new int[0];
        return temporary;
    }

    public int finalMethod() {
        String badMicPath = "AudioFiles\\bad_mic_silence.wav";
        String goodMicPath = "AudioFiles\\good_mic_silence.wav";
        String userMicPath = userAudioFilePath;
        int[] goodMicBytes = convertWAVtoBytes(goodMicPath);
        int[] badMicBytes = convertWAVtoBytes(badMicPath);
        int[] userMicBytes = convertWAVtoBytes(userMicPath);

        int results = evaluationOfUserMic(goodMicBytes,badMicBytes,userMicBytes);
        return results;
    }

    public static void main(String[] args) {

        AnalyseSilence analyseSilence = new AnalyseSilence("AudioFiles\\user_mic.wav");
        int s = analyseSilence.finalMethod();

        System.out.println(s);
    }

    public static void run(String fileName){
        AnalyseSilence analyseSilence = new AnalyseSilence(fileName);
        int s = analyseSilence.finalMethod();

        System.out.println(s);
    }
}
