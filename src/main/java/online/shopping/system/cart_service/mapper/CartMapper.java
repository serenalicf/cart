package online.shopping.system.cart_service.mapper;

import online.shopping.system.cart_service.dto.CartDto;
import online.shopping.system.cart_service.entity.Cart;

public interface CartMapper{
    CartDto toDto(Cart cart);
}
