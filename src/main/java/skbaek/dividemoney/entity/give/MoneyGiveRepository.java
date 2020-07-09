package skbaek.dividemoney.entity.give;

import org.springframework.data.jpa.repository.JpaRepository;
import skbaek.dividemoney.entity.give.MoneyGive;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MoneyGiveRepository extends JpaRepository<MoneyGive, Long>{

    MoneyGive findByTokenEqualsAndUserIdNotAndWhichRoomEquals(String token, int userId, String whichRoom);
//    boolean existsByTokenEqualsAndUserIdEqualsAndWhichRoomEquals(String token, int userId, String whichRoom);
//    boolean existsByTokenAndUserIdNotAndWhichRoomAndGiveTimeLessThanEqual(String token, int userId, String whichRoom, LocalDateTime timeGap);
//    boolean existsByTokenEqualsAndWhichRoomEquals(String token, String whichRoom);

}
