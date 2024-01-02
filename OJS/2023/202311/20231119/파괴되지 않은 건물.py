# WORST_O => O(10^3*10^3*25*10^4) = O(25*10^10) -> 25초 ?? 
from pprint import pprint
def solution(board, skill):
    answer = 0
    n = len(board)
    m = len(board[0])
    score_board = [[0]*(m+1) for _ in range(n+1)]
    
    
    for [t, r1, c1, r2, c2, degree] in skill:
        t = 1 if t==2 else -1
        degree = degree * t
        score_board[r2+1][c2+1] += degree
        score_board[r2+1][c1] -= degree
        score_board[r1][c2+1] -= degree
        score_board[r1][c1] += degree 
    #pprint(score_board)
    
    for i in range(n):
        for j in range(m+1):
            #print(i+1,j)
            score_board[i+1][j] += score_board[i][j]
    #pprint(score_board)
    for i in range(n+1):
        for j in range(m):
            score_board[i][j+1] += score_board[i][j]
    #pprint(score_board)
    
    for i in range(n):
        for j in range(m):
            if score_board[i][j] + board[i][j] > 0:
                answer += 1
    return answer

# 효율성 풀이 - 1 (중복범위 제거) # 20분
# 효율성 2개 더 맞음 -> 중복되는 범위가 있을거 같으니 dict에 넣는다. 
# def solution(board, skill):
#     answer = 0
#     my_skill_dict = {}
#     n = len(board)
#     m = len(board[0])
#     for [t, r1, c1, r2, c2, degree] in skill:
#         t = 1 if t==2 else -1
#         degree = degree * t
#         if (r1,c1,r2,c2) not in my_skill_dict :
#             my_skill_dict[(r1,c1,r2,c2)] = degree
#         else:
#             my_skill_dict[(r1,c1,r2,c2)] += degree
#     for key in my_skill_dict:
#         (r1,c1,r2,c2) = key
#         degree = my_skill_dict[key]
#         for i in range(r1,r2+1):
#             for j in range(c1,c2+1):
#                 board[i][j] += degree 
#     for i in range(n):
#         for j in range(m):
#             if board[i][j] > 0:
#                 answer += 1
#     return answer


# # 아주 쉬운 풀이
# # 10분 
# def solution(board, skill):
#     answer = 0
#     n = len(board)
#     m = len(board[0])
#     for [t, r1, c1, r2, c2, degree] in skill:
#         t = 1 if t==2 else -1
#         degree = degree * t
#         for i in range(r1,r2+1):
#             for j in range(c1,c2+1):
#                 board[i][j] += degree 
#         #pprint(board)
#     for i in range(n):
#         for j in range(m):
#             if board[i][j] > 0:
#                 answer += 1
#     return answer