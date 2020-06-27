package skbaek.dividemoney.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skbaek.dividemoney.common.ExceptionString;
import skbaek.dividemoney.dto.DivideRequestDto;
import skbaek.dividemoney.dto.DivideResponseDto;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.entity.MoneyReceive;
import skbaek.dividemoney.repository.MoneyGiveRepository;
import skbaek.dividemoney.repository.MoneyReceiveRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckService {

    private final MoneyReceiveRepository receiveRepository;
    private final MoneyGiveRepository giveRepository;

    @Transactional(readOnly = true)
    public DivideResponseDto save(DivideRequestDto requestDto){

        Optional<MoneyGive> moneyGive = giveRepository.findByTokenEqualsAndUserIdEqualsAndWhichRoomEqualsAndGiveTimeGreaterThanEqual(
                requestDto.getToken(), requestDto.getUserId(), requestDto.getWhichRoom(), LocalDateTime.now().minusDays(7L) );

        //7일이 지났거나 조회할 정보가 없을때
        if(!moneyGive.isPresent()) throw new IllegalArgumentException(ExceptionString.NOT_INFO.getMsg());

        DivideResponseDto responseDto = new DivideResponseDto();
        if(moneyGive.isPresent()){
            MoneyGive data = moneyGive.get();
            responseDto.setGiveTime(data.getGiveTime());
            responseDto.setGiveMoney(data.getGiveMoney());

            List<MoneyReceive> list = data.getReceiveList().stream().filter(tmp -> tmp.isReceiveCheck()).collect(Collectors.toList());
            List<Map<String, Integer>> receivedMensList = new ArrayList<>();
            int totalSeed = 0;

            for(MoneyReceive summary : list){
                Map<String, Integer> map = new HashMap<>();
                totalSeed += summary.getReceiveMoney();
                map.put("receiveMoney", summary.getReceiveMoney());
                map.put("receiveMen", summary.getReceiveMen());
                receivedMensList.add(map);
            }
            responseDto.setReceivedMoney(totalSeed);
            responseDto.setReceiveList(receivedMensList);
        }

        return responseDto;
    }

}
