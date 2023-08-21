import sys
ip = sys.stdin.readline

my_str = ip().rstrip()
answer = []
flag = False
cnt = 0


alpha_dict = {2: 'B', 4: 'A'}


def append_alpha(cnt, c):
    while cnt:
        cnt -= 1
        answer.append(c)


for i in range(len(my_str)):
    cur = my_str[i]
    if cur == '.':
        if cnt == 2:
            append_alpha(cnt, alpha_dict[cnt])
            cnt = 0
        if cnt != 0:
            flag = True
            break
        answer.append(cur)
    else:
        cnt += 1
    if cnt == 4:
        append_alpha(cnt, alpha_dict[cnt])
        cnt -= 4
    if i == len(my_str) - 1:
        if cnt == 2:
            append_alpha(cnt, alpha_dict[cnt])
        elif cnt != 0:
            flag = True
if flag:
    print('-1')
else:
    print(''.join(answer))
