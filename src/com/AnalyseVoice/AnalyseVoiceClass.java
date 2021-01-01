package com.AnalyseVoice;

import com.AnalyseSilence.AnalyseSilence;
import com.AnalyseSilence.Calculation;

import java.io.File;
import java.nio.file.Files;

//good mic (50.00-100.00) test values
//bad mic (100.00-200.00)
//average mic ..........
public class AnalyseVoiceClass {

    Calculation calculation = new Calculation();
    String userAudioFilePath;

    public AnalyseVoiceClass(String userAudioFilePath) {
        this.userAudioFilePath = userAudioFilePath;
    }

    public String Evaluation (int[]userMicByteArray)
    {
        double standardDeviation= calculation.standardDeviation(userMicByteArray);
        if(standardDeviation<100.00 && standardDeviation>50.00)
        {
            return "good_mic";
        }
        else if(standardDeviation>100.00)
        {
            return "bad_mic";
        }

        return "Something went wrong";
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

            int[] middleSampleOfBytes = new int[1000];
            int halfLengthOfBytes = bytesToInt.length;

            for(int i = halfLengthOfBytes; i < (halfLengthOfBytes) + 1000; i++) {
                middleSampleOfBytes[i - halfLengthOfBytes] = bytesToInt[i];
            }
            return middleSampleOfBytes;
        }
        catch (Exception e) {

        }
        int[] temporary = new int[0];
        return temporary;
    }

    public String testingMethod()
    {
        String userMicPath = userAudioFilePath;
        int[] userMicBytes = convertWAVtoBytes(userMicPath);

        return Evaluation(userMicBytes);
    }

    public static void main(String[] args) {

        AnalyseSilence analyseSilence = new AnalyseSilence("AudioFiles\\user_mic.wav");
        int s = analyseSilence.finalMethod();

        System.out.println(s);
    }
    /*public static void run(String fileName){
        AnalyseSilence analyseSilence = new AnalyseSilence(fileName);
        String s = analyseSilence.finalMethod();

        System.out.println(s);
    }*/
}
