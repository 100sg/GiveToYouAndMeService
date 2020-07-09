package skbaek.dividemoney.util;

import org.junit.jupiter.api.Test;
import skbaek.dividemoney.dto.MoneyGiveRequestDto;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TokenAndDivideUtilTest {

    @Test
    public void tokenTest(){
        assertNotEquals("abc", TokenUtil.getToken());
    }

    @Test
    public void devideTest(){
        MoneyGiveRequestDto dto = new MoneyGiveRequestDto();
        dto.setUserId(3);
        dto.setGiveMoney(1000);
        assertNotEquals(0, DivideUtil.divideMoney(dto));
    }
}
