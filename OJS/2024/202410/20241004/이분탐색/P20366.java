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
        for(int i=0;i<N-3;++i){ // N-3 한 이유는 눈덩이가 총 4개 필요하기 때문에 
            for(int j=i+3;j<N;++j){
                // 양쪽 값을 i, j 로 잡고 눈사람 만들기
                int s = i+1;
                int e = j-1;
                while(s < e){
                    int a = myList[i] + myList[j]; // 바깥쪽 눈덩이
                    int b = myList[s] + myList[e]; // 이분탐색할 안쪽 눈덩이
                    minGap = Math.min(minGap,Math.abs(a-b));
                    if(a > b){ // 바깥쪽 눈덩이가 크면
                        s++; // 안쪽 눈덩이의 덩치를 키워서 차이가 적게 만든다
                    }else{ // 바깥쪽 눈덩이가 작으면
                        e--; // 안쪽 눈덩이의 덩치를 줄여서 차이가 적게 나게 만든다
                    }
                }
            }
        }
        System.out.println(minGap);
       
    }
}
