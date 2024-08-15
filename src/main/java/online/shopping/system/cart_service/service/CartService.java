package online.shopping.system.cart_service.service;

import online.shopping.system.cart_service.dto.CartModificationDto;
import online.shopping.system.cart_service.dto.CreateCartItemRequestDTO;
import online.shopping.system.cart_service.entity.Cart;
import online.shopping.system.cart_service.exception.BusinessException;

public interface CartService {

    Cart createCart(Integer customerId, String token) throws BusinessException;

    Cart getLatestCart(Integer customerId, String token) throws BusinessException;

    CartModificationDto addItemToCart(Integer customerId, Integer cartId, CreateCartItemRequestDTO createCartItemRequestDTO, String token) throws BusinessException;

    CartModificationDto updateCartItemQuantity(Integer customerId, Integer cartId, Integer itemId, Long quantity, String token);
}
