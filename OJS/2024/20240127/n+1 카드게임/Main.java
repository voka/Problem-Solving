import java.util.*;
class Solution {
    static int start, n, myCoin, canRound = 0;
    static Set<Integer> mySet = new HashSet<>();
    public int solution(int coin, int[] cards) {
        int answer = 0;
        myCoin = coin;
        n = cards.length;
        start = n/3;
        for(int i=0;i<start;++i){
            mySet.add(cards[i]);
        }
        howManyRoundICanProcess(); // 앞으로 버틸 수 있는 라운드 수
        process(cards); // 최대로 버틸 수 있는 라운드 수 구하기
        return canRound + 1;
    }
    public void howManyRoundICanProcess(){
        canRound = 0;
        List<Integer> removeList = new ArrayList<>();
        boolean[] visited = new boolean[n+1];
        for(int k : mySet){
            if(visited[k]) continue;
            int target = n + 1 - k;
            if(mySet.contains(target)){
                visited[target] = true;
                canRound++;
            }
        }
        for(int k : removeList){
            mySet.remove(k);
        }
        //System.out.printf("canRound ==> %d\n", canRound);
    }
    public boolean useMyCard(Set<Integer> temp){
        //System.out.println("USE MY CARDS ONLY");
        int canUse = -1;
        for(int one : temp){
            int oneTarget = n+1 - one;
            if(temp.contains(oneTarget)){
                canUse = one;
                break;
            }
        }
        if(canUse != -1){
            temp.remove(canUse);
            int target = n+1 - canUse;
            //System.out.printf("%d, %d\n", canUse, target);
            temp.remove(target);
            return true;
        }
        return false;
    }
    public void process(int[] cards){
    
        Set<Integer> tempSet = new HashSet<>();
        for(int i=0;i<canRound + 1; ++i){
            if(start + i*2 + 1 >= n) break; // 카드 사이즈가 넘어간다면
            if(myCoin == 0) break;// 코인을 다 써도 이미 라운드 수는 증가했기 때문에 종료시킨다.
            int one = cards[i*2 + start];
            int two = cards[i*2 + 1 + start];
            boolean oneUse = false; // 사용 여부
            boolean twoUse = false;
            int oneTarget = n+1 - one;
            int twoTarget = n+1 - two;
            if(mySet.contains(oneTarget) && myCoin >= 1){ // 처음 받았던 카드 한개와 1번 카드 사용 (코인 - 1)
                //System.out.println("USE ONE COIN");
                //System.out.printf("%d, %d\n", one, oneTarget);
                myCoin -= 1;
                mySet.remove(oneTarget);
                oneUse = true;
                canRound += 1;
            }
            if(mySet.contains(twoTarget) && myCoin >= 1){ // 처음 받았던 카드 한개와 2번 카드 사용 (코인 - 1)
                myCoin -= 1;
                //System.out.println("USE ONE COIN");
                //System.out.printf("%d, %d\n", two, twoTarget);
                mySet.remove(twoTarget);
                twoUse = true;
                canRound += 1;
            }
            // 안쓴 카드 저장
            if(!oneUse) tempSet.add(one);
            if(!twoUse) tempSet.add(two); 
            
            if(canRound <= i && myCoin >= 2){// 이전에 나왔던 카드 중 쓰지 않은 카드 2개 사용 (코인 - 2)
                //System.out.println("USE TWO COIN IN TEMP");
                if(useMyCard(tempSet)){
                    myCoin -= 2;
                    canRound += 1;
                }
                
            }
        }
    }
    
}