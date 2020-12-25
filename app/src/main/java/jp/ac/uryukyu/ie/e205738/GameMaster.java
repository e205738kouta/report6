package jp.ac.uryukyu.ie.e205738;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class GameMaster {
    Random random = new Random();
    private int numHandCard=5;
    private int allCards=52;
    Poker poker = new Poker();
    public ArrayList<ArrayList<String>> combination() {
        ArrayList<ArrayList<String>> playerCards=new ArrayList<ArrayList<String>>();
        for (int i = 0; i < numHandCard; i++) {
            int dealCards = random.nextInt(allCards-i);
            ArrayList<String> playercard = poker.getCardList().get(dealCards);
            playerCards.add(playercard);
            poker.getCardList().remove(dealCards);
        }
        return playerCards;
    }
    public ArrayList<ArrayList<String>> change(ArrayList<ArrayList<String>> hand){
        System.out.println("交換したいカードはどれですか？");
        String sentence=""; 
        for (int i =0; i<hand.size(); i++){
            String line = String.valueOf(i)+"."+String.valueOf(hand.get(i));
            sentence+=line+" ";
        }
        sentence+="5.交換しない";
        System.out.println(sentence);
        Scanner scan = new Scanner(System.in);
        int num =scan.nextInt();
        if (num==5){
            scan.close();
            return hand;
        }else{
        ArrayList<String> trashCard = new ArrayList<>(hand.get(num));
        ArrayList<String> addCard = new ArrayList<>(poker.getCardList().get(random.nextInt(allCards-numHandCard-1)));
        hand.remove(num);
        hand.add(addCard);
        poker.addCardToList(trashCard);
        poker.pickCardFromList(addCard);
        scan.close();
        return hand;
        }
    }
}    