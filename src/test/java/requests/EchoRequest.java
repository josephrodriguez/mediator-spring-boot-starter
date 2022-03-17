package requests;

import io.github.josephrodriguez.interfaces.Request;
import responses.EchoResponse;

public class EchoRequest implements Request<EchoResponse> {

    private String message;

    public EchoRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
