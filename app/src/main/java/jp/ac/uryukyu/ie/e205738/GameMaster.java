package jp.ac.uryukyu.ie.e205738;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class GameMaster {
    Random random = new Random();
    private int numHandCard = 5;
    private int allCards = 52;
    Poker poker = new Poker();
    
    public ArrayList<ArrayList<String>> combination(ArrayList<ArrayList<String>> hand,int numMember) {
        for (int i = 0; i < numHandCard; i++) {
            int dealCards = random.nextInt(allCards - i-numHandCard*numMember);
            ArrayList<String> playercard = poker.getCardList().get(dealCards);
            hand.add(playercard);
            poker.getCardList().remove(dealCards);
        }    
        return hand;
    }

    public ArrayList<ArrayList<String>> change(ArrayList<ArrayList<String>> hand,int numMember){
        System.out.println("交換したいカードはどれですか？");
        String sentence = "";
        for (int i = 0; i < hand.size(); i++) {
            String line = String.valueOf(i) + "." + String.valueOf(hand.get(i));
            sentence += line + " ";
        }
        sentence += "5.交換しない";
        System.out.println(sentence);
        Scanner scan = new Scanner(System.in);
        int num = scan.nextInt();
        if (num == 5) {
            scan.close();
            return hand;
        } else {
            try {
                ArrayList<String> trashCard = new ArrayList<>(hand.get(num));
                ArrayList<String> addCard = new ArrayList<>(
                        poker.getCardList().get(random.nextInt(allCards - numHandCard*numMember - 1)));
                hand.remove(num);
                hand.add(addCard);
                poker.addCardToList(trashCard);
                poker.pickCardFromList(addCard);
                scan.close();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                System.out.println("入力した値が適切でありません。");
                System.out.println("0~5までの数字を一つ入力してください");

            }
        }
        return hand;

    }

    public String equalFourCardReferee(ArrayList<ArrayList<String>> player, ArrayList<ArrayList<String>> cpu) {
        ArrayList<Integer> numPlayer = sortNumber(player);
        ArrayList<Integer> numCpu = sortNumber(cpu);
        int matchPlayer = serchMatching(numPlayer, 3).get(0);
        int matchCpu = serchMatching(numCpu, 3).get(0);
        if (matchPlayer != 1 && matchCpu != 1) {
            if (matchPlayer > matchCpu) {
                return "Player Win!";
            } else if (matchPlayer == matchCpu) {
                return "Draw!";
            } else {
                return "Cpu Win!";
            }
        } else if (matchPlayer == 1 && matchCpu == 1) {
            return "Draw!";
        } else {
            if (matchPlayer == 1) {
                return "Player Win!";
            } else {
                return "Cpu Win!";
            }
        }
    }

    public String equalStraightFlushReferee(ArrayList<ArrayList<String>> player, ArrayList<ArrayList<String>> cpu) {
        ArrayList<Integer> numPlayer = sortNumber(player);
        ArrayList<Integer> numCpu = sortNumber(cpu);
        if (poker.Judgment(player) == "RoyalStraightFlush!") {
            return "Draw!";
        } else {
            if (!numPlayer.contains(1) && !numCpu.contains(1)) {
                if (numPlayer.get(4) > numCpu.get(4)) {
                    return "Player Win!";
                } else if (numPlayer.get(4) == numCpu.get(4)) {
                    return "Draw!";
                } else {
                    return "Cpu Win!";
                }
            } else if (numPlayer.contains(1) && numCpu.contains(1)) {
                return "Draw!";
            } else {
                if (numPlayer.contains(1)) {
                    return "Player Win!";
                } else {
                    return "Cpu Win!";
                }
            }
        }
    }

    public String equalThreeCardFullHouseReferee(ArrayList<ArrayList<String>> player,ArrayList<ArrayList<String>> cpu) {
        ArrayList<Integer> numPlayer = sortNumber(player);
        ArrayList<Integer> numCpu = sortNumber(cpu);
        int matchPlayer = serchMatching(numPlayer, 2).get(0);
        int matchCpu = serchMatching(numCpu, 2).get(0);
        if (matchPlayer != 1 && matchCpu != 1) {
            if (matchPlayer > matchCpu) {
                return "Player Win!";
            } else if (matchPlayer == matchCpu) {
                return "Draw!";
            } else {
                return "Cpu Win!";
            }
        } else if (matchPlayer == 1 && matchCpu == 1) {
            return "Draw!";
        } else {
            if (matchPlayer == 1) {
                return "Player Win!";
            } else {
                return "Cpu Win!";
            }
        }
    }

    public String equalTwopairReferee(ArrayList<ArrayList<String>> player , ArrayList<ArrayList<String>> cpu){
        ArrayList<Integer> numPlayer= sortNumber(player);
        ArrayList<Integer> numCpu = sortNumber(cpu);
        ArrayList<Integer> matchPlayer = serchMatching(numPlayer, 1);
        ArrayList<Integer> matchCpu= serchMatching(numCpu, 1);
        ArrayList<ArrayList<Integer>> matchLists =new ArrayList<>();
        matchLists.add(matchPlayer);
        matchLists.add(matchCpu);
        ArrayList<Integer> battleNum=new ArrayList<>();
        if(matchPlayer.contains(1)&&matchCpu.contains(1)){
            for(ArrayList<Integer> a: matchLists){
                for(int i : a){
                    if(i!=1){
                        battleNum.add(i);
                    }
                }
            }
            if(battleNum.get(0)>battleNum.get(1)){
                return "Player Win!";
            }else if(battleNum.get(0)==battleNum.get(1)){
                return "Draw!";
            }else{
                return "Cpu Win!";
            }
        }else if(!matchPlayer.contains(1)&&!matchCpu.contains(1)){
            for (ArrayList<Integer> a : matchLists){
                if (a.get(0)>a.get(1)){
                    battleNum.add(a.get(0));
                }else{
                    battleNum.add(a.get(1));
                    }
                }
                if(battleNum.get(0)>battleNum.get(1)){
                    return "Player Win!";
                }else if(battleNum.get(0)==battleNum.get(1)){
                    return "Draw!";
                }else{
                    return "Cpu Win!";
                }
        }else{
            if(matchPlayer.contains(1)){
                    return "Player Win!";
                }else{
                    return "Cpu Win!";
                }
            }
    }
    public String equalOnePairReferee(ArrayList<ArrayList<String>> player , ArrayList<ArrayList<String>> cpu){
        ArrayList<Integer> numPlayer= sortNumber(player);
        ArrayList<Integer> numCpu = sortNumber(cpu);
        ArrayList<Integer> matchPlayer = serchMatching(numPlayer, 1);
        ArrayList<Integer> matchCpu= serchMatching(numCpu, 1);
        ArrayList<ArrayList<Integer>> matchLists =new ArrayList<>();
        matchLists.add(matchPlayer);
        matchLists.add(matchCpu);
        if(matchPlayer.contains(1)&&matchCpu.contains(1)){
            return "Draw!";
        }else if(!matchPlayer.contains(1)&&!matchCpu.contains(1)){
            if(matchPlayer.get(0)>matchCpu.get(0)){
                return "Player Win!";
            }else if(matchPlayer.get(0)==matchCpu.get(0)){
                return "Draw!";
            }else{
                return "Cpu Win!";
            }
        }else{
            if(matchPlayer.get(0)==1){
                return "Player Win!";
            }else{
                return "Cpu Win!";
            }
        }
    }
    public String equalNopairReferee(ArrayList<ArrayList<String>> player, ArrayList<ArrayList<String>> cpu){
        ArrayList<Integer> numPlayer= sortNumber(player);
        ArrayList<Integer> numCpu = sortNumber(cpu);
        ArrayList<ArrayList<Integer>> numbers = new ArrayList<>();
        numbers.add(numPlayer);
        numbers.add(numCpu);
        ArrayList<Integer> battleNumbers = new ArrayList<>();
        for(ArrayList<Integer> a : numbers){
            int num=0;
            for(int i : a){
                if(i>num){
                    num=i;
                }
            }
            battleNumbers.add(num);
        }
        if(battleNumbers.get(0)>battleNumbers.get(1)){
            return "Player Win!";
        }else if(battleNumbers.get(0)==battleNumbers.get(1)){
            return "Draw!";
        }else{
            return "Cpu Win!";
        }
    }    

    public String equalReferee(ArrayList<ArrayList<String>> player, ArrayList<ArrayList<String>> cpu) {
        String judge;
        if(poker.Judgment(player)=="RoyalStraightFlush!"||poker.Judgment(player)=="Straight!"||poker.Judgment(player)=="Flush!"||poker.Judgment(player)=="StraghtFlush!"){
            judge=equalStraightFlushReferee(player, cpu);
            return judge;
        }else if(poker.Judgment(player)=="Four Cards!"){
            judge=equalFourCardReferee(player, cpu);
            return judge;
        }else if(poker.Judgment(player)=="Three Cards!"||poker.Judgment(player)=="Full House!"){
            judge=equalThreeCardFullHouseReferee(player, cpu);
            return judge;
        }else if(poker.Judgment(player)=="Two pair!"){
            judge=equalTwopairReferee(player, cpu);
            return judge;
        }else if(poker.Judgment(player)=="One pair!"){
            judge=equalOnePairReferee(player, cpu);
            return judge;
        }else{
            judge=equalNopairReferee(player, cpu);
            return judge;
        }   
    }
    public String differentReferee(ArrayList<ArrayList<String>> player, ArrayList<ArrayList<String>> cpu){
        ArrayList<Integer> judge = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<String>>> hands = new ArrayList<>();
        hands.add(player);
        hands.add(cpu);
        for (ArrayList<ArrayList<String>> a : hands){
            if(poker.Judgment(a)=="RoyalStraightFlush!"){
                judge.add(1);
            }else if(poker.Judgment(a)=="StraightFlush!"){
                judge.add(2);
            }else if(poker.Judgment(a)=="Four Cards!"){
                judge.add(3);
            }else if(poker.Judgment(a)=="Full House!"){
                judge.add(4);
            }else if(poker.Judgment(a)=="Flush!"){
                judge.add(5);
            }else if(poker.Judgment(a)=="Straight!"){
                judge.add(6);
            }else if(poker.Judgment(a)=="Three Cards!"){
                judge.add(7);
            }else if(poker.Judgment(a)=="Two pair!"){
                judge.add(8);
            }else if(poker.Judgment(a)=="One pair!"){
                judge.add(9);
            }else{
                judge.add(10);
            }
        }
        if(judge.get(0)<judge.get(1)){
            return "Player Win!";
        }else{
            return "Cpu Win!";
        }
    }
    public String referee(ArrayList<ArrayList<String>> player, ArrayList<ArrayList<String>> cpu){
        String judge;
        if(poker.Judgment(player)==poker.Judgment(cpu)){
            judge=equalReferee(player, cpu);
            return judge;
        }else{
            judge=differentReferee(player, cpu);
            return judge;
        }
    }

    public ArrayList<Integer> sortNumber(ArrayList<ArrayList<String>> hand) {
        ArrayList<Integer> sortedNum = new ArrayList<>();
        for (ArrayList<String> a : hand) {
            sortedNum.add(Integer.parseInt(a.get(1)));
        }
        Collections.sort(sortedNum);
        return sortedNum;
    }

    public ArrayList<Integer> serchMatching(ArrayList<Integer> numberList, int matchtime) {
        ArrayList<Integer> matchNumber = new ArrayList<>();
        for (int i : poker.matchTime(numberList)) {
            if (i == matchtime) {
                matchNumber.add(numberList.get(i));
            }
        }
        return matchNumber;
    }
}