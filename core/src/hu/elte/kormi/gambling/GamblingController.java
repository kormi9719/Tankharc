package hu.elte.kormi.gambling;

import hu.elte.kormi.helpers.GameInfo;

import java.util.ArrayList;
import java.util.Random;

public class GamblingController {

    public static ArrayList<Integer> gamble(){

        int result;
        Random rand = new Random();
        ArrayList<Integer> sequence = new ArrayList<>();

        int rand_int = rand.nextInt(GameInfo.COMMON_GAMBLING_RARITY + GameInfo.RARE_GAMBLING_RARITY + GameInfo.ULTRA_RARE_GAMBLING_RARITY + 1);

        if(rand_int > GameInfo.COMMON_GAMBLING_RARITY + GameInfo.RARE_GAMBLING_RARITY + GameInfo.ULTRA_RARE_GAMBLING_RARITY){
            result = 2;
        } else if (rand_int > GameInfo.COMMON_GAMBLING_RARITY + GameInfo.RARE_GAMBLING_RARITY){
            result = 1;
        } else {
            result = 0;
        }

        for(int i = 0; i < 100; i++){
            rand_int = rand.nextInt(3);
            sequence.add(rand_int);
            sequence.add(rand_int);
            sequence.add(rand_int);
        }

        sequence.add(result);

        return sequence;
    }
}
