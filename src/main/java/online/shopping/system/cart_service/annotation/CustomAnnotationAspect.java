package online.shopping.system.cart_service.annotation;

import online.shopping.system.cart_service.customer.service.CustomerService;
import online.shopping.system.cart_service.exception.BusinessException;
import online.shopping.system.cart_service.exception.constant.ErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class CustomAnnotationAspect {
    @Autowired
    CustomerService customerService;

    @Around("@annotation(validCustomer)")
    public void handleCustomerAnnotation( ProceedingJoinPoint joinPoint, ValidCustomer validCustomer) throws Throwable {
//        Object[] methodArgs = joinPoint.getArgs();
//        String customerId = (String) methodArgs[0];
//            Optional.ofNullable(customerService.getCustomer(Integer.parseInt(customerId)))
//                    .orElseThrow(()->new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND, customerId));
    }
}
