package cn.edu.ztbu.zmx.bbs.util;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;

public class BigDecimalTest {

    @Test
    public void bTest(){
        Integer b = new BigDecimal(10).divide(new BigDecimal(99),2,BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}

