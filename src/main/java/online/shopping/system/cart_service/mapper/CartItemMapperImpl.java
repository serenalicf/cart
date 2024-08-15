package online.shopping.system.cart_service.mapper;

import online.shopping.system.cart_service.dto.CartItemDto;
import online.shopping.system.cart_service.entity.CartItem;
import online.shopping.system.cart_service.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapperImpl implements CartItemMapper {

    @Autowired
    ProductService productService;

    public CartItemDto toDto(CartItem cartItem) {

        //logic to convert CartItem to CartItemDto
        if (cartItem == null) {
            return null;
        }

        return CartItemDto.builder()
                .itemId(String.valueOf(cartItem.getItemId()))
                .quantity(cartItem.getQuantity())
                .product(productService.getProduct(cartItem.getProductCode()))
                .totalPrice(cartItem.getTotalPrice())
                .build();
    }

}
