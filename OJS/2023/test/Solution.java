/**
구매 우선순위
가격 , 최근 등록
판매 프로세스 
- sell_amount -> 판매할 골드 양
- sell_price -> 판매할 실버 양
1. 판매의 경우 구매의 우선순위에 따라 구매 요청을 선택
    - 원하는 판매 가격 이상의 구매 요청을 찾지 못했을 경우 pending 상태로 변경
2. 구매자, 판매자 중 작은 가격이 있는 쪽의 양 만큼 거래 진행
3. 골드의 거래 량 x sell_price 만큼의 거래 금액 발생
4. 구매자는 거래금액 만큼 감소
5. 판매자는 거래금액 만큼 증가
-> 구매 프로세스도 동일하게 진행
모든 거래는 sell_price 기준으로 진행
*/
import java.util.*;
class Buy implements Comparable<Buy>{
    public int user;
    public int idx;
    public int buyAmount;
    public int buyPrice;

    public Buy(int id, int u, int buyA, int buyP){
        user = u;
        idx = id;
        buyAmount = buyA;
        buyPrice = buyP;
    }

    @Override
    public int compareTo(Buy buy){
        if(this.buyPrice > buy.buyPrice){
            return -1; 
        }
        else if(this.buyPrice < buy.buyPrice){
            return 1;
        }
        else{
            if(this.idx > buy.idx){
                return 1;
            }
            else{
                return -1;
            }
        }
    } 
}
class Sell implements Comparable<Sell>{
    public int user;
    public int idx;
    public int sellAmount;
    public int sellPrice;

    public Sell(int id, int u, int sellA, int sellP){
        user = u;
        idx = id;
        sellAmount = sellA;
        sellPrice = sellP;
    }

    @Override
    public int compareTo(Sell sell){
        if(this.sellPrice < sell.sellPrice){
            return -1; 
        }
        else if(this.sellPrice > sell.sellPrice){
            return 1;
        }
        else{
            if(this.idx > sell.idx){
                return 1;
            }
            else{
                return -1;
            }
        }
    } 
}

class Solution {
    public String[] solution(String[] req_id, int[][] req_info) {
        int sidx = 0;
        int bidx = 0;
        int[] gold = new int[50001]; // 골드 변화량
        int[] silver = new int[50001]; // 실버 변화량
        String[] answer = {};
        PriorityQueue<Buy> bpq = new PriorityQueue<>();
        PriorityQueue<Sell> spq = new PriorityQueue<>();
        Map<String,Integer> myMap = new HashMap<>();
        int idIter = 0;
        for(int i=0;i<req_id.length;++i){
            String name = req_id[i];
            if(!myMap.containsKey(name)){
                myMap.put(name,idIter++);
            }
        }
        for(int i=0;i<0;++i){
            // 유저 id;
            int useridx = myMap.get(req_id[i]); 
            int type = req_info[i][0], amount = req_info[i][1], price = req_info[i][2];
            // 구매 요청인 경우
            if(type == 0){ 
                // 판매요청 대기 중이지 않을때만 // 판매 요청 검색
                while(true){
                    if(spq.isEmpty()){
                        bpq.add(new Buy(bidx++,useridx,amount,price));
                        break;// 판매 요청이 없다면 구매 요청에 등록해 둔뒤 종료
                    }
                    Sell temp = spq.peek();
                    if(temp.sellPrice > price){
                        bpq.add(new Buy(bidx++,useridx,amount,price));
                        break;// 판매 요청 최소 가격이 현재 구매요청 가격보다 높은 경우 구매요청 등록후 종료
                    }
                    Sell lower = spq.poll();
                    // // 구매 요청 가격 <= 판매 가격
                    // if(lower.sellPrice <= price){
                    // 거래 진행 -> 거래 양 측정)
                    if(lower.sellAmount >= amount){ 
                        int silverAmount = amount * lower.sellPrice;
                        // 구매자는 실버양 감소, 골드양 증가;
                        silver[useridx] -= silverAmount;
                        gold[useridx] += amount;
                        // 판매자는 실버양 증가, 골드양 감소;
                        silver[lower.user] += silverAmount;
                        gold[lower.user] -= amount;
                        if(lower.sellAmount > amount){ // sell만 다시 재등록
                            spq.add(new Sell(lower.idx,lower.user,lower.sellAmount - amount,lower.sellPrice));
                        }
                    } 
                    else{ // 구매자의 거래양이 더 많을 경우
                        int silverAmount = lower.sellAmount * lower.sellPrice;
                        // 구매자는 실버양 감소, 골드양 증가;
                        silver[useridx] -= silverAmount;
                        gold[useridx] += amount;
                        // 판매자는 실버양 증가, 골드양 감소;
                        silver[lower.user] += silverAmount;
                        gold[lower.user] -= amount;
                    }
                    //}
                }
                
            }
            else{ // 판매 요청인 경우
                // 구매 요청 검색
                while(true){
                    if(bpq.isEmpty()){
                        spq.add(new Sell(sidx++,useridx,amount,price));
                        break;// 구매 요청이 없다면 판매 요청에 등록해 둔뒤 종료
                    }
                    Buy temp = bpq.peek();
                    if(temp.buyPrice < price){
                        spq.add(new Sell(sidx++,useridx,amount,price));
                        break;// 최대 구매 금액이 판매 가격보다 작을 경우 판매 요청에 등록해 둔뒤 종료
                    }
                    Buy higher = bpq.poll();
                    // 거래 진행 -> 거래 양 측정)
                    if(higher.buyAmount >= amount){ 
                        int silverAmount = amount * price;
                        // 판매자는 실버양 증가, 골드양 감소;
                        silver[useridx] += silverAmount;
                        gold[useridx] -= amount;
                        // 구매자는 실버양 감소, 골드양 증가;
                        silver[higher.user] -= silverAmount;
                        gold[higher.user] += amount;
                        if(higher.buyAmount > amount){ // buy만 다시 재등록
                            bpq.add(new Buy(higher.idx,higher.user,higher.buyAmount - amount,higher.buyPrice));
                        }
                    } 
                    else{ // 판매자의 거래양이 더 많을 경우
                        int silverAmount = higher.buyAmount * price;
                        // 판매자는 실버양 증가, 골드양 감소;
                        silver[useridx] += silverAmount;
                        gold[useridx] -= amount;
                        // 구매자는 실버양 감소, 골드양 증가;
                        silver[higher.user] -= silverAmount;
                        gold[higher.user] += amount;
                    }
                }
            }
        }
        // pq.add(new Buy(1,100,400));
        // pq.add(new Buy(2,100,200));
        // pq.add(new Buy(3,100,300));
        // while(!pq.isEmpty()){
        //     Buy buy = pq.poll();
        //     System.out.printf("%d\n",buy.idx);
        // }
        // spq.add(new Sell(1,100,200));
        // spq.add(new Sell(2,100,200));
        // spq.add(new Sell(3,100,100));
        // while(!spq.isEmpty()){
        //     Sell sell = spq.poll();
        //     System.out.printf("%d\n",sell.idx);
        // }
        String.valueOf(idIter)
        return answer;
    }
}