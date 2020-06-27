package skbaek.dividemoney.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import skbaek.dividemoney.dto.ApiResponse;
import skbaek.dividemoney.dto.MoneyGiveRequestDto;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.service.GiveService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@AllArgsConstructor
public class GiveController {

    private GiveService giveService;

    @PostMapping("/givetoyou")
    public ResponseEntity giveToYou(@RequestBody MoneyGiveRequestDto requestDto,
                                    @RequestHeader(value = "X-USER-ID", required = true) int userId,
                                    @RequestHeader(value = "X-ROOM-ID", required = true) String roomId)
    {
        requestDto.setUserId(userId);
        requestDto.setWhichRoom(roomId);
        return ResponseEntity.ok(giveService.save(requestDto));
    }
}
