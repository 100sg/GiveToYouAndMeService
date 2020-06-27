package skbaek.dividemoney.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import skbaek.dividemoney.entity.MoneyReceive;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Setter @Getter
@NoArgsConstructor
public class DivideResponseDto {
    private LocalDateTime giveTime;
    private int giveMoney;
    private int receivedMoney;
    private List<Map<String, Integer>> receiveList;
}
