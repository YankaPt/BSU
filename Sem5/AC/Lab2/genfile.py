import random

n = int(input())
arr = list(range(n))
random.shuffle(arr)

for number in arr:
    print(number, end=' ')
