//https://www.acmicpc.net/problem/9024

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class P20366 {
    static int[] myList;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        myList = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;++i){
            myList[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(myList);
        int minGap = Integer.MAX_VALUE;
        for(int i=0;i<N-3;++i){
            for(int j=i+3;j<N;++j){
                int s = i+1;
                int e = j-1;
                while(s < e){
                    int a = myList[i] + myList[j]; 
                    int b = myList[s] + myList[e];
                    minGap = Math.min(minGap,Math.abs(a-b));
                    if(a > b){
                        s++;
                    }else{
                        e--;
                    }
                }
            }
        }
        System.out.println(minGap);
       
    }
}
