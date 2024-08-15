package online.shopping.system.cart_service.exception.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import online.shopping.system.cart_service.exception.ExceptionCode;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ExceptionCode {
    CUSTOMER_NOT_FOUND("C0001","customer with id : {} is not found", HttpStatus.INTERNAL_SERVER_ERROR),

    SYSTEM_BUSY("C0002","System busy", HttpStatus.INTERNAL_SERVER_ERROR),

    CART_NOT_FOUND("C0003","cart with id : {} is not found", HttpStatus.INTERNAL_SERVER_ERROR),

    PRODUCT_NOT_FOUND("C004", "product with product code : {} is not found", HttpStatus.INTERNAL_SERVER_ERROR),

    INSUFFICIENT_PRODUCT("C005", "product with product code : {} is not sufficient", HttpStatus.INTERNAL_SERVER_ERROR),
    CART_ITEM_NOT_FOUND("C006","item with item id : {} is not found", HttpStatus.INTERNAL_SERVER_ERROR),

    UNABLE_TO_ACCESS_CUSTOMER("C007","unable to access customer with customer id: {}", HttpStatus.INTERNAL_SERVER_ERROR),

    NOT_ALLOW_ACCESS_OTHER_PERSON_CART("C008", "you are not allowed to access other person's cart", HttpStatus.UNAUTHORIZED);
    private final String errorCode;
    private final String message;

    private final HttpStatus statusCode;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage(Object... arguments) {
        if(arguments == null){
            return message;
        }
        return new ParameterizedMessage(message, arguments).getFormattedMessage();
    }

    @Override
    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
