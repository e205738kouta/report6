package jp.ac.uryukyu.ie.e205738;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Poker {
    private ArrayList<ArrayList<String>> cardList = new ArrayList<>();

    public Poker() {
        makePlayingCards();
    }

    public void makePlayingCards() {
        try {
            File file = new File("/Users/nomurakouta/prog2/report6/Cards.txt");
            FileReader filereader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(filereader);
            try {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    ArrayList<String> card = new ArrayList<String>();
                    for (String s : line.split(" ")) {
                        card.add(s);
                    }
                    cardList.add(card);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<String>> getCardList() {
        return this.cardList;
    }
    public void addCardToList(ArrayList<String> card){
        this.cardList.add(card);
    }
    public void pickCardFromList(ArrayList<String> card){
        this.cardList.remove(card);
    }

    public String Judgment(ArrayList<ArrayList<String>> hand) {
        ArrayList<String> number = new ArrayList<>();
        ArrayList<String> suit = new ArrayList<>();
        for (ArrayList<String> card : hand) {
            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    suit.add(card.get(i));
                } else {
                    number.add(card.get(i));
                }
            }
        }
        boolean judgedRoyalStraightFlush = JudgementRoyalStraightFlush(number);
        boolean judgedSuit = JudgmentSuit(suit);
        String judgedNumber = judgementNumber(number);
        if(judgedRoyalStraightFlush){
            return "RoyalStraightFlush!";
        }else if(judgedSuit){
            if (judgedNumber=="straight"){
                return "StraightFlush!";
            }else{
                return "Flush!";
            }
        }else if(judgedNumber=="straight"){
            return "Straight!";
        }else if(judgedNumber=="Four Cards"){
            return "Four Cards!";
        }else if(judgedNumber=="Full house"){
            return "Full house!";
        }else if(judgedNumber=="Three Cards"){
            return "Three Cards!";
        }else if(judgedNumber=="Two pair"){
            return "Two pair!";
        }else if(judgedNumber=="One pair"){
            return "One pair!";
        }else{
            return "No pair!";
        }
    }

    public boolean JudgmentSuit(ArrayList<String> suit) {
        if (suit.get(0) == suit.get(1) && suit.get(0) == suit.get(2) && suit.get(0) == suit.get(3) && suit.get(0) == suit.get(4)) {
            return true;
        } else {
            return false;
        }
    }

    public String judgementNumber(ArrayList<String> number){
        ArrayList<Integer> numbersInt = new ArrayList<>();
        for (String s : number) {
            numbersInt.add(Integer.parseInt(s));
        }
        ArrayList<Integer> matchList = matchTime(numbersInt);
        Collections.sort(numbersInt);
        if (numbersInt.get(0)+1==numbersInt.get(1) && numbersInt.get(1)+1==numbersInt.get(2) && numbersInt.get(2)+1==numbersInt.get(3) && numbersInt.get(3)+1==numbersInt.get(4)){
            return "straight";
        }
        
        if (matchList.contains(3)){
            return "Four Cards";   
        }else if(matchList.contains(2)){
            int pair=0;
            for(int i :matchList){
                if(i==1){
                    pair+=1;
                }
            }
            if(pair==2){
                return "Full house";
            }else{
                return "Three Cards";
            }
        }else if(matchList.contains(1)){
            int pair=0;
            for(int i : matchList){
                if(i==1){
                    pair+=1;
                }
            }    
            if(pair==2){
                return "Two pair";
            }else{
                return "One pair";
            }   
        }else{
            return "No pair";
        }
    }

    public boolean JudgementRoyalStraightFlush(ArrayList<String> number) {
        ArrayList<Integer> numbersInt = new ArrayList<>();
        for (String s : number) {
            numbersInt.add(Integer.parseInt(s));
        }
        Collections.sort(numbersInt);
        if (numbersInt.get(0) == 1 && numbersInt.get(1) == 10 && numbersInt.get(2) == 11 && numbersInt.get(3) == 12 && numbersInt.get(4) == 13) {
            return true;
        }else{
            return false;
        }
    }
    public ArrayList<Integer> matchTime(ArrayList<Integer> number){
        ArrayList<Integer> matchList = new ArrayList<>();
        for(int i=0; i<number.size(); i++){
            int match=0;
            for (int j=i+1; j<number.size(); j++){
                if (number.get(i)==number.get(j)){
                    match+=1;
                } 
            }
            matchList.add(match);
        }
        return matchList;
    }
}
