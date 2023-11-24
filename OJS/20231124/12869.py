import sys
import math
from collections import deque
ip = sys.stdin.readline
N = int(ip())
scv_list = list(map(int,ip().split()))

answer = 61
if N == 1:
    dp = [61]*61
    dp[0] = 0
    for i in range(61):
        dp[i] = min(dp[max(0,i-9)] + 1,dp[i])
    answer = dp[scv_list[0]]
elif N == 2:
    dp = [[61]*61 for _ in range(61)]
    dp[0][0] = 0
    for i in range(61):
        for j in range(61):
            dp[i][j] = min(dp[i][j], dp[max(0,i-9)][max(0,j-3)]+1, dp[max(0,i-3)][max(0,j-9)] + 1)
    answer = dp[scv_list[0]][scv_list[1]]
elif N == 3:
    dp = [[[61]*61 for _ in range(61)] for _ in range(61)]
    dp[0][0][0] = 0
    
    """
    9 3 1
    9 1 3
    3 9 1
    3 1 9
    1 9 3
    1 3 9
    """
    for i in range(61):
        for j in range(61):
            for k in range(61):
                dp[i][j][k] = min(dp[i][j][k], 
                                  dp[max(0,i-9)][max(0,j-3)][max(0,k-1)]+1, 
                                  dp[max(0,i-9)][max(0,j-1)][max(0,k-3)]+1, 
                                  dp[max(0,i-3)][max(0,j-9)][max(0,k-1)]+1, 
                                  dp[max(0,i-3)][max(0,j-1)][max(0,k-9)]+1, 
                                  dp[max(0,i-1)][max(0,j-9)][max(0,k-3)]+1, 
                                  dp[max(0,i-1)][max(0,j-3)][max(0,k-9)]+1, 
                                  )
    answer = dp[scv_list[0]][scv_list[1]][scv_list[2]]
                
                
print(answer)

    


