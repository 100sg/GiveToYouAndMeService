package skbaek.dividemoney.util;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

import java.util.Random;

@Slf4j
public class TokenUtil {

    private static final char[] BASE_CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static int fixLen = 3;

    public static String getToken(){
        int BASE_LEN = BASE_CHARACTER.length;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        while(sb.length() < fixLen){
            sb.append(BASE_CHARACTER[random.nextInt(BASE_LEN)]);
        }

        log.debug("result string : {}", sb.toString());

        return sb.toString();
    }
}
