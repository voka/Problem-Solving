def solution(id_list, report, k):
    # k 번 만큼 신고 당하면 탈락~
    n = len(id_list)
    answer = [0]*n
    user_dict = {}
    report_dict = {}
    for i in range(n):
        user_dict[id_list[i]] = i
        report_dict[i] = set()
    for text in report:
        user, target = text.split()
        user_id = user_dict[user]
        target_id = user_dict[target]
        report_dict[target_id].add(user_id)
    for key in report_dict:
        cur = len(report_dict[key])
        if cur >= k:
            for mini in report_dict[key]:
                answer[mini] += 1
    return answer