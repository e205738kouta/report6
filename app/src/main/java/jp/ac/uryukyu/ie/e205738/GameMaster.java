package jp.ac.uryukyu.ie.e205738;
import java.util.ArrayList;
import java.util.Random;
public class GameMaster {
    Random random = new Random();
    public ArrayList<ArrayList<String>> combination() {
        int numHandCard=5;
        int allCards=52;
        ArrayList<ArrayList<String>> playerCards=new ArrayList<ArrayList<String>>();
        Poker poker = new Poker();
        for (int i = 0; i < numHandCard; i++) {
            int dealCards = random.nextInt(allCards-i);
            ArrayList<String> playercard = poker.getCardList().get(dealCards);
            playerCards.add(playercard);
            poker.getCardList().remove(dealCards);
        }
        return playerCards;
    }
}
