package skbaek.dividemoney.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skbaek.dividemoney.common.ExceptionString;
import skbaek.dividemoney.dto.MoneyReceiveRequestDto;
import skbaek.dividemoney.dto.MoneyReceiveResponseDto;
import skbaek.dividemoney.entity.give.MoneyGive;
import skbaek.dividemoney.entity.receive.MoneyReceive;
import skbaek.dividemoney.entity.give.MoneyGiveRepository;
import skbaek.dividemoney.entity.receive.MoneyReceiveRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReceiveService {

    private final MoneyReceiveRepository moneyReceiveRepository;
    private final MoneyGiveRepository moneyGiveRepository;

    @Transactional
    public MoneyReceiveResponseDto save(MoneyReceiveRequestDto requestDto) {

        int userId = requestDto.getUserId();
        String roomId = requestDto.getWhichRoom();
        String token = requestDto.getToken();
        int receiveMoney = 0;
        int EXPIRED_MINUTE = 10;

        MoneyGive moneyGive = moneyGiveRepository.findByTokenEqualsAndUserIdNotAndWhichRoomEquals(token, userId, roomId);
        if(moneyGive != null) {

            //줍기 허용시간 체크
            if( moneyGive.getGiveTime().plusMinutes(EXPIRED_MINUTE).isBefore(LocalDateTime.now()) )
                throw new IllegalArgumentException(ExceptionString.EXPIRED_TIME.getMsg());
            //줍기 중복 체크
            if( moneyGive.getReceiveList().stream().filter( data -> data.getReceiveMen() == userId ).count() > 0 )
                throw new IllegalArgumentException(ExceptionString.DONE_RECEIVED.getMsg());
            //줍기 주체 여부 체크
            if( moneyGive.getUserId() == userId )
                throw new IllegalArgumentException(ExceptionString.NOT_OWN_RECEIVED.getMsg());
            //줍기 end
            if( moneyGive.getReceiveList().stream().filter( data -> !data.isReceiveCheck() ).count() == 0 )
                throw new IllegalArgumentException(ExceptionString.EVENT_END.getMsg());

            List<MoneyReceive> mrList = moneyGive.getReceiveList().stream().filter(
                    filterData -> filterData.isReceiveCheck() == false)
                    .collect(Collectors.toList());

            MoneyReceive mr = mrList.get(0);
            mr.setReceiveCheck(true);
            mr.setReceiveMen(userId);
            receiveMoney = mr.getReceiveMoney();
            moneyReceiveRepository.save(mr);

        } else {
            log.debug("not find");
            //줍기 방 체크
            throw new IllegalArgumentException(ExceptionString.NOT_EVENT_ROOM.getMsg());
        }

        return new MoneyReceiveResponseDto(receiveMoney);
    }
}
