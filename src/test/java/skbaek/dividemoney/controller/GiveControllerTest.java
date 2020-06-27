package skbaek.dividemoney.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import skbaek.dividemoney.dto.MoneyGiveRequestDto;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.entity.MoneyReceive;
import skbaek.dividemoney.repository.MoneyGiveRepository;
import skbaek.dividemoney.repository.MoneyReceiveRepository;
import skbaek.dividemoney.util.DivideUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GiveControllerTest {

    @Autowired
    private MoneyGiveRepository moneyGiveRepository;

    @Autowired
    private MoneyReceiveRepository moneyReceiveRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    private MoneyGive moneyGive;

    @BeforeEach
    void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

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
    void 뿌리기호출_성공() throws Exception {
        String url = "/givetoyou";
        String param = "{\"giveMoney\":\"500\" , \"recieveMens\":\"4\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-USER-ID", "999");
        headers.set("X-ROOM-ID", "room-1");

        mvc.perform(
                post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(param)
                .headers(headers)
                )
                .andExpect(status().isOk());

        MoneyGive resultGive = moneyGiveRepository.findAll().get(0);
        MoneyReceive resultReceive = moneyReceiveRepository.findAll().get(0);

        assertThat(resultGive.getGiveMoney()).isEqualTo(moneyGive.getGiveMoney());
        assertThat(resultGive.getRecieveMens()).isEqualTo(moneyGive.getRecieveMens());
        assertThat(resultGive.getUserId()).isEqualTo(moneyGive.getUserId());
    }

    @Test
    void 뿌리기호출_실페() throws Exception {
        String url = "/givetoyou";
        String param = "{\"giveMoney\":\"10\" , \"recieveMens\":\"20\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-USER-ID", "999");
        headers.set("X-ROOM-ID", "room-1");

        mvc.perform(
                post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(param)
                .headers(headers)
                )
                .andExpect(status().isOk());

        MoneyGive resultGive = moneyGiveRepository.findAll().get(0);
        MoneyReceive resultReceive = moneyReceiveRepository.findAll().get(0);

        assertThat(resultGive.getGiveMoney()).isEqualTo(moneyGive.getGiveMoney());
        assertThat(resultGive.getRecieveMens()).isEqualTo(moneyGive.getRecieveMens());
        assertThat(resultGive.getUserId()).isEqualTo(moneyGive.getUserId());
    }
}