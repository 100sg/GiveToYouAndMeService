package skbaek.dividemoney.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import skbaek.dividemoney.dto.MoneyReceiveRequestDto;
import skbaek.dividemoney.dto.MoneyReceiveResponseDto;
import skbaek.dividemoney.service.ReceiveService;

@Slf4j
@RestController
@AllArgsConstructor
public class ReceiveController {

    private ReceiveService receiveService;

    @PostMapping("/givetome")
    public ResponseEntity giveMe(@RequestBody MoneyReceiveRequestDto requestDto,
                                 @RequestHeader(value = "X-USER-ID", required = true) int userId,
                                 @RequestHeader(value = "X-ROOM-ID", required = true) String roomId)
    {
        requestDto.setUserId(userId);
        requestDto.setWhichRoom(roomId);
        return ResponseEntity.ok(receiveService.save(requestDto));
    }

}
