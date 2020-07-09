package skbaek.dividemoney.entity.receive;

import org.springframework.data.jpa.repository.JpaRepository;
import skbaek.dividemoney.entity.receive.MoneyReceive;

public interface MoneyReceiveRepository extends JpaRepository<MoneyReceive, Long> {

    boolean existsByReceiveMenEqualsAndTokenEquals(int userId, String token);
    boolean existsByTokenEqualsAndReceiveCheckFalse(String token);

}
