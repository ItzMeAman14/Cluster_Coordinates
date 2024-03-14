import random

N = int(input("Enter number of points to generate: "))
dimensions = int(input("Enter dimensions: "))
precision = 1

points = []
while len(points) < N:
    point = []
    for _ in range(dimensions):
        point.append(str(round(random.uniform(0.0, 20.0), precision)))
    if point not in points:
        points.append(point)

try:
    with open('data.txt', 'w') as f:
        for point in points:
            print('Adding point:', point)
            f.write(','.join(point) + "\n")
    print('`data.txt` created successfully')
except IOError:
    print("Error: could not create file `data.txt`")
