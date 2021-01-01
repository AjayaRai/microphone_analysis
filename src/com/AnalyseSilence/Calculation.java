package com.AnalyseSilence;

public class Calculation {

    public static double average(int[] x) {
        int n = x.length;               //Quantity
        double sum = 0;
        for (double i : x) {
            sum += i;                     //SUM
        }
        return sum / n;
    }

    // Example: Variance s^2=[（x1-x）^2+（x2-x）^2+......（xn-x）^2]/（n）
    public static double variance(int[] byteArray) {
        int byteArrayLen = byteArray.length;               //Quantity
        double avg = average(byteArray);        //Average
        double result = 0;
        for (double i : byteArray) {
            result += (i - avg) * (i - avg);     //（x1-x）^2+（x2-x）^2+......（xn-x）^2
        }
        return result / byteArrayLen;

    }

    public static double standardDeviation(int[] byteArray) {
        return Math.sqrt(variance(byteArray));
    }

}
