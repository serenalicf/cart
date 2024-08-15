package online.shopping.system.cart_service.mapper;

import online.shopping.system.cart_service.dto.CartDto;
import online.shopping.system.cart_service.dto.CartItemDto;
import online.shopping.system.cart_service.entity.Cart;
import online.shopping.system.cart_service.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartMapperImpl implements CartMapper {

    @Autowired
    CartItemMapper cartItemMapper;

    public CartDto toDto(Cart cart) {
        //convert cart to cart dto
        if (cart == null) {
            return null;
        }

        return CartDto.builder()
                .cartId(String.valueOf(cart.getCartId()))
                .customerId(String.valueOf(cart.getCustomerId()))
                .items(cartItemListToCartItemDtoList(cart.getItems()))
                .totalPrice(cart.getTotalPrice())
                .build();
    }

    protected List<CartItemDto> cartItemListToCartItemDtoList(List<CartItem> list) {
        if ( list == null ) {
            return null;
        }

        List<CartItemDto> list1 = new ArrayList<CartItemDto>( list.size() );
        for ( CartItem cartItem : list ) {
            list1.add(cartItemMapper.toDto(cartItem));
        }

        return list1;
    }
}