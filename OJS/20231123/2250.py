import sys
ip = sys.stdin.readline

x_point_list = []

class Node:
    def __init__(self,val):
        self.x = -1
        self.parent = -1
        self.left = -1
        self.right = -1
        self.val = val
        self.level = -1
    def find_root_node(self):
        if self.parent == -1:
            self.level = 1
            return self.val
        else:
            return self.parent.find_root_node()
        
    def set_parent(self,parent):
        self.parent = parent
    
    def set_child(self,left,right):
        self.left = left
        self.right = right
    
    def set_my_position(self):
        if self.level == -1:
            self.level = self.parent.level + 1
        if self.left != -1:
            self.left.set_my_position()
        x_point_list.append(self.val)
        if self.right != -1:
            self.right.set_my_position()

N = int(ip())
my_node_dict = {}
my_node_dict[-1] = -1
for i in range(N+1):
    my_node_dict[i] = Node(i)

clue = -1

for i in range(N):
    p,l,r = map(int,ip().split())
    if clue == -1:
        clue = p
    my_node_dict[p].set_child(my_node_dict[l],my_node_dict[r])
    if l != -1:
        my_node_dict[l].set_parent(my_node_dict[p])
    if r != -1:
        my_node_dict[r].set_parent(my_node_dict[p])

root_idx = my_node_dict[clue].find_root_node()

my_node_dict[root_idx].set_my_position()

dist_info_dict = {}

for i in range(N):
    cur = my_node_dict[x_point_list[i]]
    if cur.level not in dist_info_dict:
        dist_info_dict[cur.level] = [i+1]
    else:
        dist_info_dict[cur.level].append(i+1)


answer = 1 # 노드 한개의 넓이는 1이다
level = 1
key_list = sorted(list(dist_info_dict.keys()))
for key in key_list:
    cur_list = dist_info_dict[key]
    if len(cur_list) == 1: # 해당 레벨에 노드가 하나일 경우에는 따로 계산하지 않는다.
        continue
    dist = cur_list[-1] - cur_list[0] + 1
    if answer < dist:
        answer = dist 
        level = key
print(level,answer)