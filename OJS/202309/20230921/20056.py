import sys
ip = sys.stdin.readline 
"""
파이어볼 개수 -> M 개 
i번째 파이어볼 -> 위치 : ri,ci / 질량 mi , 방샹 di, 속력 si
(r 행 c 열 -> x -> c, r -> y)
방향은 대각선 포함 총 8 방향임 


- 마법사가 파이어볼에게 이동 명령을 내리면 
1. 파이어볼이 방향 di로 속력 si 칸 만큼 이동 (방향 X 속력 해서 더해야 할듯)
2. 이동이 모두 끝나고, 2개 이상의 파이어 볼이 있는 칸이 있다면 
    2-1. 같은 칸의 파이어볼은 모두 하나로 합쳐짐
    2-2. 파이어볼은 4개의 파이어볼로 나누어짐 
    2-3. 나누어진 파이어볼은 다음과 같은 특징을 가짐 
        - 질량 : 합쳐진 파이어볼 질량의 합 / 5
        - 속력 : 랍쳐진 파이어볼 속력의 합 / 합쳐진 파이어볼의 개수
        - 방향 : 합쳐지는 파이어볼의 방향이 모두 홀수 혹은 짝수 : 0,2,4,6 아니면 1,3,5,7


구해야 하는 답 : K 번 이동명령을 한 후 남아있는 질량의 합
"""

N,M,K = map(int,ip().split())
fire_ball = list(list(map(int,ip().split())) for _ in range(M))
# for r,c,m,s,d in fire_ball:
#     print(r,c,m,s,d)

dx = [0,1,1,1,0,-1,-1,-1]
dy = [-1,-1,0,1,1,1,0,-1]

def move():
    global fire_ball
    result = []
    #print(fire_ball)
    for r,c,m,s,d in fire_ball:
        cnt = s % N # N 번만큼 같은 방향으로 이동하면 제자리에 옴
        for _ in range(cnt):
            nc,nr = c + dx[d], r + dy[d]
            if nc <= 0:
                nc += N
            if nr <= 0:
                nr += N
            if nc >= N+1 :
                nc -= N
            if nr >= N+1:
                nr -= N
            c,r = nc,nr
        result.append([r,c,m,s,d])
    return result

def add_fire(fire_list):
    result = []
    my_rc = {}
    #print(fire_list)
    for r,c,m,s,d in fire_list:
        if (c,r) in my_rc :
            my_rc[(c,r)].append([m,s,d])
        else:
            my_rc[(c,r)] = [[m,s,d]]
    for (c,r) in my_rc.keys():
        n = len(my_rc[(c,r)])
        if n >= 2:
            tm = 0
            ts = 0
            dOdd = True
            dEven = True
            for m,s,d in my_rc[(c,r)]:
                tm += m 
                ts += s 
                if d % 2 == 0:
                    dEven = False
                else:
                    dOdd = False
            tm = tm // 5
            ts = ts // n
            #print(tm)
            if tm == 0 :
                continue
            if dOdd or dEven:
                for i in range(0,7,2):
                    result.append((r,c,tm,ts,i))
            else:
                for i in range(1,8,2):
                    result.append((r,c,tm,ts,i))
        else:
            m,s,d = my_rc[(c,r)][0]
            result.append([r,c,m,s,d])
        
    return result

for i in range(K):
    #print("Start")
    #print(fire_ball)
    move_result = move()
    #print("Move")
    #print(move_result)
    fire_ball = add_fire(move_result)
    #print("Fire_ball")
    #print(fire_ball)
ans = 0
#print(fire_ball)
for r,c,m,s,d in fire_ball:
    ans += m 
print(ans)

"""
X X A X
X X X X
X X X X
X X X X
"""