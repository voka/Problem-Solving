from itertools import product
result = []
def r_combination(_list,choice,cur):
    if choice > len(_list):
        return 
    if choice == 0:
        result.append(cur)
    else:
        for i in range(len(_list)):
            r_combination(_list,choice-1,cur + [_list[i]])
cur = product([1,2,3,4,5],repeat=3)
print(len(list(cur)))
r_combination([1,2,3,4,5],3,[])
print(len(result))