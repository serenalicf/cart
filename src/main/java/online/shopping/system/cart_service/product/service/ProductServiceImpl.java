package online.shopping.system.cart_service.product.service;

import online.shopping.system.cart_service.helper.RestHelper;
import online.shopping.system.cart_service.product.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Value("${product.rest.uri}")
    String productRestUri;

    @Autowired
    RestHelper restHelper;

    @Override
    public ProductDto getProduct(String productCode) {
        return restHelper.getObject(productRestUri + "/products/" + productCode, ProductDto.class, null);
    }
}
