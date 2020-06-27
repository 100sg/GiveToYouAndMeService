package skbaek.dividemoney.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import skbaek.dividemoney.dto.DivideRequestDto;
import skbaek.dividemoney.dto.DivideResponseDto;
import skbaek.dividemoney.service.CheckService;

@Slf4j
@RestController
@AllArgsConstructor
public class CheckController {

    private CheckService checkService;

    @PostMapping("/info")
    public ResponseEntity giveInfo(@RequestBody DivideRequestDto requestDto,
                                   @RequestHeader(value = "X-USER-ID", required = true) int userId,
                                   @RequestHeader(value = "X-ROOM-ID", required = true) String roomId)
    {
        requestDto.setUserId(userId);
        requestDto.setWhichRoom(roomId);
        return ResponseEntity.ok(checkService.save(requestDto));
    }

}
