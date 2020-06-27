package skbaek.dividemoney.controller;

import org.aspectj.weaver.patterns.IToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import skbaek.dividemoney.dto.DivideResponseDto;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.repository.MoneyGiveRepository;
import skbaek.dividemoney.repository.MoneyReceiveRepository;
import skbaek.dividemoney.util.DivideUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CheckControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MoneyGiveRepository moneyGiveRepository;

    @Autowired
    private MoneyReceiveRepository moneyReceiveRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    private List<MoneyGive> moneyGive = new ArrayList<>();

    @BeforeEach
    void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

        moneyGive.add(MoneyGive.builder()
                .userId(999)
                .whichRoom("room-1")
                .token("abc")
                .giveMoney(10)
                .recieveMens(5)
                .giveTime(LocalDateTime.now())
                .build());

        moneyGive.add(MoneyGive.builder()
                .userId(999)
                .whichRoom("room-1")
                .token("def")
                .giveMoney(10)
                .recieveMens(5)
                .giveTime(LocalDateTime.now().minusDays(8))
                .build());

        moneyGiveRepository.saveAll(moneyGive);

        moneyReceiveRepository.saveAll(
                DivideUtil.devideListSet(
                    DivideUtil.divideMoney(moneyGive.get(0)),
                    moneyGive.get(0)
                )
        );
    }

    @Test
    void 정보조회_성공() throws Exception {
        String url = "/info";
        String param = "{\"token\":\"abc\"}";

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

        MoneyGive result = moneyGiveRepository.findAll().get(0);

        assertThat(result.getToken()).isEqualTo(moneyGive.get(0).getToken());
        assertThat(result.getRecieveMens()).isEqualTo(moneyGive.get(0).getRecieveMens());

    }

    @Test
    void 타인_정보조회_실패() throws Exception {
        String url = "/info";
        String param = "{\"token\":\"abc\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-USER-ID", "100");
        headers.set("X-ROOM-ID", "room-1");

        mvc.perform(
                post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(param)
                .headers(headers)
                )
                .andExpect(status().isOk());

    }

    @Test
    void 정보조회_기간초과() throws Exception {
        String url = "/info";
        String param = "{\"token\":\"def\"}";

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
    }

}