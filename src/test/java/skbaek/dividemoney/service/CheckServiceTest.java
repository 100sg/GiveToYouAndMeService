package skbaek.dividemoney.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import skbaek.dividemoney.dto.MoneyGiveRequestDto;
import skbaek.dividemoney.entity.give.MoneyGive;
import skbaek.dividemoney.entity.give.MoneyGiveRepository;
import skbaek.dividemoney.entity.receive.MoneyReceiveRepository;
import skbaek.dividemoney.util.DivideUtil;

import java.time.LocalDateTime;

@SpringBootTest
class CheckServiceTest {

    @Autowired
    private MoneyGiveRepository moneyGiveRepository;

    @Autowired
    private MoneyReceiveRepository moneyReceiveRepository;

    private MoneyGive moneyGive;

    @BeforeEach
    void setUp() {
        String token = "abc";
        String roomId = "room-1";
        int userId = 999;
        int giveMoney = 10;
        int receiveMens = 5;

        MoneyGiveRequestDto dto =
                MoneyGiveRequestDto.builder()
                        .userId(userId)
                        .giveMoney(giveMoney)
                        .recieveMens(receiveMens)
                        .giveTime(LocalDateTime.now())
                        .token(token)
                        .whichRoom(roomId)
                        .build();

        MoneyGive moneyGive = dto.toEntity();

        DivideUtil.devideListSet(DivideUtil.divideMoney(dto), moneyGive);

        moneyGiveRepository.save(moneyGive);
    }

    @Test
    void 정보조회_실패_본인아님() {


    }
}