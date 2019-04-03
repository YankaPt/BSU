import random

n = int(input())

arr = [random.randrange(n) for x in range(n)]

print(*arr)
