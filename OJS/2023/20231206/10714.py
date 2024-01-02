import sys
ip = sys.stdin.readline 
dp = [0]*1001
N = int(ip())
last_num = N // 2 if N % 2 == 0 else (N // 2) + 1
#print(last_num)
A_list = [int(ip()) for _ in range(N)]
def dfs(start,end,size,num):
    #print(start,end,size,num)
    dp[num] = max(dp[num],size)
    if(num == last_num or start > N - 1 or end < - N):
        return
    if(A_list[start] > A_list[end]):
        if(start <= N-2) : dfs(start + 2, end, size + A_list[start+1], num + 1)
        dfs(start + 1, end - 1, size + A_list[end], num + 1)
    else:
        dfs(start + 1, end - 1 , size + A_list[start], num + 1)
        if(end >= - N + 1 ) : dfs(start, end - 2, size + A_list[end-1], num + 1)

for i in range(N):
    dfs(i+1,i-1,A_list[i],1)
print(dp[last_num])