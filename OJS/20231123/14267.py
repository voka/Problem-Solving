import sys
ip = sys.stdin.readline
class Node:
    def __init__(self, idx):
        self.idx = idx
        self.val = 0
        self.parent = -1
        self.child = []
    def add_child(self,child_node):
        self.child.append(child_node)
    def set_parent(self,parent_node):
        self.parent = parent_node
    # 상사한테만 값 더하기
    def add_value(self,val):
        self.val += val
    # 직속 부하들 에게만 값 전달
    def spread(self):
        for c in self.child:
            c.add_value(self.val)
            
            
n,m = map(int,ip().split())
node_dict = {}
_list = list(map(int,ip().split()))

for i in range(1,n+1):
    node_dict[i] = Node(i)

for i in range(2,n+1):
    p = _list[i-1]
    node_dict[i].set_parent(node_dict[p])
    node_dict[p].add_child(node_dict[i])

# 상사에게 칭찬값 더하기
for i in range(m):
    idx,val = map(int,ip().split())
    node_dict[idx].add_value(val)

# 직속 부하들에게만 값 더해주기
for i in range(2,n+1):
    node_dict[i].spread()

answer = [node_dict[i].val for i in range(1,n+1)]

print(*answer)