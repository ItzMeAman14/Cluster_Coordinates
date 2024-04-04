import math
import Prims

with open("testing_data.txt","r") as f:
    ftp = f.readlines()

l = []
for i in ftp:
    l.append(i.split(","))

final = []
temp = []
for i in l:
    for j in i:
        temp.append(float(j))
    final.append(temp)
    temp = []

print(final)
coordinates = []
diff = 0
for i in range(len(final)):
    for j in range(i+1,len(final)):
        for k in range(0,len(final[j])):
            diff += (final[i][k] - final[j][k])**2

        diff = math.sqrt(diff)
        coordinates.append(diff)
        diff = 0

Adj = [[0 for i in range(len(final))] for j in range(len(final[0]))]
k = 0
for i in range(len(final)):
    for j in range(i+1,len(final)):
        if i!=j:
            Adj[i][j] = coordinates[k]
            print(coordinates[k])
            k+=1

g = Prims.Graph(len(Adj))
g.graph = Adj
g.primMST()
# print(Adj)
# print(coordinates)