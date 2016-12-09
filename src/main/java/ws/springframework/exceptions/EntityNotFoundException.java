package ws.springframework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by farou_000 on 29/10/2016.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    String message;

    public EntityNotFoundException(String Id) {
        this.message = "Could not find entity with ID '" + Id + "'.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}