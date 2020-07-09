package skbaek.dividemoney.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DivideRequestDto {
    private int userId;
    private String whichRoom;
    private String token;

}
