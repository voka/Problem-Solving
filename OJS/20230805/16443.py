import sys
import math
ip = sys.stdin.readline
N, atk = map(int, ip().split())
map_list = [tuple(map(int, ip().split())) for _ in range(N)]

"""
i번째 방을 통해서만 i+1번째 방으로 이동 할 수 있습니다. 
전체 sum을 통해서는 구할 수 없는 해답
"""


def simulation(atk, hp, max_hp):
    for i in range(N):
        t, a, h = map_list[i]
        if t == 1:
            # print("용사의 현재 체력은 {0}이고, 공격력은 {1}입니다.".format(hp, atk))
            # print("체력이 {0}이고 공격력이 {1}인 몬스터에게 {2}번 공격을 받았습니다. ".format(
            #    h, a, (math.ceil(h/atk)-1)))
            hp -= a*(math.ceil(h/atk)-1)
        elif t == 2:
            # print("용사의 현재 체력은 {0}이고, 공격력은 {1}입니다.".format(hp, atk))
            # print("용사가 체력을 {0}올려주고 공격력을 {1} 욜려주는 포션을 섭취했습니다.".format(h, a))
            hp = min(max_hp, hp + h)
            atk += a
            # print("포션을 마신뒤 용사의 체력은 {0}이고, 공격력은 {1}입니다.".format(hp, atk))
        if hp <= 0:
            # print("용사의 체력이 0 이하 입니다. 전투를 종료합니다. ")
            return False
    # print("용사의 체력이 {}인 상태로 공주를 구했습니다! 축하합니다!!".format(hp))
    return True


start = 0
end = 999999000000 * 123456 + 1
ans = end
while start <= end:
    cur_hp = (start + end) // 2
    if simulation(atk, cur_hp, cur_hp):
        end = cur_hp - 1
        ans = min(ans, cur_hp)
    else:
        start = cur_hp + 1
print(ans)
