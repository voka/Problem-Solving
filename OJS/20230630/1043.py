import sys
ip = sys.stdin.readline
N, M = map(int, ip().split())


def get_list_with_num():
    temp = list(map(int, ip().split()))
    return temp[0], temp[1:]


tN, true_persons = get_list_with_num()
true_persons = set(true_persons)
pN = []
parties = []
for i in range(M):
    cN, cL = get_list_with_num()
    pN.append(cN)
    parties.append(cL)
parent = [i for i in range(N+1)]


def find_parent(x):
    if parent[x] != x:
        parent[x] = find_parent(parent[x])
    return parent[x]


def union_parent(a, b):
    pa = find_parent(a)
    pb = find_parent(b)
    if pa in true_persons:
        parent[pb] = pa
    elif pb in true_persons:
        parent[pa] = pb
    elif pa < pb:
        parent[pb] = pa
    else:
        parent[pa] = pb


answer = 0
for i in range(M):
    for p in range(pN[i]-1):
        cp = parties[i][p]
        np = parties[i][p+1]
        union_parent(cp, np)
for i in range(M):
    flag = True
    for p in parties[i]:
        pp = find_parent(p)
        if pp in true_persons:
            flag = False
            break
    if flag:
        answer += 1
print(answer)
