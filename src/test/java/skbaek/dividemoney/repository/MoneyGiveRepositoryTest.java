package skbaek.dividemoney.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.entity.MoneyReceive;
import skbaek.dividemoney.util.DivideUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class MoneyGiveRepositoryTest {

    @Autowired
    private MoneyGiveRepository moneyGiveRepository;

    @Autowired
    private MoneyReceiveRepository moneyReceiveRepository;

    @Test
    void 뿌리기_정보저장_성공() {
        String token = "abc";
        String roomId = "room-1";
        int userId = 999;
        int giveMoney = 10;
        int receiveMens = 5;

        MoneyGive moneyGive = MoneyGive.builder()
                .userId(userId)
                .whichRoom(roomId)
                .token(token)
                .giveMoney(giveMoney)
                .recieveMens(receiveMens)
                .build();

        moneyGiveRepository.save(moneyGive);

        List<Integer> moneyList = DivideUtil.divideMoney(moneyGive);

        moneyReceiveRepository.saveAll(DivideUtil.devideListSet(moneyList,moneyGive));

    }

    @Test
    void 뿌리기_정보저장_실패() {
        String token = "abc";
        int userId = 999;
        String roomId = "room-1";
        int giveMoney = 4;
        int receiveMens = 5;

        MoneyGive moneyGive = MoneyGive.builder()
                .userId(userId)
                .whichRoom(roomId)
                .token(token)
                .giveMoney(giveMoney)
                .recieveMens(receiveMens)
                .build();

        moneyGiveRepository.save(moneyGive);

        List<Integer> moneyList = DivideUtil.divideMoney(moneyGive);

        moneyReceiveRepository.saveAll(DivideUtil.devideListSet(moneyList,moneyGive));

    }

}