import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

class Node{
    int time1;
    int time2;
    int id;
    int level;
    int maxChildLevel;
    int lastChildSize;
    Node parent;
    List<Node> child;
    int totalChild;
    public Node(int id, int n){
        this.id = id;
        this.time1 = n + 1;
        this.time2 = n + 1;
        this.totalChild = 0;
        this.child = new ArrayList<>();
    }
    public void getMaxChildLevel(int level){
        if(this.id != 0) {
            this.maxChildLevel = Math.max(this.maxChildLevel,level);
            this.parent.getMaxChildLevel(this.maxChildLevel);
        }
    }
    public void getLastChildSize(int size){
        if(this.id != 0){
            this.parent.lastChildSize = Math.max(this.parent.lastChildSize,size);
            this.parent.getLastChildSize(size);
        }
    }
    public void addChild(Node c){
        c.level = this.level+1;
        c.parent = this;
        this.child.add(c);
        addChildNum();
    }
    public void addChildNum(){
        this.totalChild++;
        if(this.id != 0) this.parent.addChildNum();
    }
    public void spreadTochildUsingTotalChild(int t){
        this.time1 = Math.min(t,this.time1);
        Collections.sort(this.child,(Node a, Node b) -> (b.totalChild != a.totalChild) ? b.totalChild - a.totalChild : b.lastChildSize - a.lastChildSize);
        for(int i=0;i<this.child.size();++i){
            this.child.get(i).spreadTochildUsingTotalChild(this.time1+ i + 1);
        }
    }

    public void spreadTochildUsingMaxChildLevel(int t){
        this.time2 = Math.min(t,this.time2);
        Collections.sort(this.child,(Node a, Node b) -> (b.maxChildLevel != a.maxChildLevel) ? b.maxChildLevel - a.maxChildLevel : b.lastChildSize - a.lastChildSize);
        for(int i=0;i<this.child.size();++i){
            this.child.get(i).spreadTochildUsingMaxChildLevel(this.time2 + i + 1);
        }
    }
}

class Pair implements Comparable<Pair>{
    int c; 
    int p;
    public Pair(int c, int p){
        this.c = c;
        this.p = p;
    }
    @Override
    public int compareTo(Pair o) {
        return this.p - o.p;
    }
}
public class Sub {
    static int n;
    static int[] dp;
    static Node[] nodeList;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        nodeList = new Node[n];
        dp = new int[n];
        for(int i=0;i<n;++i) nodeList[i] = new Node(i,n);
        nodeList[0].level = 0;
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        List<Pair> myPairs = new ArrayList<>();
        for(int i=0;i<n;++i){
            int p = Integer.parseInt(st.nextToken());
            if(p != -1) myPairs.add(new Pair(i, p));
        }
        Collections.sort(myPairs);

        for(Pair pair : myPairs){
            nodeList[pair.p].addChild(nodeList[pair.c]);
        }
        for(int i=1;i<n;++i){
            if(nodeList[i].child.size() == 0) {
                nodeList[i].getMaxChildLevel(nodeList[i].level);
            }
        }

        int maxLevel = 0;
        for(Node c : nodeList[0].child){
            maxLevel = Math.max(maxLevel,c.maxChildLevel);
        }
        for(int i=1;i<n;++i){
            if(nodeList[i].level == maxLevel) {
                nodeList[i].getLastChildSize(nodeList[i].parent.child.size());
            }
        }

        nodeList[0].spreadTochildUsingMaxChildLevel(0);
        nodeList[0].spreadTochildUsingTotalChild(0);


        int answer1 = 0;
        int answer2 = 0;
        for(int i=0;i<n;++i){
            answer1 = Math.max(answer1,nodeList[i].time1);
            answer2 = Math.max(answer2,nodeList[i].time2);
        }
        System.out.print("Time1 :");
        for(int i=0;i<n;++i){
            System.out.printf(" %d",nodeList[i].time1);
        }
        System.out.println();
        System.out.print("Time2 :");
        for(int i=0;i<n;++i){
            System.out.printf(" %d",nodeList[i].time2);
        }
        System.out.println();
        System.out.print("Time3 :");
        for(int i=0;i<n;++i){
            System.out.printf(" %d",dp[i]);
        }
        System.out.println();
        System.out.print("LastChildSize :");
        for(int i=0;i<n;++i){
            System.out.printf(" %d",nodeList[i].lastChildSize);
        }
        System.out.println();
        System.out.print("TotalChild :");
        for(int i=0;i<n;++i){
            System.out.printf(" %d",nodeList[i].totalChild);
        }
        System.out.println();
        System.out.print("maxChildLevel :");
        for(int i=0;i<n;++i){
            System.out.printf(" %d",nodeList[i].maxChildLevel);
        }
        System.out.println();
        System.out.println(Math.min(answer1,answer2));
    }   
}

/**
 *                              0
 *                  1                 2
 *          3      4      5               6        7 
 *      8    9  10 11   12 13            14      15   16
 *     17               18 19            20         21 22 23 
 * 
 * 
 * 15
-1 0 0 0 0 2 2 3 3 6 7 7 4 12 13

 *                              0
 *              1            2         3           4
 *                       5    6      7     8        12
 *                            9    10 11            13
 *                                                  14 
 * 
 * 
10
-1 0 0 1 2 2 2 3 7 2

 *                             0
 *                  1                   2
 *                  3               4      5     6     9
 *                 7
 *                 8
 * 
23
-1 0 1 1 1 2 2 2 3 3 3 4 4 4 0 14 15 16 17 18 19 20 21
                                        0
                            1                    14
        2              3             4               15
    5   6   7     8   9   10    11  12  13              16
                                                            17
                                                                18
                                                                    19
                                                                        20
                                                                            21
                                                                                22
11
-1 0 0 1 3 4 3 2 7 8 8
        0
    1       2
    3       7
    4 6     8  
    5       9 10
-> 5

11
-1 0 0 1 3 4 3 2 7 7 9
        0
    1       2
    3       7
    4 6     8 9
    5         10
 -> 5

 */