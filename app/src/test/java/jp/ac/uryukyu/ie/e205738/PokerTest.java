package jp.ac.uryukyu.ie.e205738;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class PokerTest {
    @Test
    void JudgementRoyalStraightFlushTest(){
        Poker poker = new Poker();
        ArrayList<String> demo = new ArrayList<>(Arrays.asList("1","13","12","11","10"));
        assertEquals(poker.JudgementRoyalStraightFlush(demo), true);
    }
}
