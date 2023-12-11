import java.util.*;
class Solution {
    public int solution(int[] bandage, int health, int[][] attacks) {
        int answer = 0;
        int chealth = health;
        int aidx = 0;
        int max_time = attacks[attacks.length - 1][0];
        int band_cnt = 0;
        for(int t = 1; t<=max_time; ++t){
            if(attacks[aidx][0] == t){
                chealth -= attacks[aidx][1];
                aidx++;
                band_cnt = 0;
                if(chealth <= 0){
                    break;
                }
            }
            else{
                chealth = Math.min(health,chealth+bandage[1]);
                band_cnt += 1;
                if(band_cnt == bandage[0]){
                    chealth = Math.min(health,chealth+bandage[2]);
                    band_cnt = 0;
                }
            }
        }
        if(chealth < 1){
            chealth = -1;
        }
        return chealth;
    }
}