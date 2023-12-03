import sys
ip = sys.stdin.readline 

lv, com = ip().rstrip().split()
lv = int(lv)
answer = 0
if com == "bad":
    answer = lv * 200
elif com == "cool":
    answer = lv * 400
elif com == "great":
    answer = lv * 600
elif com == "perfect":
    answer = lv * 1000
print(answer)