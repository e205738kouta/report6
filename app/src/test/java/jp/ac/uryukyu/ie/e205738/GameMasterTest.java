package jp.ac.uryukyu.ie.e205738;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class GameMasterTest {
    @Test
    void serchMatchingTest(){
        GameMaster gameMaster=new GameMaster();
        ArrayList<Integer> demo1=new ArrayList<>(Arrays.asList(1,1,1,2,2));
        ArrayList<Integer> demo2=new ArrayList<>(Arrays.asList(13,13,12,11,10));
        assertEquals(gameMaster.serchMatching(demo1, 2).get(0),1);
        assertEquals(gameMaster.serchMatching(demo2, 1).get(0),13);

    }
    
}
