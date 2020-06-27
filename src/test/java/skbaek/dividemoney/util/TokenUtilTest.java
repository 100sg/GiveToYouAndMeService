package skbaek.dividemoney.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenUtilTest {

    @Test
    public void tokenTest(){
        assertNotEquals("abc", TokenUtil.getToken());
    }

    @Test
    public void devideTest(){
//        assertEquals(0, DivideUtil.divide(1000, 3));
    }
}
