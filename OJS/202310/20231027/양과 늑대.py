class Node:
    def __init__(self,idx):
        self.idx = idx
        self.max_sheep = 0
        self.min_wolf = 20
        self.left = -1
        self.right = -1
        self.parent = -1
    
    def add_child(self,child_node):
        if self.left == -1:
            self.left = child_node
        else:
            self.right = child_node
    
    def register_parent(self,parent_node):
        self.parent = parent_node    
        
    def post_travel(self):
        #print(self.idx,self.max_sheep)
        a, b = 0, 0
        if self.left != -1:
            a = self.left.post_travel()
        if self.right != -1:
            b = self.right.post_travel()
        return max(self.max_sheep,max(a,b))
    def find_max_sheep(self,travle,info,sheep,wolf):
        #print(self.idx,sheep,wolf,self.max_sheep,self.min_wolf,travle)
        self.max_sheep = max(self.max_sheep,sheep)
        self.min_wolf = min(self.min_wolf,wolf)
        if self.left != -1:
            cleft = self.left.idx
            if travle[cleft] == 0:
                cur = info[cleft]
                if cur == 1: # 늑대인 경우
                    if sheep > wolf + 1 : # 양이 늑대보다 많은 경우
                        #if self.left.max_sheep < sheep : # 저장된 최대 양 수 보다 현재 양 수가 많은 경우에만
                        travle[cleft] = 1
                        self.left.find_max_sheep(travle,info,sheep,wolf+1)
                        travle[cleft] = 0
                else: # 양인 경우
                    #if self.left.max_sheep < sheep + 1 :
                    travle[cleft] = 1
                    self.left.find_max_sheep(travle,info,sheep+1,wolf)
                    travle[cleft] = 0
        if self.right != -1 :
            cright = self.right.idx
            if travle[cright] == 0:
                cur = info[cright]
                if cur == 1: # 늑대인 경우
                    if sheep > wolf + 1 : # 양이 늑대보다 많은 경우
                        #if self.right.max_sheep < sheep : # 저장된 최대 양 수 보다 현재 양 수가 많은 경우에만
                        travle[cright] = 1
                        self.right.find_max_sheep(travle,info,sheep,wolf+1)
                        travle[cright] = 0
                else: # 양인 경우
                    travle[cright] = 1
                    self.right.find_max_sheep(travle,info,sheep+1,wolf)
                    travle[cright] = 0
        if self.left != -1 and travle[cleft] != 0 and travle[cleft] != 3:
            travle[cleft] += 1
            self.left.find_max_sheep(travle,info,sheep,wolf)
        if self.right != -1 and travle[cright] != 0 and travle[cright] != 3:
            travle[cright] += 1
            self.right.find_max_sheep(travle,info,sheep,wolf)
        if self.parent != -1 :
            self.parent.find_max_sheep(travle,info,sheep,wolf)
                
            
        
        
def solution(info, edges):
    answer = 0
    my_list = [ Node(i) for i in range(len(info))]
    
    for [p,c] in edges:
        my_list[p].add_child(my_list[c])
        my_list[c].register_parent(my_list[p])
        
    root = my_list[0]
    travle = [0]*(len(info))
    travle[0] = 1
    root.find_max_sheep(travle,info,1,0)
    answer = root.post_travel()
    print(answer)
    return answer

info = [0,0,1,1,1,0,1,0,1,0,1,1]
edge = [[0,1],[1,2],[1,4],[0,8],[8,7],[9,10],[9,11],[4,3],[6,5],[4,6],[8,9]]
solution(info,edge)