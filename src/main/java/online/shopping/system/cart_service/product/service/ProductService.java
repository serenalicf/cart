package online.shopping.system.cart_service.product.service;


import online.shopping.system.cart_service.product.dto.ProductDto;

public interface ProductService {
    ProductDto getProduct(String productCode);
}
