package skbaek.dividemoney.util;

import lombok.extern.slf4j.Slf4j;
import skbaek.dividemoney.dto.MoneyGiveRequestDto;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.entity.MoneyReceive;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
public class DivideUtil {

    public static List<Integer> divideMoney(MoneyGive requestDto) {
        Random random = new Random();
        List<Integer> moneyList = new ArrayList<>();

        int remain = requestDto.getGiveMoney();
        int recieveMens = requestDto.getRecieveMens();

        for(int i = 1; i <= recieveMens; i++){
            int giveRandomMoney = random.nextInt(remain);

            //최소값 보장
            if(giveRandomMoney == 0) giveRandomMoney++;


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
            list.add(MoneyReceive.builder()
                    .receiveCheck(false)
                    .receiveMoney(moneyList.get(combine))
                    .receivedHistory(i+1)
                    .token(moneyGive.getToken())
                    .moneyGive(moneyGive)
                    .build());
            moneyList.remove(combine);
        }
        return list;
    }
}
