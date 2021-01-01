package com.Tests;

import com.AnalyseVoice.AnalyseVoiceClass;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AnalyseVoiceClassTest {
    AnalyseVoiceClass analyseVoiceClass= new AnalyseVoiceClass("AudioFiles\\user_mic.wav");

    @Test
    public void goodMic(){
        int[] userMicByteArray = {60,51,-51};
        String s= analyseVoiceClass.Evaluation(userMicByteArray);
        assertEquals("good_mic", s);
    }

    @Test
    public void badMic(){
        int[] userMicByteArray = {185,-101,120};
        String s= analyseVoiceClass.Evaluation(userMicByteArray);
        assertEquals("bad_mic", s);
    }
}
//Test
