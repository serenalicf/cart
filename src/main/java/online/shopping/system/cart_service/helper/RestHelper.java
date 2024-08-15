package online.shopping.system.cart_service.helper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface RestHelper {

    public <T>T getObject(String url, Class<T> response, HttpHeaders headers);

    public <T> ResponseEntity<T> getObjectEntity(String url, Class<T> response, HttpHeaders header);

    public <T>T postObject(String url, String request, Class<T> response);

}
