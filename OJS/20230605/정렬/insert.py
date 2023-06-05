arr = [2, 4, 9, 5, 3, 6, 8, 7, 1, 10]
'''
삽입 정렬 -> 1 번째 자리 숫자부터 그 앞의 인덱스에 있는 자리와 비교해 
자신의 자리를 찾아가는 정렬 (자깃보다 큰 놈들이 앞에 있을 경우 그놈들을 뒤로 보낸다.)


'''

for i in range(1, 10):
    j = i - 1
    key = arr[i]
    while arr[j] > key and j >= 0:
        arr[j+1] = arr[j]
        j -= 1
    arr[j+1] = key
print(arr)
