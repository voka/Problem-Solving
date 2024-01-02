import sys
import pprint
ip = sys.stdin.readline
N, L = map(int, ip().split())
check_list = [list(map(int, ip().split())) for _ in range(N)]
for i in range(N):
    for j in range(N):
        temp = []
        for i in range(N):
            temp.append(check_list[i][j])
        check_list.append(temp)


def check_array(arr):
    pre = arr[0]
    j = 0
    visited = [0]*(N)  # i 번쨰 자리에 경사로가 있는지 확인할 배열
    while (j < N):
        if pre != arr[j]:
            result, j = cannot_put_runway(arr, j, visited)
            if result:
                return False
        pre = arr[j]
        j += 1
    return True


def cannot_put_runway(arr, j, visited):
    # print(arr2)
    if arr[j-1] - arr[j] == 1:  # 다음 칸 부터 내려감
        if N - j < L:  # 길이 확인 (j번째 칸 부터 + L 번째 까지 경사로를 나둠)
            return True, -1
        pre = arr[j]
        for k in range(j, L+j):
            if arr[k] != pre or visited[k] != 0:
                return True, -1
            visited[k] = 1
        return False, j+L-1
    elif arr[j] - arr[j-1] == 1:  # 현재 칸 부터 올라감
        if j - L < 0:  # 길이 확인 (j-1 번째 칸 부터 j-L 번째 까지 경사로를 둬야함)
            return True, -1
        pre = arr[j-1]
        for k in range(j-1, j-1-L, -1):
            if arr[k] != pre or visited[k] != 0:
                return True, -1
            visited[k] = 1
        return False, j
    else:
        return True, -1


answer = 0
for i in range(2*N):
    # print(check_list[i], check_array(check_list[i]))
    # print(check_list[i][::-1], check_array(check_list[i][::-1]))
    # print("++++++++++++++++++++++++++")
    if check_array(check_list[i]) or check_array(check_list[i][::-1]):
        answer += 1
print(answer)
