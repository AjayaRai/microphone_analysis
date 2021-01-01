package com.Tests;

import com.AnalyseSilence.AnalyseSilence;
import com.OutputAnalysis.OutputAnalysis;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvgMarkTests {
 OutputAnalysis outputAnalysis = new OutputAnalysis();

 @Test
 public void test(){
  int value = outputAnalysis.aveMark(2);
  assertEquals(0, value); //Expected can be < or >? for when mark has existing value
 }

 @Test
 public void test2(){
  outputAnalysis.aveMark(0);
  int value = outputAnalysis.aveMark(10);
  assertEquals(0, value);
 }

 @Test
 public void test3(){
  outputAnalysis.aveMark(0);
  outputAnalysis.aveMark(5);
  outputAnalysis.aveMark(5);
  outputAnalysis.aveMark(10);
  int value = outputAnalysis.aveMark(5);

  assertEquals(0, value);
 }

 }
