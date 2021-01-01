package com.Tests;

import com.Volume;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class VolumeTests {
    public static Volume createVolume(String path) throws IOException {
        File file = new File(path);
        byte[] bytesFromFile = Files.readAllBytes(file.toPath());
        return new Volume(Volume.getAudioFormat(), bytesFromFile);
    }

    @Test
    public void volumeTest() {
        try {
            Volume volume = createVolume("AudioFiles\\one_two_three.wav");
        }
        catch (IOException e) {
            System.out.println("[ERR]: " + e.getMessage());
        }
    }

}
