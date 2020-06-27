package skbaek.dividemoney.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.repository.MoneyGiveRepository;
import skbaek.dividemoney.repository.MoneyReceiveRepository;
import skbaek.dividemoney.util.DivideUtil;

import static org.junit.jupiter.api.Assertions.*;

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

        moneyGive = MoneyGive.builder()
                .userId(userId)
                .whichRoom(roomId)
                .token(token)
                .giveMoney(giveMoney)
                .recieveMens(receiveMens)
                .build();

        moneyReceiveRepository.saveAll( DivideUtil.devideListSet(DivideUtil.divideMoney(moneyGive), moneyGive) );
        moneyGiveRepository.save(moneyGive);
    }
    @Test
    void 정보조회_성공() {

    }
}