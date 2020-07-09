package skbaek.dividemoney.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import skbaek.dividemoney.entity.give.MoneyGive;
import skbaek.dividemoney.entity.receive.MoneyReceive;
import skbaek.dividemoney.entity.give.MoneyGiveRepository;
import skbaek.dividemoney.entity.receive.MoneyReceiveRepository;
import skbaek.dividemoney.util.DivideUtil;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GiveServiceTest {

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

//        moneyReceiveRepository.saveAll( DivideUtil.devideListSet(DivideUtil.divideMoney(moneyGive), moneyGive) );
//        moneyGiveRepository.save(moneyGive);
    }

    @Test
    @Transactional
    void 뿌리기정보_저장_성공() {

        MoneyGive giveOne = moneyGiveRepository.findAll().get(0);
        MoneyReceive receiveOne = moneyReceiveRepository.findAll().get(0);

        assertThat(giveOne.getUserId()).isEqualTo(moneyGive.getUserId());
        assertThat(giveOne.getWhichRoom()).isEqualTo(moneyGive.getWhichRoom());
        assertThat(giveOne.getRecieveMens()).isEqualTo(moneyGive.getRecieveMens());
        assertThat(giveOne.getToken()).isEqualTo(moneyGive.getToken());

        assertThat(receiveOne.getReceiveMoney()).isBetween(1, 10);
        assertThat(receiveOne.isReceiveCheck()).isFalse();
        assertThat(receiveOne.getToken()).isEqualTo(receiveOne.getToken());

    }

}