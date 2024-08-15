package online.shopping.system.cart_service.mapper;

import online.shopping.system.cart_service.dto.CartItemDto;
import online.shopping.system.cart_service.entity.CartItem;

public interface CartItemMapper{

    CartItemDto toDto(CartItem cartItem);

}

