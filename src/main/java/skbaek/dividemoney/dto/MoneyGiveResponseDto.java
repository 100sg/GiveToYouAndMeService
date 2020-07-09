package skbaek.dividemoney.dto;

import lombok.Getter;
import skbaek.dividemoney.entity.give.MoneyGive;

@Getter
public class MoneyGiveResponseDto {
    private String token;

    public MoneyGiveResponseDto(MoneyGive entity){
        this.token = entity.getToken();
    }

}
