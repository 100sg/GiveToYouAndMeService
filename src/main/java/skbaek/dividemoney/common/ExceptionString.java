package skbaek.dividemoney.common;

public enum ExceptionString {

    NOT_EVENT_ROOM("해당 방에 뿌리기 이벤트가 없습니다"),
    NOT_OWN_RECEIVED("본인 뿌리기에는 줍기를 할 수 없습니다"),
    DONE_RECEIVED("줍기에 참여하셨습니다"),
    EXPIRED_TIME("줍기 가능한 시간이 지났습니다"),
    NOT_INFO("조회할 정보가 없습니다"),
    LACK_OF_MONEY("인원수 보다 많은 금액을 입력하세요"),
    EVENT_END("즙기 이벤트가 끝났습니다"),

    DB_CONNECTION_ERROR("DB connection error")
    ;

    private String message;
    ExceptionString(String code){
        this.message =  code;
    }
    public String getMsg() {
        return message;
    }

}
