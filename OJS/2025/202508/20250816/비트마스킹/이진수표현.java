import java.io.*;
import java.util.*;

class 이진수표현
{
	public static void main(String args[]) throws Exception
	{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T;
		T=Integer.parseInt(br.readLine());

		for(int test_case = 1; test_case <= T; test_case++)
		{
		
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            System.out.println("#" + test_case + " " + ((((1<<N)-1) & M) == (((1<<N)-1) ) ? "ON":"OFF"));

		}
	}
}