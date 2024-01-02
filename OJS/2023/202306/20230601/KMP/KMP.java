
class KMP {
    int len;
    String pattern;
    int[] patternTable;

    public KMP(String s) {
        pattern = s;
        len = s.length();
        makeTable();
    }

    public void makeTable() {
        patternTable = new int[this.len];
        int i, j = 0;
        for (i = 1; i < this.len; ++i) {
            while (pattern.charAt(i) != pattern.charAt(j) && j > 0)
                j = patternTable[j - 1];
            if (pattern.charAt(i) == pattern.charAt(j))
                j++;
            patternTable[i + 1] = j;
        
        }
    }
    public int matching(String target){
        int n = target.length();
        int pi = 0,i=0;
        int c = 0;
        while(i < n){
            if(this.pattern[pi] == target[i]){
                pi++;
                i++;
            }
            else{
                if(pi == 0){
                    i++;
                }
                else{
                    pi = this.patternTable[pi-1];
                    i = i + pi;
                }
            }
            if(pi == this.len){
                c++;
                pi = 0;
            }
        }
        return c;
    }
}