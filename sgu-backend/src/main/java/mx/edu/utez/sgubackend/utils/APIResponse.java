package mx.edu.utez.sgubackend.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class APIResponse<T> {
    private T data;
    private String message;
    private HttpStatus status;
    private String error;

    public APIResponse(T data, String message, HttpStatus status, String error) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public APIResponse(String message, HttpStatus status, String error) {
        this.message = message;
        this.status = status;
        this.error = error;
    }
}
