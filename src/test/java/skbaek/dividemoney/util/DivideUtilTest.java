package skbaek.dividemoney.util;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.repository.MoneyGiveRepository;

import static org.junit.jupiter.api.Assertions.*;

class DivideUtilTest {

    @Autowired
    private MoneyGive moneyGive;

    @Autowired
    private MoneyGiveRepository moneyGiveRepository;

    @BeforeEach
    public void setUp(){

    }

    @AfterEach
    public void cleanUp(){

    }

    @Test
    public void divide() {

    }
}