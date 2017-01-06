package packt.twitterdatascienceproject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SentimentAnalysisTrainingData {
    
    public static void main(String[] args) {
        try {
            String filename;
            String file;
            String text;
            List<String> lines = Files.readAllLines(Paths.get("C:\\Jenn Personal\\Packt Data Science\\Chapter 12\\Sentiment-Analysis-Dataset\\SentimentAnalysisDataset.csv"),StandardCharsets.ISO_8859_1);
            for(String s : lines){
                String[] oneLine = s.split(",");
                if(Integer.parseInt(oneLine[1])==1){
                    filename = "pos";
                }else{
                    filename = "neg";
                }
                file = oneLine[0]+".txt";
                text = oneLine[3];
                Files.write(Paths.get("C:\\Jenn Personal\\Packt Data Science\\Chapter 12\\review_polarity\\txt_sentoken\\"+filename+"\\"+file), text.getBytes());
            }
          
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
