package Divide_and_Conquer;
import java.io.*;
import java.util.StringTokenizer;
public class _1074_Z {
    static int N,R,C;
    static int count = 0;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        int size = (int)Math.pow(2,N);

        dac(0,0,size);
        System.out.println(count);
    }
    public static void dac(int r, int c, int size){
        int newSize = size/2;

        if(newSize ==0)
            return;
        if((r + newSize) >R && (c+newSize) > C){
            dac(r,c,newSize);
        }
        if((r + newSize) >R && (c+newSize) <= C){
            count+=newSize*newSize;
            dac(r,c+newSize,newSize);
        }
        if((r + newSize) <=R && (c+newSize) > C){
            count+=newSize*newSize*2;
            dac(r+newSize,c,newSize);
        }
        if((r + newSize) <=R && (c+newSize) <= C){
            count+=newSize*newSize*3;
            dac(r+newSize,c+newSize,newSize);
        }
    }
}
