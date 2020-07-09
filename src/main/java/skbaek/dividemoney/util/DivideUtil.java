package skbaek.dividemoney.util;

import lombok.extern.slf4j.Slf4j;
import skbaek.dividemoney.dto.MoneyGiveRequestDto;
import skbaek.dividemoney.entity.give.MoneyGive;
import skbaek.dividemoney.entity.receive.MoneyReceive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class DivideUtil {

    public static List<Integer> divideMoney(MoneyGiveRequestDto requestDto) {
        Random random = new Random();
        List<Integer> moneyList = new ArrayList<>();

        int remain = requestDto.getGiveMoney();
        int recieveMens = requestDto.getRecieveMens();

        for(int i = 1; i <= recieveMens; i++){
            int giveRandomMoney =  randomRange(1, remain);

            //최소값 보장
//            if(giveRandomMoney == 0) giveRandomMoney++;

            if(i == recieveMens) {
                giveRandomMoney = remain;
            } else {
                remain -= giveRandomMoney;
                //분배횟수 보장
                if(remain <= recieveMens) {
                    remain += giveRandomMoney;
                    giveRandomMoney = 1;
                    remain -= giveRandomMoney;
                }
            }

            moneyList.add(giveRandomMoney);

            log.debug("giveMoney : {}, lastremain :{}, peoples : {}", giveRandomMoney, remain, recieveMens);
        }

       return moneyList;
    }

    public static List<MoneyReceive> devideListSet(List<Integer> moneyList, MoneyGive moneyGive){
        List<MoneyReceive> list = new ArrayList<>();
        Random random = new Random();
        int initSize = moneyList.size();
        for(int i=0; i < initSize; i++){
            int combine = random.nextInt(moneyList.size());
            MoneyReceive mr = MoneyReceive.builder()
                    .receiveCheck(false)
                    .receiveMoney(moneyList.get(combine))
                    .receivedHistory(i+1)
                    .token(moneyGive.getToken())
                    .moneyGive(moneyGive)
                    .build();
            moneyGive.addReceive(mr);
            moneyList.remove(combine);
        }
        return list;
    }

    public static int randomRange(int min, int max){
        return (int) (Math.random() * (max - min +1)) + min;
    }
}
