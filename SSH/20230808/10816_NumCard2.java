package BinarySearch;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;
import java.util.HashMap;
public class _10816_NumCard2 {
    static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        String result = "";
        int n = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<n;i++){
            int tmp = Integer.parseInt(st.nextToken());
            if(map.get(tmp) == null) map.put(tmp, 1);
            else map.put(tmp, map.get(tmp) + 1);
        }


        int m = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<m;i++){
            int j = Integer.parseInt(st.nextToken());
            if(map.get(j) == null){
                bw.write("0 ");
            }
            else{
                bw.write(map.get(j)+" ");
            }
        }
        bw.flush();
        bw.close();

    }
}
