import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main2 {
    static int wish,m,n;
    static int[] mList,nList;
    static List<Integer> mCountList, nCountList;

    static int lowerBound(List<Integer> data, int target){
        int s = 0; 
        int e = data.size();
        while(s < e){
            int m = (s + e) / 2;
            if(data.get(m) >= target){
                e = m;
            }else{
                s = m + 1;
            }
        }
        return e;
    }
    static int upperBound(List<Integer> data, int target){
        int s = 0; 
        int e = data.size();
        while(s < e){
            int m = (s + e) / 2;
            if(data.get(m) <= target){
                s = m + 1;
            }else{
                e = m;
            }
        }
        return e;
    }
    static List<Integer> makeCountList(int[] temp,int max){
        List<Integer> result = new ArrayList<>();
        result.add(0); // size 0인거 추가
        int cur;
        for(int i=0;i<temp.length;++i){
            cur = 0;
            for(int j=1;j<temp.length;++j){
                cur += temp[(i+j)%temp.length];
                result.add(cur);
            }
        }
        result.add(max);
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
        Collections.sort(mCountList);
        Collections.sort(nCountList);

        int answer = 0;
        for(int i=0;i<mCountList.size();++i){
            int cur = wish - mCountList.get(i);
            if(cur < 0) break;
            int lo = lowerBound(nCountList, cur);
            int up = upperBound(nCountList, cur);
            //System.out.printf("A : %d, lo : %d, up : %d\n", mCountList.get(i),lo, up);
            answer += (up - lo);
        }
        System.out.println(answer);
    }
}
