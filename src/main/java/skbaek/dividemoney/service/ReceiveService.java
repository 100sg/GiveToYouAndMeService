package skbaek.dividemoney.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skbaek.dividemoney.common.ExceptionString;
import skbaek.dividemoney.dto.MoneyReceiveRequestDto;
import skbaek.dividemoney.dto.MoneyReceiveResponseDto;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.entity.MoneyReceive;
import skbaek.dividemoney.repository.MoneyGiveRepository;
import skbaek.dividemoney.repository.MoneyReceiveRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        //줍기 허용시간 체크
        if(moneyGiveRepository.existsByTokenAndUserIdNotAndWhichRoomAndGiveTimeLessThanEqual(token, userId, roomId, LocalDateTime.now().minusMinutes(10)))
            throw new IllegalArgumentException(ExceptionString.EXPIRED_TIME.getMsg());
        //줍기 중복 체크
        if(moneyReceiveRepository.existsByReceiveMenEqualsAndTokenEquals(userId, token))
            throw new IllegalArgumentException(ExceptionString.DONE_RECEIVED.getMsg());
        //줍기 주체 여부 체크
        if(moneyGiveRepository.existsByTokenEqualsAndUserIdEqualsAndWhichRoomEquals(token, userId, roomId))
            throw new IllegalArgumentException(ExceptionString.NOT_OWN_RECEIVED.getMsg());
        //줍기 방 체크
        if(!moneyGiveRepository.existsByTokenEqualsAndWhichRoomEquals(token, roomId))
            throw new IllegalArgumentException(ExceptionString.NOT_EVENT_ROOM.getMsg());
        //줍기 end
        if(!moneyReceiveRepository.existsByTokenEqualsAndReceiveCheckFalse(token))
            throw new IllegalArgumentException(ExceptionString.EVENT_END.getMsg());


        Optional<MoneyGive> moneyGive = moneyGiveRepository.findByTokenAndUserIdNotAndWhichRoom(token, userId, roomId);
//        MoneyReceiveResponseDto res = null;
        MoneyReceive mr = null;
        if(moneyGive.isPresent()) {
            List<MoneyReceive> mrList = new ArrayList<>();
            mrList = moneyGive.get().getReceiveList();
            List<MoneyReceive> filter = mrList.stream().filter(data -> data.isReceiveCheck() == false).collect(Collectors.toList());

//            for (int i = 0; i < filter.size(); i++) {
                mr = filter.get(0);
                mr.setReceiveCheck(true);
                mr.setReceiveMen(userId);
//                return;
//            }
        }
        return new MoneyReceiveResponseDto(moneyReceiveRepository.save(mr));
    }

}
