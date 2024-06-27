public class Basic {
    static int []arr = {1, 9, 3, 8, 4, 5, 5, 9, 10, 3, 4, 5};
    static int []tree;
    public static void main(String[] args){
        int alen = arr.length;
        tree = new int[4 * alen];
        init(0,alen-1,1);
        int all = sum(0,alen-1,1,0,12);
        System.out.println(all);
        int some = sum(0,alen-1,1,1,10);
        System.out.println(some);
    }
    static int init(int s, int e, int node){
        if(s == e) return tree[node] = arr[s];
        int mid = (s + e) / 2;
        return tree[node] = init(s, mid, node * 2) + init(mid + 1, e, node * 2 + 1);
    }
    /**
     * s : 시작 인덱스
     * e : 끝 인덱스
     * l,r : 구간 합을 구하고자 하는 범위
     */
    static int sum(int s, int e, int node, int l, int r){
        if(l > e || r < s) return 0;
        else if(l <= s && e <= r) return tree[node];
        else{
            int mid = (s+e) / 2;
            return sum(s, mid, node * 2 , l, r) + sum(mid+1, e, node*2 + 1, l, r);
        }
    }
    /**
     * s : 시작 인덱스
     * e : 끝 인덱스
     * index : 구간 합을 수정하고자 하는 노드
     * dif : 수정할 값
     */
    static void update(int s, int e, int node, int index , int dif){
        if(index < s || index > e) return;
        tree[node] += dif;
        if(s == e) return;
        int mid = (s + e) / 2;
        update(s, mid, node * 2, index, dif);
        update(mid+1, e, node *2 + 1, index, dif);
    }


}
