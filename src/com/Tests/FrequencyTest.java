package com.Tests;

import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FrequencyTest {
@Test

public void testSoundLength(){
    assertEquals(com.AnalyseMicrophone.Frequency.getSoundLength((long) 8.0f, 4.0f), 2.0 );
}

@Test
    public void TestDistance() {assertEquals(com.AnalyseMicrophone.Frequency.getDistancePoints(10,10),50);}

@Test
public void TestIntervals() {assertEquals(com.AnalyseMicrophone.Frequency.getintervalSeconds(5000,50),100);}


}