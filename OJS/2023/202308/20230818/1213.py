import sys
ip = sys.stdin.readline
strs = list(ip().rstrip())
mydict = {}
for s in strs:
    if s not in mydict:
        mydict[s] = 1
    else:
        mydict[s] += 1

count = 0
keys = list(mydict.keys())
keys.sort()
odd = ''
for key in keys:
    if mydict[key] % 2 == 1:
        count += 1
        odd = key
if count not in [0, 1]:
    print("I'm Sorry Hansoo")
else:
    front = []
    back = []
    for k in keys:
        cnt = mydict[k]//2
        while cnt:
            cnt -= 1
            front.append(k)
            back.append(k)
    back.reverse()
    print(''.join(front) + odd + ''.join(back))
