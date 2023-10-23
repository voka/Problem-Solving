from itertools import permutations
result = []
def permutation(_list,choice,cur):
    if choice == 0:
        result.append(cur)
    else:
        for i in _list:
            if i not in cur:
                permutation(_list,choice-1,cur + [i])
    pass

cur = permutations([1,2,3,4,5],3)
print(len(list(cur)))
permutation([1,2,3,4,5],3,[])
print(len(result))