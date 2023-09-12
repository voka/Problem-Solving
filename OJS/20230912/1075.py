N = int(input())
F = int(input())


last_num = N % 100 

start = N - last_num
end = N - last_num + 100 
ans = 0

for i in range(start,end):
    if i % F == 0:
        ans = i % 100
        break
#print(start,end)
if ans < 10 :
    ans = '0'+str(ans)
print(ans)