package skbaek.dividemoney.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skbaek.dividemoney.common.ExceptionString;
import skbaek.dividemoney.dto.DivideRequestDto;
import skbaek.dividemoney.dto.DivideResponseDto;
import skbaek.dividemoney.entity.give.MoneyGive;
import skbaek.dividemoney.entity.receive.MoneyReceive;
import skbaek.dividemoney.entity.give.MoneyGiveRepository;
import skbaek.dividemoney.entity.receive.MoneyReceiveRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckService {

    private final MoneyGiveRepository giveRepository;

    @Transactional(readOnly = true)
    public DivideResponseDto save(DivideRequestDto requestDto){

        int userId = requestDto.getUserId();
        String roomId = requestDto.getWhichRoom();
        String token = requestDto.getToken();

        MoneyGive moneyGive = giveRepository.findByTokenEqualsAndUserIdNotAndWhichRoomEquals(
                token, userId, roomId );

        DivideResponseDto responseDto = new DivideResponseDto();
        if( moneyGive != null ){
            responseDto.setGiveTime(moneyGive.getGiveTime());
            responseDto.setGiveMoney(moneyGive.getGiveMoney());

            List<MoneyReceive> list = moneyGive.getReceiveList().stream().filter(tmp -> tmp.isReceiveCheck()).collect(Collectors.toList());
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
        } else {
            //7일이 지났거나 조회할 정보가 없을때
            throw new IllegalArgumentException(ExceptionString.NOT_INFO.getMsg());
        }

        return responseDto;
    }

}
