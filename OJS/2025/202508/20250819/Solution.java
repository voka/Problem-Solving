import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;



class Member{
    int mTeam;
    int mScore;
    public Member(int mTeam, int mScore){
        this.mTeam = mTeam;
        this.mScore = mScore;
    }
}
class UserSolution
{

    int[] maxScoreIdx, minScoreIdx;
    TreeSet<Integer>[][] treeSets;
    Map<Integer, Member> maps;
    int[] check;
    public void init()
    {
        treeSets = new TreeSet[6][6];//반, 점수
        maps = new HashMap<>();
        check = new int[100001];

        maxScoreIdx = new int[6];
        minScoreIdx = new int[6];
        for(int i=0;i<6;++i){
            minScoreIdx[i] = 1; // 최저 점수 idx
            maxScoreIdx[i] = 5; // 최대 점수 idx;
            for(int j=0;j<6;++j){
                treeSets[i][j] = new TreeSet<>();
            }
        }

    }


    public void hire(int mID, int mTeam, int mScore)
    {
        check[mID] = 1;
        maps.put(mID, new Member(mTeam,mScore));
        treeSets[mTeam][mScore].add(mID); 
    }

    public void fire(int mID)
    {
        
        if(check[mID] == 0){
            System.out.println("해고 : 고용된 적이 없는데요 ???");
            return;
        }
        if(check[mID] == -1) {
            System.out.println("해고 : 이미 해고 됐는데요 ????");
            return;
        }
        Member m = maps.get(mID);
        check[mID] = -1;
        treeSets[m.mTeam][m.mScore].remove(mID);

    }

    public void updateSoldier(int mID, int mScore)
    {

        if(check[mID] == 0){
            System.out.println("점수 업데이트 : 고용된 적이 없는데요 ???");
            return;
        }
        if(check[mID] == -1) {
            System.out.println("점수 업데이트 : 이미 해고 됐는데요 ????");
            return;
        }
        Member m = maps.get(mID);
        int newScore = Math.min(5,m.mScore+mScore);
        check[mID] = -1;
        treeSets[m.mTeam][m.mScore].remove(mID);
        treeSets[m.mTeam][newScore].add(mID);

        maps.put(mID,new Member(m.mTeam, newScore));
    }

    public void updateTeam(int mTeam, int mChangeScore)
    {
        if(mChangeScore > 0) maxScoreIdx[mTeam] = Math.max(1, maxScoreIdx[mTeam]-mChangeScore); // +3 이면 인덱스 2인 병사들 부터 모두 최고 점수 5가 된다.
        if(mChangeScore < 0) minScoreIdx[mTeam] = Math.min(5, minScoreIdx[mTeam]-mChangeScore); // -2 이면 인덱스 3인 병사들 까지 모두 최저 점수 1이 된다.

        // 팀에 -5를 한 뒤 -> 
    }

    public int bestSoldier(int mTeam)
    {
        int result = Integer.MIN_VALUE;




        
        
        return 0;
    }
}
class Solution
{
    private final static int CMD_INIT				= 1;
    private final static int CMD_HIRE				= 2;
    private final static int CMD_FIRE				= 3;
    private final static int CMD_UPDATE_SOLDIER		= 4;
    private final static int CMD_UPDATE_TEAM		= 5;
    private final static int CMD_BEST_SOLDIER		= 6;

    private final static UserSolution usersolution = new UserSolution();

    private static boolean run(BufferedReader br) throws Exception
    {
        StringTokenizer st;

        int numQuery;

        int mID, mTeam, mScore, mChangeScore;

        int userAns, ans;

        boolean isCorrect = false;

        numQuery = Integer.parseInt(br.readLine());

        for (int q = 0; q < numQuery; ++q)
        {
            st = new StringTokenizer(br.readLine(), " ");

            int cmd;
            cmd = Integer.parseInt(st.nextToken());

            switch(cmd)
            {
                case CMD_INIT:
                    usersolution.init();
                    isCorrect = true;
                    break;
                case CMD_HIRE:
                    mID = Integer.parseInt(st.nextToken());
                    mTeam = Integer.parseInt(st.nextToken());
                    mScore = Integer.parseInt(st.nextToken());
                    usersolution.hire(mID, mTeam, mScore);
                    break;
                case CMD_FIRE:
                    mID = Integer.parseInt(st.nextToken());
                    usersolution.fire(mID);
                    break;
                case CMD_UPDATE_SOLDIER:
                    mID = Integer.parseInt(st.nextToken());
                    mScore = Integer.parseInt(st.nextToken());
                    usersolution.updateSoldier(mID, mScore);
                    break;
                case CMD_UPDATE_TEAM:
                    mTeam = Integer.parseInt(st.nextToken());
                    mChangeScore = Integer.parseInt(st.nextToken());
                    usersolution.updateTeam(mTeam, mChangeScore);
                    break;
                case CMD_BEST_SOLDIER:
                    mTeam = Integer.parseInt(st.nextToken());
                    userAns = usersolution.bestSoldier(mTeam);
                    ans = Integer.parseInt(st.nextToken());
                    if (userAns != ans) {
                        isCorrect = false;
                    }
                    break;
                default:
                    isCorrect = false;
                    break;
            }
        }

        return isCorrect;
    }

    public static void main(String[] args) throws Exception
    {
        int TC, MARK;

        //System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        TC = Integer.parseInt(st.nextToken());
        MARK = Integer.parseInt(st.nextToken());

        for (int testcase = 1; testcase <= TC; ++testcase)
        {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }

        br.close();
    }
}