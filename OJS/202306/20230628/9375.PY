"""
패션왕 신해빈

첫째 줄에 테스트 케이스가 주어진다. 테스트 케이스는 최대 100이다.

각 테스트 케이스의 첫째 줄에는 해빈이가 가진 의상의 수 n(0 ≤ n ≤ 30)이 주어진다.
다음 n개에는 해빈이가 가진 의상의 이름과 의상의 종류가 공백으로 구분되어 주어진다. 같은 종류의 의상은 하나만 입을 수 있다.
모든 문자열은 1이상 20이하의 알파벳 소문자로 이루어져있으며 같은 이름을 가진 의상은 존재하지 않는다.

각 테스트 케이스에 대해 해빈이가 알몸이 아닌 상태로 의상을 입을 수 있는 경우를 출력하시오.

"""
T = int(input())
while T:
    T -= 1
    N = int(input())
    ans = 1
    cur_dict = {}
    for i in range(N):
        val, key = input().split()
        if key in cur_dict:
            cur_dict[key] += 1
        else:
            cur_dict[key] = 1
    for key in cur_dict.keys():
        ans *= cur_dict[key] + 1
    print(ans-1)
