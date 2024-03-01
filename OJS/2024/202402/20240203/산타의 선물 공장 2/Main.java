import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int Q,N,M;
    static int[] pre, next, head, tail, size;

    static void pushTail(int containerId, int boxId){
        if(size[containerId] == 0){ // 해당 컨테이너에 처음으로 물건이 들어옴.
            head[containerId] = tail[containerId] = boxId; // 컨테이너의 앞, 뒤 부분을 해당 박스로 바꾸기
            pre[boxId] = -1; // 다음 상자가 없으므로 -1로 초기화
        }else{// 컨테이너의 꼬리부분만 바뀌고 머리 부분은 바뀌지 않는다.
            int preTail = tail[containerId];
            tail[containerId] = boxId;
            pre[boxId] = preTail;
            next[preTail] = boxId;
        }//공통
        next[boxId] = -1; // 꼬리는 이전 박스가 없다.
        size[containerId]++;// 컨테이너의 사이즈 1 증가
    }
    static void pushHead(int containerId, int boxId){
        if(size[containerId] == 0){ // 해당 컨테이너에 처음으로 물건이 들어옴.
            head[containerId] = tail[containerId] = boxId; // 컨테이너의 앞, 뒤 부분을 해당 박스로 바꾸기
            next[boxId] = -1; // 다음 상자가 없으므로 -1로 초기화
        }else{// 컨테이너의 머리 부분만 바뀌고, 꼬리는 바뀌지 않는다.
            int preHead = head[containerId];
            head[containerId] = boxId;
            next[boxId] = preHead;
            pre[preHead] = boxId;
        }//공통
        pre[boxId] = -1; // 머리는 이전 박스가 없다.
        size[containerId]++;// 컨테이너의 사이즈 1 증가
    }
    static int popHead(int containerId){
        int outBox = -1;
        if(size[containerId] > 0){
            if(size[containerId] == 1){ // 1개인 경우
                outBox = head[containerId];
                head[containerId] = tail[containerId] = -1; // 컨테이너의 앞, 뒤 숫자 -1으로 초기화
            }else{// 2개 이상인 경우 
                outBox = head[containerId];
                int nextBox = next[outBox];
                head[containerId] = nextBox;
                pre[nextBox] = -1; //nextBox가 컨테이너의 앞 부분 박스가 되어서 이전 박스 배열 초기화
                next[outBox] = -1; //나가는 박스의 다음 것도 -1로 초기화
            }
            size[containerId]--;
        }
        return outBox;
    }
    static long getGiftInfo(int boxId){
        return (long)pre[boxId] + 2 * next[boxId];
    }
    static long getContainerInfo(int containerId){
        return (long)head[containerId] + 2*tail[containerId] + 3*size[containerId];
    }
    static void moveAllBox(int sCon, int dCon){
        if(size[sCon] == 0){
            return;
        }
        if(size[sCon] == 1){
            int out = popHead(sCon);
            pushHead(dCon, out);
        }else{
            int sHead = head[sCon];
            int sTail = tail[sCon];
            moveBoxs(sCon, dCon, sHead, sTail, size[sCon]);
            head[sCon] = tail[sCon] = -1; // 물건 전체를 옴겼으므로 컨테이너는 빔.
        }
    }
    static void moveBoxs(int sCon, int dCon, int sHead, int sTail, int moveCount){
        int dSize = size[dCon];
        if(dSize == 0){
            head[dCon] = sHead;
            tail[dCon] = sTail;
            next[sTail] = -1;// 꼬리부분이 되므로 next가 -1로 바뀌게 됨.
        }else{ // 도착 컨테이너에 박스가 1개라도 있을 경우
            // 도착 컨테이너 정보 갱신
            int dHead = head[dCon];
            // 헤드 변경
            head[dCon] = sHead;
            // 박스간 정보 갱신
            next[sTail] = dHead;
            pre[dHead] = sTail;
        }
        // 도착 컨테이너 총 사이즈 변경
        size[dCon] += moveCount;
        // 물건이 있던 컨테이너 물건 정보 갱신
        size[sCon] = size[sCon] - moveCount;
        
    }
    static void changeHead(int sCon, int dCon){
        int sHead = popHead(sCon);
        int dHead = popHead(dCon);
        if(sHead == -1 && dHead == -1) return;
        if(sHead == -1 && dHead != -1){
            pushHead(sCon, dHead);
        }else if(sHead != -1 && dHead == -1){
            pushHead(dCon, sHead);
        }else{
            pushHead(sCon, dHead);
            pushHead(dCon, sHead);
        }
    }

    static void divideContainer(int sCon, int dCon){
        int sSize = size[sCon]; 
        if(sSize <= 1) return;// 1개 이하인 경우는 물건을 옮기지 않는다.
        int moveCount = sSize / 2;
        //System.out.printf("옮기는 박스 개수 => %d\n",moveCount);
        if(moveCount == 1){
            pushHead(dCon, popHead(sCon));
        }
        else{
            int moveHead = head[sCon];
            int moveTail = head[sCon];
            for(int i=1;i<moveCount;++i){
                moveTail = next[moveTail];
            }
            head[sCon] = next[moveTail]; // head 정보 갱신
            pre[head[sCon]] = -1;
            //System.out.printf("옮기는 첫번째 박스 => %d, 마지막 박스 => %d\n",moveHead, moveTail);
            moveBoxs(sCon, dCon, moveHead, moveTail, moveCount);

        }
    }
    static void printAllContainer(){
        for(int i=1;i<=N;++i){
            printContainer(i);
        }
    }
    static void printContainer(int containerId){
        System.out.printf("CONTAINER %d =========\n", containerId);
        System.out.printf("SIZE %d\n", size[containerId]);
        int boxId = head[containerId];
        while(boxId != -1){
            System.out.printf("%d ", boxId);
            boxId = next[boxId];
        }
        System.out.println("\nEND\n");
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Q = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        st.nextToken(); // 명령 100
        N = Integer.parseInt(st.nextToken()); // 컨테이너 수
        M = Integer.parseInt(st.nextToken()); // 박스 수
        head = new int[N+1];
        tail = new int[N+1];
        size = new int[N+1];
        next = new int[M+1];
        pre = new int[M+1];
        Arrays.fill(head, -1);
        Arrays.fill(tail, -1);
        Arrays.fill(next, -1);
        Arrays.fill(pre, -1);
        for(int i=0;i<M;++i){
            int containerId = Integer.parseInt(st.nextToken());
            pushTail(containerId, i+1);
        }
        StringBuilder sb = new StringBuilder();
        for(int q=1;q<Q;++q){
            System.out.println("==================");
            printAllContainer();
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            int sCon=-1, dCon=-1, boxId = -1, conId = -1;
            switch (command) {
                case 200:
                    sCon = Integer.parseInt(st.nextToken());
                    dCon = Integer.parseInt(st.nextToken());
                    moveAllBox(sCon, dCon);
                    sb.append(size[dCon]).append('\n');
                    break;
                case 300:
                    sCon = Integer.parseInt(st.nextToken());
                    dCon = Integer.parseInt(st.nextToken());
                    changeHead(sCon, dCon);
                    sb.append(size[dCon]).append('\n');
                    break;
                case 400:
                    sCon = Integer.parseInt(st.nextToken());
                    dCon = Integer.parseInt(st.nextToken());
                    divideContainer(sCon, dCon);
                    sb.append(size[dCon]).append('\n');
                    break;
                case 500:
                    boxId = Integer.parseInt(st.nextToken());
                    sb.append(getGiftInfo(boxId)).append('\n');
                    break;
                case 600:
                    conId = Integer.parseInt(st.nextToken());
                    sb.append(getContainerInfo(conId)).append('\n');
                    break;
                default:
                    break;
            }
        }
        System.out.println(sb.toString());

    }
}
/**
 * 
 * 
 * 
 2
 19
 4
 2 * 1 + 4 * 2 + 2 * 3 => 16
 0
 1 + 12 => 13


 */