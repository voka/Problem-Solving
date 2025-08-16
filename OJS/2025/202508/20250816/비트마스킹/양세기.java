import java.io.*;
import java.util.*;

class 양세기
{
	public static void main(String args[]) throws Exception
	{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T;
		T=Integer.parseInt(br.readLine());
		/*
		   여러 개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		*/

		for(int test_case = 1; test_case <= T; test_case++)
		{
		
            int N = Integer.parseInt(br.readLine());
            int answer = 1;
            int a = 0;
            while(true){
                long temp = (long)answer * N;
                while(temp > 0){
                    long k = temp % 10;
                    a = a | (1<<k);
                    temp = temp / 10;
                }
                //System.out.println(Integer.toBinaryString(a));
                if(check(a)) break;
                answer++;
       
            }
            System.out.println("#" + test_case + " " + (long)answer * N);
			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
				 이 부분에 여러분의 알고리즘 구현이 들어갑니다.
			 */
			/////////////////////////////////////////////////////////////////////////////////////////////

		}
	}
    static boolean check(int a){
        return a == ((1 << 10) - 1);
    }
}