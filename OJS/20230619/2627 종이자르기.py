import sys
ip = sys.stdin.readline
N, M = map(int, ip().split())
C = int(ip())
rows = [M]
cols = [N]
for i in range(C):
    command, line = map(int, ip().split())
    if command == 0:
        rows.append(line)
    else:
        cols.append(line)

rows.sort()
cols.sort()
max_row_len = -1
max_col_len = -1
pre_row = 0
pre_col = 0
for i in rows:
    max_row_len = max(i - pre_row, max_row_len)
    pre_row = i

for i in cols:
    max_col_len = max(i - pre_col, max_col_len)
    pre_col = i

print(max_col_len * max_row_len)
