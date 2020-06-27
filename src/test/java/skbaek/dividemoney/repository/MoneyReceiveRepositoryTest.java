package skbaek.dividemoney.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import skbaek.dividemoney.dto.MoneyReceiveResponseDto;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.entity.MoneyReceive;
import skbaek.dividemoney.util.DivideUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class MoneyReceiveRepositoryTest {

    @Autowired
    private MoneyGiveRepository moneyGiveRepository;

    @Autowired
    private MoneyReceiveRepository moneyReceiveRepository;

    @BeforeEach
    void setUp() {
        String token = "abc";
        String roomId = "room-1";
        int userId = 999;
        int giveMoney = 2500;
        int receiveMens = 5;
        LocalDateTime giveTime = LocalDateTime.now().minusMinutes(11);

        MoneyGive moneyGive = MoneyGive.builder()
                .userId(userId)
                .whichRoom(roomId)
                .token(token)
                .giveMoney(giveMoney)
                .recieveMens(receiveMens)
                .giveTime(giveTime)
                .build();

        moneyGiveRepository.save(moneyGive);

        List<Integer> moneyList = DivideUtil.divideMoney(moneyGive);

        moneyReceiveRepository.saveAll(DivideUtil.devideListSet(moneyList,moneyGive));
    }

    @Test
    @Transactional
    void 줍기_정보저장_성공() {
        String token = "abc";
        String roomId = "room-1";
        int userId = 100;

        Optional<MoneyGive> moneyGive = moneyGiveRepository.findByTokenAndUserIdNotAndWhichRoom(token, userId, roomId);
        List<MoneyReceive> mrList = moneyReceiveRepository.findAll();
        MoneyReceiveResponseDto res = new MoneyReceiveResponseDto();

        if(moneyGive.isPresent()) {

            List<MoneyReceive> filter = mrList.stream().filter(data -> data.isReceiveCheck() == false).collect(Collectors.toList());

            for (int i = 0; i < filter.size(); i++) {
                MoneyReceive mr = filter.get(i);
                if (!mr.isReceiveCheck()) {
                    mr.setReceiveCheck(true);
                    mr.setReceiveMen(userId);
                    moneyReceiveRepository.save(mr);
                    res.setReceiveMoney(mr.getReceiveMoney());
                }
            }
        }

        MoneyReceive moneyReceive = mrList.get(0);

        assertThat(moneyReceive.getToken()).isEqualTo(token);
        assertThat(moneyReceive.isReceiveCheck()).isTrue();

    }

}