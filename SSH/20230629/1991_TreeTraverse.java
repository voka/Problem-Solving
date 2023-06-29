package Graph;
import java.io.*;
import java.util.StringTokenizer;
public class _1991_TreeTraverse {
    static class Node{
        char value;
        Node left;
        Node right;
        Node(char value,Node left,Node right){
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        Node head = new Node('A',null,null);
        for(int i=0;i<n;i++){
            st = new StringTokenizer(br.readLine());
            char root = st.nextToken().charAt(0);
            char left = st.nextToken().charAt(0);
            char right = st.nextToken().charAt(0);

            insertNode(head,root,left,right);
        }
        preOrder(head);
        System.out.println();
        inOrder(head);
        System.out.println();
        postOrder(head);
    }
    public static void insertNode(Node tmp, char root, char left, char right){
        if(tmp.value == root){
            tmp.left = (left == '.' ? null :new Node(left,null,null));
            tmp.right = (right == '.' ? null :new Node(right,null,null));
        }else{
            if(tmp.left != null) insertNode(tmp.left,root,left,right);
            if(tmp.right != null) insertNode(tmp.right,root,left,right);
        }
    }
    public static void preOrder(Node n){
        if(n == null) return;
        System.out.print(n.value);
        preOrder(n.left);
        preOrder(n.right);
    }
    public static void inOrder(Node n){
        if(n == null) return;
        inOrder(n.left);
        System.out.print(n.value);
        inOrder(n.right);
    }
    public static void postOrder(Node n){
        if(n == null) return;
        postOrder(n.left);
        postOrder(n.right);
        System.out.print(n.value);
    }
}
