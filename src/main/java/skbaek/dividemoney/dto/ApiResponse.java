package skbaek.dividemoney.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class ApiResponse<T> {

    private String result = "OK";
    private String state;
    private String reason;
    private T data;

}
