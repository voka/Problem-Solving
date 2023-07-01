arr = [2, 4, 1, 5, 3, 6, 8, 7, 9, 10]


def quicksort(_list):
    n = len(_list)

    if n <= 1:
        return _list

    pivot = _list[n // 2]
    # pivot 보다 작은 값들
    left = []
    # pivot 보다 큰 값들
    right = []
    # pivot과 같은 값들
    equals = []
    for a in _list:
        if a < pivot:
            left.append(a)
        elif a == pivot:
            equals.append(a)
        else:
            right.append(a)
    return quicksort(left) + equals + quicksort(right)


result = quicksort(arr)
print(result)
