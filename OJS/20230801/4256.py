import sys
ip = sys.stdin.readline

T = int(ip())
while T:
    T -= 1
    N = int(ip())
    Pre = list(map(int, ip().split()))
    In = list(map(int, ip().split()))
    Post = []
    mydict = {}
    for i in range(N):
        mydict[In[i]] = i

    def devide(Ps, Pe, Is, Ie):
        if Ps < Pe and Is < Ie:
            # print(Ps, Pe, Is, Ie)
            root = Pre[Ps]
            find_idx = mydict[root]
            left_num = find_idx - Is  # 왼쪽에 남은 개수
            right_num = Ie - find_idx - 1  # 오른쪽 남은 개수
            devide(Ps+1, Ps + left_num + 1, Is, Is + left_num)
            devide(Ps + left_num + 1, Pe, Ie - right_num, Ie)
            print(root, end=" ")

    devide(0, N, 0, N)
    print()
"""
3 6 5 4 8 7 1 2 Pre
5 6 8 4 3 1 2 7 In



1. 0 N 0 N 
root = Pre[0]
find_idx = mydict[root] = 4

Pre -> 1 ~ 1 + 4-> 1 ~ 5, 4 + 1 ~ Pe
In -> 0 ~ find_idx - 1, find_dix +1 ~ Ie

2. 1 5 0 4
root = Pre[1] = 6
find_idx = 1
Pre -> Ps + 1 = 2 ~ 2, 2 ~ 5 
In -> 0 ~ 1, 2 ~ 4 

3. 2 2 0 1
X

4. 2 5 2 4 
root = Pre[2] = 5
find_idx = 0
원래 
Pre -> 3 ~ 1, 2, 0 
In -> 1 ~5, 1 ~ 4


"""
