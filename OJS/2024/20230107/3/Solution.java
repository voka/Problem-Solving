import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public int maximumSetSize(int[] nums1, int[] nums2) {
        Set<Integer> mySet1 = new HashSet<>();
        Set<Integer> mySet2 = new HashSet<>();
        Set<Integer> total = new HashSet<>();
        for(int num : nums1){
            mySet1.add(num);
        }
        for(int num : nums2){
            mySet2.add(num);
        }
        int numlen1 = (nums1.length / 2);
        int numlen2 = (nums2.length / 2);
        if(mySet1.size() > numlen1){
            //Set2에 있는 거 제거
            List<Integer> delete =  new ArrayList<>();
            for(int num : mySet1){
                if(mySet2.contains(num)){
                    delete.add(num);
                }
                if(mySet1.size() == numlen1) break;
            }
            while(mySet1.size() > numlen1){
                int num = mySet1.iterator().next();
                mySet1.remove(num);
            }
        }
        if(mySet2.size() > numlen2){
            //Set1에 있는 거 제거
            for(int num : mySet2){
                if(mySet1.contains(num)){   
                    mySet2.remove(num); 
                }
                if(mySet2.size() == numlen2) break;
            }
            while(mySet2.size() > numlen2){
                int num = mySet2.iterator().next();
                mySet2.remove(num);
            }
        }
        for(int num : mySet1){
            total.add(num);
        }
        for(int num : mySet2){
            total.add(num);
        }
        return total.size();

    }
}