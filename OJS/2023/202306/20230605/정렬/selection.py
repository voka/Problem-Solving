arr = [2, 4, 1, 5, 3, 6, 8, 7, 9, 10]

'''
선택 정렬은 제일 작은 값을 arr이 중에서 골라 0번 인덱스에 넣고 하는 방식이다.
'''

for i in range(10):
    min_idx = i
    for j in range(i+1, 10):  # i 번째 인덱스에 들어갈 녀석을 j번째에서 찾는 거다. !
        if arr[j] < arr[min_idx]:
            min_idx = j
    if min_idx != i:
        arr[i], arr[min_idx] = arr[min_idx], arr[i]
print(arr)
