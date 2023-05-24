package other;
import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.StringTokenizer;
public class _1406_Editor {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        String str = br.readLine();
        LinkedList<Character> list = new LinkedList<Character>();

        for(int i=0;i<str.length();i++){
            list.add(str.charAt(i));
        }

        ListIterator<Character> listiter = list.listIterator();

        while(listiter.hasNext()){
            listiter.next();
        }
        int num = Integer.parseInt(br.readLine());

        while(num>0){
            st = new StringTokenizer(br.readLine());
            switch(st.nextToken()){
                case "L":
                    if(listiter.hasPrevious())
                        listiter.previous();
                    break;
                case "D":
                    if(listiter.hasNext())
                        listiter.next();
                    break;
                case "B":
                    if(listiter.hasPrevious()){
                        listiter.previous();
                        listiter.remove();
                    }
                    break;
                case "P":
                    listiter.add(st.nextToken().charAt(0));
                    break;

            }
            num--;
        }
        for(Character ch : list)
            bw.write(ch);

        bw.flush();
        bw.close();
    }
}
