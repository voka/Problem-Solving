package Graph;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
public class _2667_EstateNum {
    static ArrayList<Integer> countArray;
    static int[][] map;
    static int[][] check;
    static int n,count;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        countArray = new ArrayList<Integer>();
        map = new int[n][n];
        check = new int[n][n];
        for(int i=0;i<n;i++){
            String str = br.readLine();
            for(int j=0;j<n;j++){
                map[i][j] = str.charAt(j)-'0';
            }
        }
        int Num=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(check[i][j] == 0){
                    estateCheck(i,j);
                    if(count!=0){
                        countArray.add(count);
                        Num++;
                        count =0;
                    }


                }
            }
        }

        Collections.sort(countArray);
        System.out.println(Num);
        for(int i : countArray){
            System.out.println(i);
        }
    }
    public static void estateCheck(int row,int col){
        check[row][col] =1;
        if(map[row][col] ==0)
            return;
        count+=1;
        if((col+1 < n) && (check[row][col+1] == 0)){
            estateCheck(row,col+1);
        }
        if((row+1 < n) && (check[row+1][col] == 0)){
            estateCheck(row+1,col);
        }
        if((row-1 >= 0) && (check[row-1][col] == 0)){
            estateCheck(row-1,col);
        }
        if((col-1 >= 0) && (check[row][col-1] == 0)){
            estateCheck(row,col-1);
        }

    }
}
