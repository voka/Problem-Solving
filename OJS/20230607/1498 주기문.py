import sys
ip = sys.stdin.readline
mystr = ip().rstrip()
n = len(mystr)
pT = [0]*n
j = 0
for i in range(1, n):
    while (j > 0 and mystr[i] != mystr[j]):
        j = pT[j-1]
    if (mystr[i] == mystr[j]):
        j += 1
        pT[i] = j
    cur = (i+1) - pT[i]
    if ((i+1) % cur == 0 and (i+1) != cur):
        print(i+1, (i+1)//cur)
