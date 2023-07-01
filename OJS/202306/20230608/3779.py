import sys
ip = sys.stdin.readline


def solve(len):
    mystr = ip().rstrip()
    pT = [0]*len
    j = 0
    for i in range(1, len):
        while (j > 0 and mystr[i] != mystr[j]):
            j = pT[j-1]
        if (mystr[i] == mystr[j]):
            j += 1
            pT[i] = j
        # 0 ~ i 번째 인덱스를 가지는 문자열 => i전체 , i전체의 최대 접미, 접두사 길이 => a
        cur = (i+1) - pT[i]
        if ((i+1) % cur == 0 and (i+1) // cur > 1):  # i전체의 길이는 a 로 나누어 떨어져야 하며, 나눈 값은 1 보다 커야함 !
            print(i+1, (i+1) // cur)


T = 0
while True:
    len = int(input())
    if len == 0:
        break
    T += 1
    print("Test case #{}".format(T))
    solve(len)
    print()
