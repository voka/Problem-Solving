class Solution {
    public int missingInteger(int[] nums) {
        int maxSum = nums[0];
        for(int i=1;i<nums.length;++i){
            if(nums[i] - 1 == nums[i-1]){
                maxSum += nums[i];
            }else break;
        }
        while(true){
            int start = maxSum;
            for(int i=0;i<nums.length;++i){
                if(nums[i] == maxSum) {
                    maxSum++;
                }
            }
            if(start == maxSum) break;
        }
        return maxSum; 
    }
}