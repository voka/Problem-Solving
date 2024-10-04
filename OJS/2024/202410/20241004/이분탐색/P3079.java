//https://www.acmicpc.net/problem/3079

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class P3079 {
    static int N,M;
    static long[] time;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        time = new long[N];
        long timeMax = 0;
        for(int i=0;i<N;++i){
            time[i] = Long.parseLong(br.readLine());
            timeMax = Math.max(timeMax,time[i]);
        }
        long s = 0, e = timeMax * M;
        long minTime = timeMax * M;
        while(s<=e){
            long mid = (s + e) / 2;
            if(count(mid)){
                minTime = Math.min(minTime,mid);
                e = mid - 1;
            }else{
                s = mid + 1;
            }
        }
        //System.out.println(s);
        System.out.println(minTime);
    }
    static boolean count(long t){
        long re = 0;
        for(int i=0;i<N;++i){
            re += t / time[i];
            if (re >= M) return true;
        }
        return  re >= M;
    }
}
