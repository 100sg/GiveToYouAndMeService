package skbaek.dividemoney.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skbaek.dividemoney.entity.receive.MoneyReceive;

@Setter @Getter
@NoArgsConstructor
public class MoneyReceiveResponseDto {
    private int receiveMoney;

    public MoneyReceiveResponseDto(int money){
        this.receiveMoney = money;
    }

}
