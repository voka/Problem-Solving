arr = [2, 4, 1, 5, 3, 6, 8, 7, 9, 10]
result = [0]*10


def devide(start, end):
    if start < end:
        mid = (start + end) // 2
        devide(start, mid)
        devide(mid+1, end)
        merge(start, mid, end)


def merge(start, mid, end):
    for i in range(start, end):
        result[i] = arr[i]
    l = start
    r = mid
    main_idx = start
    print(start, mid, end)
    while (l < mid and r < end):
        if result[l] <= result[r]:
            arr[main_idx] = result[l]
            l += 1
        else:
            arr[main_idx] = result[r]
            r += 1
        main_idx += 1
    for i in range(mid - l):
        arr[main_idx + i] = result[l + i]
    print(arr)


def merge_sort(start, end):
    devide(start, end)


merge_sort(0, 10)
