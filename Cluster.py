import math
with open("data.txt","r") as f:
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
for i in range(len(final)):
    for j in range(len(final[i])-1):
        for k in range(i+1,len(final)):
            for z in range(len(final[k])-1):
                form = math.sqrt((final[i][j] - final[k][z])**2 + (final[i][j+1] - final[k][z+1])**2)
                coordinates.append(form)
        
print(coordinates)