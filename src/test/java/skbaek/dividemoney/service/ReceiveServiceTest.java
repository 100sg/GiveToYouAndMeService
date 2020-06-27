package skbaek.dividemoney.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import skbaek.dividemoney.dto.MoneyReceiveResponseDto;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.entity.MoneyReceive;
import skbaek.dividemoney.repository.MoneyGiveRepository;
import skbaek.dividemoney.repository.MoneyReceiveRepository;
import skbaek.dividemoney.util.DivideUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReceiveServiceTest {

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
                .giveTime(LocalDateTime.now())
                .build();

        moneyGiveRepository.save(moneyGive);
        List<Integer> moneyList = DivideUtil.divideMoney(moneyGive);

        moneyReceiveRepository.saveAll(DivideUtil.devideListSet(moneyList,moneyGive));
    }

    @Test
    @Transactional
    void 줍기_성공() {
        String token = "abc";
        String roomId = "room-1";
        int userId = 100;

        Optional<MoneyGive> moneyGive = moneyGiveRepository.findByTokenAndUserIdNotAndWhichRoom(token, userId, roomId);
        List<MoneyReceive> mrList = moneyReceiveRepository.findAll();

        if(moneyGive.isPresent()) {
            List<MoneyReceive> filter = mrList.stream().filter(data -> data.isReceiveCheck() == false).collect(Collectors.toList());

            for (int i = 0; i < filter.size(); i++) {
                MoneyReceive mr = filter.get(i);
                if (!mr.isReceiveCheck()) {
                    mr.setReceiveCheck(true);
                    mr.setReceiveMen(userId);
                    moneyReceiveRepository.save(mr);
                }
            }
        }

        MoneyReceive moneyReceive = mrList.get(0);

        assertThat(moneyReceive.getToken()).isEqualTo(token);
        assertThat(moneyReceive.isReceiveCheck()).isTrue();
    }

    @Test
    void 줍기_실패_줍기중복체크() {
        String token = "abc";
        String roomId = "room-1";
        int userId = 100;

        MoneyReceive tmp = moneyReceiveRepository.findAll().get(0);

        tmp.setReceiveMen(userId);
        tmp.setReceiveCheck(true);

        moneyReceiveRepository.save(tmp);

        assertThat(moneyReceiveRepository.existsByReceiveMenEqualsAndTokenEquals(userId, token)).isFalse();
    }

    @Test
    void 줍기_실패_뿌리기주체확인() {
        String token = "abc";
        String roomId = "room-1";
        int userId = 999;

        assertThat(moneyGiveRepository.existsByTokenEqualsAndUserIdEqualsAndWhichRoomEquals(token, userId, roomId)).isFalse();
    }

    @Test
    void 줍기_실패_이벤트가없는방(){
        String token = "abc";
        String roomId = "room-2";

        assertThat(!moneyGiveRepository.existsByTokenEqualsAndWhichRoomEquals(token, roomId)).isFalse();
    }

    @Test
    void 줍기_실패_시간초과(){
        String token = "abc";
        String roomId = "room-1";
        int userId = 999;

        MoneyGive tmp = moneyGiveRepository.findAll().get(0);

        assertThat(moneyGiveRepository.existsByTokenAndUserIdNotAndWhichRoomAndGiveTimeLessThanEqual(token, userId, roomId, LocalDateTime.now().plusMinutes(15))).isFalse();

        if(tmp.getGiveTime().plusMinutes(15).getHour() != tmp.getGiveTime().getHour())
            assertThat(tmp.getGiveTime().plusMinutes(15).getMinute()+60 - tmp.getGiveTime().getMinute() > 10  ).isFalse();
        else
            assertThat(tmp.getGiveTime().plusMinutes(15).getMinute() - tmp.getGiveTime().getMinute() > 10  ).isFalse();

    }

}