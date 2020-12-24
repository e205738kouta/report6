package jp.ac.uryukyu.ie.e205738;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Poker {
    public Poker() {
        try {
            File file = new File("/Users/nomurakouta/prog2/report6/Cards.txt");
            FileReader filereader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(filereader);
            try {
                String line;
                while ((line=bufferedReader.readLine()) != null){
                    System.out.println(line);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
