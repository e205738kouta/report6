package jp.ac.uryukyu.ie.e205738;

/**
 * ポーカーの実装。
 * 手札を配ったり交換したり、勝敗を判定するメソッドなどが含まれている。
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class GameMaster {
    /**
     * 手札の枚数 : numHandCard
     * 山札の初期枚数 : allCards
     */
    Random random = new Random();
    private int numHandCard = 5;
    private int allCards = 52;
    Poker poker = new Poker();
    /**
     * 手札を配るメソッド。
     * @param hand　空のリストを渡している。
     * @param numMember　手札を受け取るのが何番目かを渡す。1番目の場合は0を渡す。
     * @return　作られた手札が返ってくる。
     */
    public ArrayList<ArrayList<String>> combination(ArrayList<ArrayList<String>> hand,int numMember) {
        for (int i = 0; i < numHandCard; i++) {
            int dealCards = random.nextInt(allCards - i-numHandCard*numMember);
            ArrayList<String> playercard = poker.getCardList().get(dealCards);
            hand.add(playercard);
            poker.getCardList().remove(dealCards);
        }    
        return hand;
    }
    /**
     * 手札を一枚交換するメソッド
     * @param hand　交換前の手札を渡す。
     * @param numMember　プレイヤーが何人かを渡す。今回の場合は自分とcpuの2人なので2を渡す。
     * @return　ランダムに一枚交換された後の手札が返ってくる。
     */
    public ArrayList<ArrayList<String>> change(ArrayList<ArrayList<String>> hand,int numMember){
        int num=0;
        System.out.println("交換したいカードはどれですか？");
        String sentence = "";
        for (int i = 0; i < hand.size(); i++) {
            String line = String.valueOf(i) + "." + String.valueOf(hand.get(i));
            sentence += line + " ";
        }
        sentence += "5.交換しない";
        System.out.println(sentence);
        Scanner scan = new Scanner(System.in);
        try{
        num = scan.nextInt();
        }catch(java.util.NoSuchElementException e){
            e.printStackTrace();
        }
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
    /**
     * 両者がフォーカードで被った際の勝敗判定のメソッド。
     * @param player　自分の手札を渡す。
     * @param cpu　cpuの手札を渡す。
     * @return　勝者もしくは引き分けを表すStringが返ってくる。
     */
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
    /**
     * 両者がストレートフラッシュで被った際の勝敗判定のメソッド。
     * @param player　自分の手札を渡す。
     * @param cpu　cpuの手札を渡す。
     * @return　勝者もしくは引き分けを表すStringが返ってくる。
     */
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

    /**
     * 両者がスリーカードまたはフルハウスで被った際の勝敗判定のメソッド。
     * @param player　自分の手札を渡す。
     * @param cpu　cpuの手札を渡す。
     * @return　勝者もしくは引き分けを表すStringが返ってくる。
     */
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
    /**
     * 両者がツーペアで被った際の勝敗判定のメソッド
     * @param player　自分の手札を渡す。
     * @param cpu　cpuの手札を渡す。
     * @return　勝者もしくは引き分けを表すStringが返ってくる。
     */
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
    /**
     * 両者がワンペアで被った際の勝敗判定のメソッド。
     * @param player　自分の手札を渡す。
     * @param cpu　cpuの手札を渡す。
     * @return　勝者もしくは引き分けを表すStringが返ってくる。
     */
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
    /**
     * 両者がノーペア同士だった場合の勝敗判定のメソッド。
     * @param player　自分の手札を渡す。
     * @param cpu　cpuの手札を渡す。
     * @return　勝者もしくは引き分けを表すStringが返ってくる。
     */
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
    /**
     * 今までの両者の役が被った際の判定をまとめたメソッド。
     * @param player　自分の手札を渡す。
     * @param cpu　cpuの手札を渡す。
     * @return　勝者もしくは引き分けを表すStringが返ってくる。
     */
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
    /**
     * 両者の役が違う場合の勝敗判定のメソッド。
     * @param player　自分の手札を渡す。
     * @param cpu　cpuの手札を渡す。
     * @return　勝者もしくは引き分けを表すStringが返ってくる。
     */
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
    /**
     * 今までの勝敗判定をまとめたメソッド。
     * @param player　自分の手札を渡す。
     * @param cpu　cpuの手札を渡す。
     * @return　勝者もしくは引き分けを表すStringが返ってくる。
     */
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
    /**
     * 手札の数字だけに着目して小さい順にソートするメソッド
     * @param hand　手札をそのまま渡す。
     * @return　ソートされた数字のリストが返ってくる。
     */
    public ArrayList<Integer> sortNumber(ArrayList<ArrayList<String>> hand) {
        ArrayList<Integer> sortedNum = new ArrayList<>();
        for (ArrayList<String> a : hand) {
            sortedNum.add(Integer.parseInt(a.get(1)));
        }
        Collections.sort(sortedNum);
        return sortedNum;
    }
    /**
     * 数字のリストから任意数ある数字を見つけるメソッド。
     * @param numberList　捜索対象の数字のリストを渡す。
     * @param matchtime　探して欲しい数字がリストにある個数を渡す。
     * @return　対象の数をリストで返す。ツーペアの場合、二枚あるペアが二組出るのでリストにしている。
     */
    public ArrayList<Integer> serchMatching(ArrayList<Integer> numberList, int matchtime) {
        ArrayList<Integer> matchNumber = new ArrayList<>();
        for (int i=0;i<numberList.size();i++) {
            if (poker.matchTime(numberList).get(i) == matchtime) {
                matchNumber.add(numberList.get(i));
            }
        }
        return matchNumber;
    }
}