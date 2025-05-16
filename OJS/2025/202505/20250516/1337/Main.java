import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int N;
    static int[] arr;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        for(int i=0;i<N;++i){
            arr[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(arr);
        if(N==1){
            System.out.println(4);
        }
        else if(N==2){
            if(arr[1] - arr[0] == 0){
                System.out.println(3);
            }else{
                System.out.println(4);
            }
        }else if(N==3){
            if(arr[2] - arr[0] <= 4){
                System.out.println(2);
            }else if(arr[2] - arr[1] <= 4){
                System.out.println(3);
            }else if(arr[1] - arr[0] <= 4){
                System.out.println(3);
            }else{
                System.out.println(4);
            }
        }else if(N==4){
            if(arr[3] - arr[0] <= 4){
                System.out.println(1);
            }else if(arr[3] - arr[1] <= 4){
                System.out.println(2);
            }else if(arr[2] - arr[0] <= 4){
                System.out.println(2);
            }else if(arr[3] - arr[2] <= 4){
                System.out.println(3);
            }else if(arr[2] - arr[1] <= 4){
                System.out.println(3);
            }else if(arr[1] - arr[0] <= 4){
                System.out.println(3);
            }else{
                System.out.println(4);
            }
        }else{
            int answer = 4;
            for(int i=4;i<N;++i){
                if(arr[i] - arr[i-4]<=4){
                    answer = 0;
                    break;
                }else if(arr[i] - arr[i-3]<=4){
                    answer = Math.min(answer,1);
                }else if(arr[i-1] - arr[i-4] <=4){
                    answer = Math.min(answer,1);
                }else if(arr[i] - arr[i-2] <= 4){
                    answer = Math.min(answer,2);
                }else if(arr[i-1] - arr[i-3] <= 4){
                    answer = Math.min(answer,2);
                }else if(arr[i-2] - arr[i-4] <= 4){
                    answer = Math.min(answer,2);
                }else if(arr[i] - arr[i-1] <= 4){
                    answer = Math.min(answer,3);
                }else if(arr[i-1] - arr[i-2] <= 4){
                    answer = Math.min(answer,3);
                }else if(arr[i-2] - arr[i-3] <= 4){
                    answer = Math.min(answer,3);
                }else if(arr[i-3] - arr[i-4] <= 4){
                    answer = Math.min(answer,3);
                }
            }
            System.out.println(answer);
        }
    }
}
