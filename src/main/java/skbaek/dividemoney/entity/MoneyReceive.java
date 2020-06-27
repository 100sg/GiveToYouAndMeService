package skbaek.dividemoney.entity;

import lombok.*;
import org.aspectj.weaver.patterns.IToken;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
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
