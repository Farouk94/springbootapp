package ws.springframework.exceptions;


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
