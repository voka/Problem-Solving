import sys
ip = sys.stdin.readline 

my_str = ip().rstrip()
sub_str = set()
N = len(my_str)
for i in range(N):
    for j in range(i+1,N+1):
        sub_str.add(my_str[i:j])

print(len(sub_str))

