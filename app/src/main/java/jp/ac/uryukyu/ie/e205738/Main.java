package jp.ac.uryukyu.ie.e205738;

import java.util.ArrayList;

public class Main {
    public static void main(String args[]){
        Poker poker =new Poker();
        GameMaster gameMaster = new GameMaster();
        ArrayList<ArrayList<String>> hand = gameMaster.combination();
        System.out.println(hand);
        System.out.println(poker.Judgment(hand));
    }
}
