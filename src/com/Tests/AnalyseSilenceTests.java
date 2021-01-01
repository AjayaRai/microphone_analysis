package com.Tests;

import com.AnalyseSilence.AnalyseSilence;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalyseSilenceTests {
    AnalyseSilence analyseSilence = new AnalyseSilence("AudioFiles\\user_mic.wav");

    int[] goodMicByteArray = {0,0,0};
    int[] badMicByteArray = {-10,0,10};

    @Test
    public void goodMic(){
        int[] userMicByteArray = {0,0,1};
        int s = analyseSilence.evaluationOfUserMic(goodMicByteArray,badMicByteArray,userMicByteArray);
        assertEquals(10, s);
    }

    @Test
    public void badMic(){
        int[] userMicByteArray = {-90,0,10};
        int s = analyseSilence.evaluationOfUserMic(goodMicByteArray,badMicByteArray,userMicByteArray);
        assertEquals(0, s);
    }

    @Test
    public void avgMic(){
        int[] userMicByteArray = {-5,0,5};
        int s = analyseSilence.evaluationOfUserMic(goodMicByteArray,badMicByteArray,userMicByteArray);
        assertEquals(5, s);
    }
}
