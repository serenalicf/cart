package online.shopping.system.cart_service.customer.service;


import lombok.extern.slf4j.Slf4j;
import online.shopping.system.cart_service.customer.dto.CustomerDto;
import online.shopping.system.cart_service.exception.BusinessException;
import online.shopping.system.cart_service.exception.constant.ErrorCode;
import online.shopping.system.cart_service.helper.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;


@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{

    @Value("${customer.rest.uri}")
    String customerRestUri;

    @Autowired
    RestHelper restHelper;

    @Override
    public CustomerDto getCustomer(Integer customerId, String token) throws BusinessException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        try{
            return restHelper.getObject(customerRestUri + "/customers/" + customerId,
                    CustomerDto.class,
                    headers
            );
        } catch (Exception ex){
            log.error(ex.getMessage(), ex);
            if(ex instanceof HttpStatusCodeException &&
                    ((HttpStatusCodeException) ex).getStatusCode()== HttpStatus.UNAUTHORIZED){
                throw new BusinessException(ErrorCode.NOT_ALLOW_ACCESS_OTHER_PERSON_CART);
            }
            throw new BusinessException(ErrorCode.UNABLE_TO_ACCESS_CUSTOMER, customerId);
        }
    }

}
