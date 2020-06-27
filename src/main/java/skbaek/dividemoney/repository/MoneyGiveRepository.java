package skbaek.dividemoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skbaek.dividemoney.entity.MoneyGive;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MoneyGiveRepository extends JpaRepository<MoneyGive, Long>{

    Optional<MoneyGive> findByTokenAndUserIdNotAndWhichRoom(String token, int userId, String whichRoom);
    Optional<MoneyGive> findByTokenEqualsAndUserIdEqualsAndWhichRoomEqualsAndGiveTimeGreaterThanEqual(String token, int userId, String whichRoom, LocalDateTime timeGap);
    boolean existsByTokenEqualsAndUserIdEqualsAndWhichRoomEquals(String token, int userId, String whichRoom);
    boolean existsByTokenAndUserIdNotAndWhichRoomAndGiveTimeLessThanEqual(String token, int userId, String whichRoom, LocalDateTime timeGap);
    boolean existsByTokenEqualsAndWhichRoomEquals(String token, String whichRoom);

}
