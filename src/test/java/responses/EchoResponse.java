package responses;

public class EchoResponse {

    private String message;

    public EchoResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
