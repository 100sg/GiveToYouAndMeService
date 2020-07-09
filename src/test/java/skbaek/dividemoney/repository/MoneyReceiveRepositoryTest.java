package skbaek.dividemoney.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import skbaek.dividemoney.dto.MoneyGiveRequestDto;
import skbaek.dividemoney.dto.MoneyReceiveResponseDto;
import skbaek.dividemoney.entity.give.MoneyGive;
import skbaek.dividemoney.entity.give.MoneyGiveRepository;
import skbaek.dividemoney.entity.receive.MoneyReceive;
import skbaek.dividemoney.entity.receive.MoneyReceiveRepository;
import skbaek.dividemoney.util.DivideUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    void setUp(){
        String token = "abc";
        String roomId = "room-1";
        int userId = 999;
        int giveMoney = 10;
        int receiveMens = 5;

        MoneyGiveRequestDto dto = new MoneyGiveRequestDto();
        dto.setGiveMoney(giveMoney);
        dto.setWhichRoom(roomId);
        dto.setUserId(userId);
        dto.setRecieveMens(receiveMens);
        dto.setToken(token);

        MoneyGive moneyGive = dto.toEntity();

        DivideUtil.devideListSet(DivideUtil.divideMoney(dto), moneyGive);

        moneyGiveRepository.save(moneyGive);
    }

    @Test
    void 줍기_성공() {
        MoneyReceive moneyReceive = moneyReceiveRepository.findAll().get(0);
        long no = moneyReceive.getNo();
        moneyReceive.setReceiveCheck(true);
        moneyReceive.setReceiveMen(222);
        moneyReceiveRepository.save(moneyReceive);
        assertThat(moneyReceiveRepository.findById(no).get().isReceiveCheck()).isTrue();
    }

}