import sys
ip = sys.stdin.readline
arr = []
for _ in range(5):
    arr.append(int(ip()))
arr.sort()
print(sum(arr)//5)
print(arr[2])
