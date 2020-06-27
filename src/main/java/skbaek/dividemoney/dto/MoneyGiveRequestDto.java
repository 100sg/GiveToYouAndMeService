package skbaek.dividemoney.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.util.TokenUtil;

import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
public class MoneyGiveRequestDto {
    private int userId;
    private int giveMoney;
    private LocalDateTime giveTime;
    private int recieveMens;
    private String whichRoom;
    private String token;

    public MoneyGive toEntity(){
        return MoneyGive.builder()
                .userId(userId)
                .giveMoney(giveMoney)
                .giveTime(LocalDateTime.now())
                .recieveMens(recieveMens)
                .whichRoom(whichRoom)
                .token(TokenUtil.getToken())
                .build();
    }

}
