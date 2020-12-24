package jp.ac.uryukyu.ie.e205738;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Poker {
    public Poker() {
        ArrayList<ArrayList<String>> cardList = new ArrayList<>();
        try {
            File file = new File("/Users/nomurakouta/prog2/report6/Cards.txt");
            FileReader filereader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(filereader);
            try {
                String line;
                while ((line=bufferedReader.readLine()) != null){
                    ArrayList<String> card = new ArrayList<String>();
                    for (String s : line.split(" ")){
                        card.add(s);
                    }
                    cardList.add(card);
                }
                System.out.println(cardList);
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

  
}
