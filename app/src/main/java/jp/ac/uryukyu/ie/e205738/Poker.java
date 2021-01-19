package jp.ac.uryukyu.ie.e205738;

/**
 * ポーカーの実装。
 * 山札を作る、役を判定するメソッドなどが含まれている。
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Poker {
    /**
     * ポーカーの山札：cardList
     */
    private ArrayList<ArrayList<String>> cardList = new ArrayList<>();
    
    public Poker() {
        makePlayingCards();
    }
    /**
     * 山札を作るメソッド
     */
    public void makePlayingCards() {
        try {
            File file = new File("Cards.txt");
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
    /**
     * 作った山札を得るためのgetterメソッド
     * @return　makePlayingCards()で作った山札。
     */
    public ArrayList<ArrayList<String>> getCardList() {
        return this.cardList;
    }
    /**
     * 手札の交換の際に捨てたカードを山札に戻すメソッド
     * @param card　捨てたカード
     */
    public void addCardToList(ArrayList<String> card){
        this.cardList.add(card);
    }
    /**
     * 手札の交換の際に山札からランダムに手札に加えるカードを山札から消すメソッド
     * @param card　この後交換で手札に追加されるカード
     */
    public void pickCardFromList(ArrayList<String> card){
        this.cardList.remove(card);
    }
    /**
     * 手札がなんの役を成立させているかを判定するメソッド
     * @param hand　役判定をさせる手札
     * @return　できた役をStringで返す
     */
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
    /**
     * 役判定の中でもフラッシュなどのスートの役を判定するメソッド
     * @param suit　判定対象のスートリスト
     * @return　フラッシュか否かをbooleanで返す
     */
    public boolean JudgmentSuit(ArrayList<String> suit) {
        if (suit.get(0) == suit.get(1) && suit.get(0) == suit.get(2) && suit.get(0) == suit.get(3) && suit.get(0) == suit.get(4)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 役判定の中でも大半の数字の役判定を担うメソッド
     * @param number　判定対象の数字リスト
     * @return　判定された役をStringで返す
     */
    public String judgementNumber(ArrayList<String> number){
        ArrayList<Integer> numbersInt = new ArrayList<>();
        for (String s : number) {
            numbersInt.add(Integer.parseInt(s));
        }
        Collections.sort(numbersInt);
        ArrayList<Integer> matchList = matchTime(numbersInt);
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
    /**
     * ロイヤルストレートフラッシュかどうかを判定するメソッド
     * @param number　判定対象の数字リスト
     * @return　ロイヤルストレートフラッシュだったかどうかをbooleanで返す
     */
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
    /**
     * 数字のリストに対して同じものが何個あったか調べるメソッド
     * 三つ同じ数字があった場合、その一つ目の場所は2、二つ目は1、三つ目は0という値が入る。
     * 最終的に返されるリターンに2があれば三つ同じ＝スリーカードかフルハウス、というように利用する。
     * @param number　判定対象の数字リスト
     * @return　与えたリストとサイズが同じmatchListを返す
     */
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
