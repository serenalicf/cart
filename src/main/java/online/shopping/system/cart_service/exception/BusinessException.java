package online.shopping.system.cart_service.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends Exception {
    private final String errorCode;
    private final HttpStatus statusCode;

    public BusinessException(ExceptionCode exceptionCode, Object...arguments) {
        super(exceptionCode.getMessage(arguments));
        this.errorCode = exceptionCode.getErrorCode();
        this.statusCode = exceptionCode.getStatusCode();
    }

    public BusinessException(String message, String errorCode, HttpStatus statusCode) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    public String getErrorCode(){
        return this.errorCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
