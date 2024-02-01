import java.util.*;

class Pair{
    int win;
    int draw;
    public Pair(int win, int draw){
        this.win = win;
        this.draw = draw;
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
        combination(choiceN, visited, 0, total, half);
        int myN = choiceList.size() / 2;
        int curMaxWin = 0;
        for(int i=0;i<myN;++i){
            int[] aResult = getAllPossibleScore(choiceList.get(i), dice);
            int[] bResult = getAllPossibleScore(choiceList.get(choiceList.size() -1 - i), dice);
            int totalGame = aResult.length * bResult.length;
            
            Arrays.sort(bResult);
            Pair aInfo = countAWin(aResult, bResult);
            int aWin = aInfo.win; 
            int bWin = totalGame - aWin - aInfo.draw;
            //System.out.printf("%d, %d\n",aWin, bWin);
            if(aWin < bWin){
                if(curMaxWin < bWin){
                    curMaxWin = bWin;
                    tempAnswer = choiceList.get(choiceList.size() -1 - i);
                }
            }else{
                if(curMaxWin < aWin){
                    curMaxWin = aWin;
                    tempAnswer = choiceList.get(i);
                }
            }
        }
        for(int i=0;i<tempAnswer.size();++i){
            answer[i] = tempAnswer.get(i) + 1;
            //System.out.printf("%d ", tempAnswer.get(i));
        }
        return answer;
    }
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
    public int[] getAllPossibleScore(List<Integer> choice, int[][] dice){
        int[] result = dice[choice.get(0)];
        for(int i=1;i<choice.size();++i){
            result = multiple2Array(result, dice[choice.get(i)]);
        }
        return result;
    }
    public int[] multiple2Array(int[] a, int[] b){
        int[] result = new int[a.length * b.length];
        for(int i=0;i<a.length;++i){
            for(int j=0;j<b.length;++j){
                result[i * b.length + j] = a[i] + b[j];
            }
        }
        return result;
    }
    public Pair countAWin(int[] a, int[] b){
        int aWin = 0, draw = 0;
        for(int i=0;i<a.length;++i){
            int lowerBoundIndex = lowerBound(b, a[i]);
            int upperBoundIndex = upperBound(b, a[i]);
            aWin += lowerBoundIndex;
            draw += upperBoundIndex - lowerBoundIndex;
        }
        return new Pair(aWin, draw);
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