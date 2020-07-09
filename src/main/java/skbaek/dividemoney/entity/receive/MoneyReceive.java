package skbaek.dividemoney.entity.receive;

import lombok.*;
import skbaek.dividemoney.entity.give.MoneyGive;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class MoneyReceive {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private String token;

    @Setter
    private int receiveMoney;

    @Setter
    private int receiveMen;

    @Setter
    private boolean receiveCheck;
    private int receivedHistory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "GIVE_TOKEN")
    private MoneyGive moneyGive;

    public void setMoneyGive(MoneyGive mg){
        this.moneyGive = mg;
    }

    @Builder
    public MoneyReceive(String token, int receiveMoney, int receiveMen, boolean receiveCheck, int receivedHistory, MoneyGive moneyGive){
        this.token = token;
        this.receiveMoney = receiveMoney;
        this.receiveMen = receiveMen;
        this.receiveCheck = receiveCheck;
        this.receivedHistory = receivedHistory;
        this.moneyGive = moneyGive;
    }


}
