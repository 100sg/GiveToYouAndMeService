package skbaek.dividemoney.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.util.TokenUtil;

import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
public class RequestHeader {

    private int userId;
    private String roomId;

//    public MoneyGive toEntity(int nUserId, String nWhichRoom){
//        return MoneyGive.builder()
//                .userId(nUserId)
//                .giveTime(LocalDateTime.now())
//                .whichRoom(nWhichRoom)
//                .token(TokenUtil.getToken())
//                .build();
//    }
}
