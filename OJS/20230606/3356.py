import sys
ip = sys.stdin.readline
N = int(ip())
pattern = ip().rstrip()
pT = [0]*(N)


def makeTable(p):
    j = 0
    for i in range(1, N):
        while (j > 0 and p[i] != p[j]):
            j = pT[j-1]
        if (p[i] == p[j]):
            j += 1
            pT[i] = j


makeTable(pattern)
print(N - pT[-1])  # 중복되는 접두, 접미어를 제거함으로써 가장 짧은 반복 분자열을 구할 수 있음
