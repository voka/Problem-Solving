import java.util.*;

class Pair{
    int win;
    int same;
    public Pair(int win, int same){
        this.win = win;
        this.same = same;
    }
}

class Solution {
    static PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
    static List<List<Integer>> choiceList = new ArrayList<>();
    static List<Integer> tempAnswer = new ArrayList<>();
    static int total, half;
    public int[] solution(int[][] dice) {
        total = dice.length;
        half = total / 2;
        int[] answer = new int[half];
        int[] choiceN = new int[total];
        for(int i=0;i<total;++i){
            choiceN[i] = i;
        }
        boolean[] visited = new boolean[total];
        // 주사위를 고를 수 있는 경우의 수
        combination(choiceN, visited, 0, total, half);
        int myN = choiceList.size() / 2; // 고를 수 있는 경우의 수가 N개 일때 1번을 A가 고르면 자동으로 B가 N번을 고르게 된다.
        int curMaxWin = 0; // 최대 승리수를 구하기 위한 변수
        for(int i=0;i<myN;++i){
            // A가 i번 초이스로 주사위를 굴렸을때 나올 수 있는 모든 경우의 수
            int[] aResult = getAllPossibleScore(choiceList.get(i), dice);
            // B가 N번 초이스로 주사위를 굴렸을때 나올 수 있는 모든 경우의 수
            int[] bResult = getAllPossibleScore(choiceList.get(choiceList.size() -1 - i), dice);
            // 전체 경기 수
            int totalGame = aResult.length * bResult.length;
            // A의 승리, 비긴 수
            Pair aInfo = countAWin(aResult, bResult);
            int aWin = aInfo.win; 
            int bWin = totalGame - aWin - aInfo.same;
            //System.out.printf("%d, %d\n",aWin, bWin);
            if(aWin < bWin){ //b의 승리 횟수가 더 많다면 
                if(curMaxWin < bWin){
                    curMaxWin = bWin;
                    // 정담을 변경해 준다.
                    tempAnswer = choiceList.get(choiceList.size() -1 - i);
                }
            }else{
                if(curMaxWin < aWin){
                    curMaxWin = aWin;
                    tempAnswer = choiceList.get(i);
                }
            }
        }
        // 정답 옮기기
        for(int i=0;i<tempAnswer.size();++i){
            answer[i] = tempAnswer.get(i) + 1;
            //System.out.printf("%d ", tempAnswer.get(i));
        }
        return answer;
    }
    // combination을 통해 주사위를 고를 수 있는 경우의 수를 구한다. 
    public void combination(int[] arr, boolean[] visited, int s, int n, int r){
        if(r == 0){
            List<Integer> temp = new ArrayList<>();
            for(int i=0; i<arr.length; ++i){
                if(visited[i]) temp.add(arr[i]);
            }
            choiceList.add(temp);
            return;
        }
        for(int i=s;i<n;++i){
            visited[i] = true;
            combination(arr, visited, i+1, n, r-1);
            visited[i] = false;
        }
    }
    // 가능한 스코어들을 모두 구해준다.
    public int[] getAllPossibleScore(List<Integer> choice, int[][] dice){
        int[] result = dice[choice.get(0)];
        for(int i=1;i<choice.size();++i){
            result = multiple2Array(result, dice[choice.get(i)]);
        }
        return result;
    }
    // 2개의 배열을 합쳐주는 과정이다. {1,2,3}, (1,2,3) => {1 + 1, 1 + 2, 1 + 3, ... 3 + 1, 3 + 2, 3 + 3}
    public int[] multiple2Array(int[] a, int[] b){
        int[] result = new int[a.length * b.length];
        for(int i=0;i<a.length;++i){
            for(int j=0;j<b.length;++j){
                result[i * b.length + j] = a[i] + b[j];
            }
        }
        return result;
    }
    // a의 승리 수, 비긴 수를 lowerbound와 upperbound로 계산한다.
    public Pair countAWin(int[] a, int[] b){
        int aWin = 0, aSame = 0;
        Arrays.sort(b);
        for(int i=0;i<a.length;++i){
            int lowerBoundIndex = lowerBound(b, a[i]);
            int upperBoundIndex = upperBound(b, a[i]);
            aWin += lowerBoundIndex;
            aSame += upperBoundIndex - lowerBoundIndex;
        }
        return new Pair(aWin, aSame);
    }
    
    public int lowerBound(int[] b, int target){
        int s = 0;
        int e = b.length;
        while(s < e){
            int mid = (s+e) / 2;
            if(b[mid] < target){
                s = mid + 1;
            }else{
                e = mid;
            }
        }
        return e;
    }
    public int upperBound(int[] b, int target){
        int s = 0;
        int e = b.length;
        while(s < e){
            int mid = (s+e) / 2;
            if(b[mid] <= target){
                s = mid + 1;
            }else{
                e = mid;
            }
        }
        return e;
    }
    
}