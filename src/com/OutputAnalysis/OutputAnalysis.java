package com.OutputAnalysis;
import com.AnalyseSilence.AnalyseSilence;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class OutputAnalysis {
    ArrayList<Integer> scores = new ArrayList<>();

    public int aveMark(int value){
        if (scores.size() == 0 ){
            return 0;
        }
        int total = 0;
        for (int i : scores){
            total += i;
        }
        return (int)total / scores.size();
    }

    public JPanel getAnalyseSilencePanel(){
        JPanel returnPanel = new JPanel();

        AnalyseSilence analyseSilence = new AnalyseSilence("Test.wav");
        int score = analyseSilence.finalMethod();
        //int score = 0;

        JLabel title = new JLabel("Background Noise:");
        title.setBounds(400,0, 1000, 100 );
        title.setFont(title.getFont().deriveFont(32.0f));
        returnPanel.add(title);

        JLabel averageMark = new JLabel(String.valueOf(score) + "/10");
        averageMark.setBounds(400, 400, 1000, 100);
        averageMark.setFont(title.getFont().deriveFont(128.0f));

        String goodmic = "Good Quality; cannot detect much background noise";
        String badmic = "Bad Quality; detecting too much background noise";
        String averageMic = "Average Quality: Detecting an acceptable amount of background noise";
        if(score > 5){
            JLabel subTitle = new JLabel(goodmic);
            subTitle.setBounds(200,600, 1000, 100 );
            returnPanel.add(subTitle);
            returnPanel.setBackground(Color.green);
        } else if (score < 5) {
            JLabel subTitle = new JLabel(badmic);
            subTitle.setBounds(200,600, 1000, 100 );
            returnPanel.add(subTitle);
            returnPanel.setBackground(Color.red);
        }
        else {
            JLabel subTitle = new JLabel(averageMic);
            subTitle.setBounds(200,600, 1000, 100 );
            returnPanel.add(subTitle);
        }

        returnPanel.updateUI();
        returnPanel.add(averageMark);

        scores.add(score);

        return returnPanel;
    }

    public static void main(String[] args) {
        OutputAnalysis outputAnalysis = new OutputAnalysis();

        JFrame frame = new JFrame("Output");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2,1));

        frame.add(outputAnalysis.getAnalyseSilencePanel());
        frame.add(outputAnalysis.getAnalyseSilencePanel());

        frame.pack();
        frame.repaint();

        frame.setSize(1000,1000);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
