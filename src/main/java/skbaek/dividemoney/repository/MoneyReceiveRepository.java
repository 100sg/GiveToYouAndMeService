package skbaek.dividemoney.repository;

import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import skbaek.dividemoney.entity.MoneyReceive;

import java.util.List;
import java.util.Optional;

public interface MoneyReceiveRepository extends JpaRepository<MoneyReceive, Long> {

    boolean existsByReceiveMenEqualsAndTokenEquals(int userId, String token);
    boolean existsByTokenEqualsAndReceiveCheckFalse(String token);

}
