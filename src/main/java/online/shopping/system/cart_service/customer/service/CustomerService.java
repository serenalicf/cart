package online.shopping.system.cart_service.customer.service;

import online.shopping.system.cart_service.customer.dto.CustomerDto;
import online.shopping.system.cart_service.exception.BusinessException;

public interface CustomerService {

    CustomerDto getCustomer(Integer customerId, String token) throws BusinessException;

}
