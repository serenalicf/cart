package online.shopping.system.cart_service.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestHelperImpl implements RestHelper {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public <T> T getObject(String url, Class<T> response, HttpHeaders headers) {
        return getObjectEntity(url, response, headers).getBody();
    }

    @Override

    public <T> ResponseEntity<T> getObjectEntity(String url, Class<T> response, HttpHeaders headers) {
        HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, request, response);
    }

    @Override
    public <T> T postObject(String url, String request, Class<T> response) {
       return restTemplate.postForObject(url, request, response);
    }
}
