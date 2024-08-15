package online.shopping.system.cart_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<ErrorDetails> handleBusinessException (Exception ex, HttpServletRequest request) throws Exception {
        //cast the ex to BusinessException object
        BusinessException bex = (BusinessException) ex;

        // get the error code and error message from the BusinessException object
        String errorCode = bex.getErrorCode();
        String errorMessage = bex.getMessage();
        HttpStatus statusCode = bex.getStatusCode();

        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(statusCode.value())
                .timestamp(LocalDateTime.now())
                .error(statusCode.name())
                .errorMessage(errorMessage)
                .errorCode(errorCode)
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build();

        return new ResponseEntity<ErrorDetails>(errorDetails, statusCode);
    }
}
