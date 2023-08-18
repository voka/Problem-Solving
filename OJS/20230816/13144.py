import sys
ip = sys.stdin.readline

N = int(ip())
_list = list(map(int, ip().split()))


def solve():
    ans = 0
    check_list = [False]*(100001)
    s, e = 0, 0
    while s < N and e < N:  # 시작점이나 끝점이 리스트 맨 끝에 다가가기 전까지만 실행
        # print(s, e)
        if not check_list[_list[e]]:  # 나오지 않았던 수가 나올떄마다
            check_list[_list[e]] = True
            e += 1
            ans += (e - s)  # 정답에 현재 뽑은 리스트의 길이를 더함
        else:
            while check_list[_list[e]]:  # 이번에 중복해서 나온 숫자를 제거하기 위해 앞에서 봤던 숫자를 뺴냄-
                check_list[_list[s]] = False
                s += 1  # 길이 줄이기
    # print(e-s)
    return ans


print(solve())
"""
 
s
e
1 2 3 1 2 

1 2 3 1 2

1
2
3
1 2 
1 2 3
2 3
6
2 3 1
3 1
3 1 2
1
2
1 2



1 1 2 1 1 

1
1
1 2
2
2 1
1 
1

 
 """
