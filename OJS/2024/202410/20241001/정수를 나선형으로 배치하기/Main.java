class Solution {
    public int[][] solution(int n) {
        int[][] answer = new int[n][n];
        int c = 2;
        int num = 1;
        for(int i=0;i<n;++i){
            answer[0][i] = num++;
        }
        int x = 0;
        int y = n-1;
        int f = n-1;
        while(f > 0){
            // 위 ->  아래
            for(int i=0;i<f;++i){
                answer[++x][y] = num++;               
            }
            c-=1;
            if(c == 0){
                f--;
                if(f == 0) break;
                c = 2;
            }
            // 오른쪽 -> 왼쪽
            for(int i=0;i<f;++i){
                answer[x][--y] = num++;               
            }
            c-=1;
            if(c == 0){
                f--;
                if(f == 0) break;
                c = 2;
            }
            
            // 아래 -> 위  
            for(int i=0;i<f;++i){
                answer[--x][y] = num++;               
            }
            c-=1;
            if(c == 0){
                f--;
                if(f == 0) break;
                c = 2;
            }
            
            // 왼쪽 -> 오른쪽
            for(int i=0;i<f;++i){
                answer[x][++y] = num++;               
            }
            c-=1;
            if(c == 0){
                f--;
                if(f == 0) break;
                c = 2;
            }
            
        }
        //printArr(answer);
        
        return answer;
    }
    static void printArr(int[][] answer){
        for(int i=0;i<answer.length;++i){
            for(int j=0;j<answer[i].length;++j){
                System.out.printf("%d ",answer[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}