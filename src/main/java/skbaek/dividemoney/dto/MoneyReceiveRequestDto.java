package skbaek.dividemoney.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skbaek.dividemoney.entity.MoneyReceive;

@Setter @Getter
@NoArgsConstructor
public class MoneyReceiveRequestDto {
    private String token;
    private String whichRoom;
    private int userId;

    public MoneyReceive toEntity(int userId){
        return MoneyReceive.builder()
                .receiveMen(userId)
                .receiveCheck(true)
                .build();
    }
}
