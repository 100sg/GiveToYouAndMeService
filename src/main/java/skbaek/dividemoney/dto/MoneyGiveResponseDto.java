package skbaek.dividemoney.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.entity.MoneyReceive;

@Getter
public class MoneyGiveResponseDto {
    private String token;

    public MoneyGiveResponseDto(MoneyGive entity){
        this.token = entity.getToken();
    }

}
