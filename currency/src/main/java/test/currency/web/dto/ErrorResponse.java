package test.currency.web.dto;

public class ErrorResponse {
    private String reason;


    public ErrorResponse(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
