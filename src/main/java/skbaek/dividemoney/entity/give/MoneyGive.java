package skbaek.dividemoney.entity.give;

import lombok.*;
import skbaek.dividemoney.entity.receive.MoneyReceive;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class MoneyGive {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private int userId;
    private int giveMoney;
    private LocalDateTime giveTime;
    private int recieveMens;
    private String whichRoom;
    @Column(name = "GIVE_TOKEN", unique = true)
    private String token;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "moneyGive", cascade = CascadeType.PERSIST)
    private List<MoneyReceive> receiveList = new ArrayList<>();

    public void addReceive(MoneyReceive moneyReceive) {
        receiveList.add(moneyReceive);
        moneyReceive.setMoneyGive(this);
    }

    @Builder
    public MoneyGive(int userId, int giveMoney, LocalDateTime giveTime, int recieveMens, String whichRoom, String token){
        this.userId = userId;
        this.giveMoney = giveMoney;
        this.giveTime = giveTime;
        this.recieveMens = recieveMens;
        this.whichRoom = whichRoom;
        this.token = token;
    }


}
