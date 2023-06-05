arr = [2, 4, 1, 5, 3, 6, 8, 7, 9, 10]
'''
버블 정렬은 0번째 부터 시작해 자신의 뒤에 있는 녀석들과 비교하면서 자신보다 작은 놈이 자신보다 뒤에 있다면 그 자리를 바꾼다. 
'''

for i in range(10):
    for j in range(9):
        if arr[j] > arr[j+1]:
            arr[j], arr[j+1] = arr[j+1], arr[j]
print(arr)
