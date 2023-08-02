import sys
ip = sys.stdin.readline
"""
S0 -> m o o (3)
S1 -> m o oS(0) + m o o o (m + o * (k+2)) + m o o S(0) -> 3 + (1 + 1 + 2) + 3  = 10
S3 -> 10 + (1 + 2 + 2) + 10 = 25
S4 -> 25 + (1 + 3 + 2) + 25 = 56
S5 -> 56 + (1 + 4 + 2) + 56 = 119
이거 그대로 구현하면 무조건 메모리 초과 날듯 ?

수열 길이 -> len(S(k-1)) + 1 + k+2 + len(S(k-1))
1, 0 0 0, 0 0 0, 0 0 0

"""
S = [0]*29  # 수열 길이 저장소
S[0] = 3
for i in range(1, 29):
    S[i] = S[i-1] + 1 + i + 2 + S[i-1]


def get_N(num):
    result = 'o'
    for i in range(29):  # 입력받은 숫자를 포함할 수 있는 장 짧은 수열 찾기
        if num <= S[i]:
            break
    if num <= 3:  # 3 이하라면 i-1 에서 S[-1] -> 마지막 인덱스 값을 가리키므로
        if num == 1:  # 따로 처리를 해준다.
            result = 'm'
    else:
        # print(i, S[i-1], S[i])
        if num - S[i-1] == 1:  # 이 경우는 S[i-1]을 벗어나 새로운 M o o 수열이 시작하는 지점이다.
            result = 'm'
        # 이 경우는 새로운 M o o 수열이 끝나는 지점이라 S[i-1]에서 다시 작업을 시작해준다.
        elif S[i-1] + i + 3 < num:
            result = get_N(num - (S[i-1] + i + 3))
    return result


print(get_N(int(ip())))
