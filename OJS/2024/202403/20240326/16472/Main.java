import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String temp = br.readLine();
        int sn = temp.length();
        int[] arr = new int[sn];
        for(int i=0;i<sn;++i){
            arr[i] = temp.charAt(i) - 'a';
        }
        int ans = 0;
        if(N == 26){
            ans = sn;
        }else{
            int s = 0;
            int e = 0;
            int type = 0;
            int[] cnt = new int[26]; // 알파벳의 각 개수 저장하기
            while(s < sn  &&  e < sn){
                if(cnt[arr[e]] == 0){ // 새로운 알파벳이 들어온다면 반복문 종료
                    type++;
                }
                cnt[arr[e]]++; 
                if(type > N){
                    while(true){
                        cnt[arr[s]]--;
                        if(cnt[arr[s]] == 0) { // s 지점의 알파벳 개수가 0이 된다면 종류가 1개 줄어든 것이므로 반복문 종료
                            type--;
                            s++;
                            break;
                        }
                        s++;
                    }
                }
                e++;
                ans = Math.max(ans,e-s);
            }
        }
        System.out.println(ans);
    } 
}
