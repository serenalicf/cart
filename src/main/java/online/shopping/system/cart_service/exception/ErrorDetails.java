package online.shopping.system.cart_service.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDetails {
    private int status;
    private LocalDateTime timestamp;
    private String error;
    private String errorMessage;
    private String errorCode;
    private String path;
    private String method;
}

