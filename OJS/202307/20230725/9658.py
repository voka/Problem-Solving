import sys
ip = sys.stdin.readline
N = int(ip())
if N % 7 == 1 or N % 7 == 3:
    print("CY")
else:
    print("SK")
