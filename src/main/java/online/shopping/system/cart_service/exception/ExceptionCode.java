package online.shopping.system.cart_service.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {

    String getErrorCode();

    //the getMessage method : new ParameterizedMessage(message, arguments).getFormattedMessage();
    //will automatically help you to replace {} with the arguments
    String getMessage(Object...arguments);

    HttpStatus getStatusCode();

}
