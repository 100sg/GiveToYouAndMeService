package skbaek.dividemoney.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import skbaek.dividemoney.dto.ApiResponse;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	public ResponseEntity handleException(Exception e){
		log.error("ApiExceptionHandler e.getMessage: {}, Exception: {} ", e.getMessage(), e);

		ApiResponse<String> response = new ApiResponse();
		response.setResult("FAIL");
		response.setReason("Exception");
//		response.setState("400");
		response.setData( e.getMessage() );


		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity exception(Exception e) {
		log.error("ApiExceptionHandler 2 e.getMessage{}, Exception: {} ", e.getMessage(), e);

		ApiResponse<String> response = new ApiResponse();
		response.setResult("FAIL");
		response.setReason("Exception");
//		response.setState("500");
		response.setData(e.getMessage());

		return ResponseEntity.badRequest().body(response);
	}

}
