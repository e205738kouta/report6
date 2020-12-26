package jp.ac.uryukyu.ie.e205738;

import java.util.ArrayList;

public class Main {
    public static void main(String args[]){
        Poker poker =new Poker();
        GameMaster gameMaster = new GameMaster();
        ArrayList<ArrayList<String>> playerHand = new ArrayList<>();
        ArrayList<ArrayList<String>> cpuHand = new ArrayList<>();
        playerHand=gameMaster.combination(playerHand, 0);
        cpuHand=gameMaster.combination(cpuHand, 1);
        //System.out.println(poker.getCardList());
        System.out.println(playerHand);
        //System.out.println(poker.getCardList());
        System.out.println(poker.Judgment(playerHand));
        System.out.println(cpuHand);
        System.out.println(poker.Judgment(cpuHand));

        gameMaster.change(playerHand,2);
        System.out.println(playerHand);
        System.out.println(poker.Judgment(playerHand));
        System.out.println(cpuHand);
        System.out.println(poker.Judgment(cpuHand));
        System.out.println(gameMaster.referee(playerHand, cpuHand));
        
    }
}
