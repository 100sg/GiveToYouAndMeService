package skbaek.dividemoney.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.entity.MoneyReceive;
import skbaek.dividemoney.util.TokenUtil;

import java.time.LocalDateTime;

@Getter
public class MoneyReceiveResponseDto {
    private int receiveMoney;

    public MoneyReceiveResponseDto(MoneyReceive entity){
        this.receiveMoney = entity.getReceiveMoney();
    }

}
