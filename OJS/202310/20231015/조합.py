from itertools import combinations
result = []
def combination(_list,choice,cur):
    if choice > len(_list):
        return 
    if choice == 0:
        result.append(cur)
    else:
        for i in range(len(_list) - choice + 1):
            combination(_list[i+1:],choice-1,cur + [_list[i]])
cur = combinations([1,2,3,4,5],3)
print(len(list(cur)))
combination([1,2,3,4,5],3,[])
print(len(result))
print(result)