package ws.springframework.exceptions;

/**
 * Created by farou_000 on 01/11/2016.
 */
public class RegistredException extends RuntimeException {
    private String message;

    public RegistredException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
