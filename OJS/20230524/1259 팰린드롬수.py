import sys
ip = sys.stdin.readline
while (True):
    text = ip().rstrip()
    if text == '0':
        break
    start = 0
    end = len(text) - 1
    result = "yes"
    while (start < end):
        if text[start] != text[end]:
            result = "no"
            break
        start += 1
        end -= 1
    print(result)
