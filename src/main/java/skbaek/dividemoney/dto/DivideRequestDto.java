package skbaek.dividemoney.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.util.TokenUtil;

import java.time.LocalDateTime;

@Setter @Getter
public class DivideRequestDto {
    private int userId;
    private String whichRoom;
    private String token;

}
