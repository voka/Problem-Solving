import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int wish,m,n;
    static int[] mList,nList, mCountList, nCountList;

    static int[] makeCountList(int[] temp,int max){
        int listSize = Math.max(max+1, wish+1);
        int[] result = new int[listSize];
        int cur;
        for(int i=0;i<temp.length;++i){
            cur = 0;
            for(int j=1;j<temp.length;++j){
                cur += temp[(i+j)%temp.length];
                result[cur] += 1; 
            }
        }
        result[max] += 1;
        return result;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        wish = Integer.parseInt(br.readLine());
        String[] temp = br.readLine().split(" ");
        m = Integer.parseInt(temp[0]);
        n = Integer.parseInt(temp[1]);
        mList = new int[m];
        nList = new int[n]; 

        int mMax = 0;
        int nMax = 0;
        for(int i=0;i<m;++i){
            mList[i] = Integer.parseInt(br.readLine());
            mMax += mList[i];
        }
        for(int i=0;i<n;++i){
            nList[i] = Integer.parseInt(br.readLine());
            nMax += nList[i];
        }

        mCountList = makeCountList(mList,mMax);
        nCountList = makeCountList(nList,nMax);
        // i : A 조각의 개수 wish - i : B 조각의 개수
        int answer = 0;
        mCountList[0] = 1;
        nCountList[0] = 1;
        for(int i=0;i<=wish;++i){
            answer += (mCountList[i] * nCountList[wish-i]);
        }
        System.out.println(answer);
    }
}
