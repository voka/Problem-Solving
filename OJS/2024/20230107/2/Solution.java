class Solution {
    public boolean isEven(int a, int b){
        return (a+b)%2 == 0;
    }
    public boolean isOdd(int a, int b){
        return (a+b)%2 != 0;
    }
    public boolean checkRange(int x, int y){
        return (x>=1) && (x<=8) && (y>=1) &&(y<=8); 
    }
    public boolean isIn(int s, int m, int e){
        return ((s <= m) && (m <= e)) || ((e <= m) && (m <= s));
    }
    public int minMovesToCaptureTheQueen(int a, int b, int c, int d, int e, int f) {
        int minMove = 2;
        if(a == e){
            if(c != e) return 1;
            else if(!isIn(b,d,f)) return 1;
        }
        if(b == f){
            if(d != f) return 1;
            else if(!isIn(a,c,e)) return 1;
        }
        if((isEven(c,d) && isEven(e,f)) || (isOdd(c,d) && isOdd(e,f))){
            int[] dx = {1,1,-1,-1};
            int[] dy = {-1,1,-1,1};
            for(int i=0;i<4;++i){
                int nx = c + dx[i];
                int ny = d + dy[i]; 
                boolean flag = false;
                for(int j=0;j<8;++j){
                    if(checkRange(nx,ny) == false ) break;
                    if((nx == a) && (ny == b)) break;
                    if((nx == e) && (ny == f)){
                        flag = true;
                        break;
                    }
                    nx += dx[i];
                    ny += dy[i];
                }
                if(flag){
                    return 1;
                }
            }
        }
        return minMove;
    }
}