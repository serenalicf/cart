package online.shopping.system.cart_service.controller;

import online.shopping.system.cart_service.dto.CartDto;
import online.shopping.system.cart_service.dto.CartModificationDto;
import online.shopping.system.cart_service.dto.CreateCartItemRequestDTO;
import online.shopping.system.cart_service.entity.Cart;
import online.shopping.system.cart_service.exception.BusinessException;
import online.shopping.system.cart_service.mapper.CartMapper;
import online.shopping.system.cart_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartMapper cartMapper;


    @PostMapping("/{customerId}/carts")

    public CartDto createCart(@Validated @PathVariable("customerId") Integer customerId, @RequestHeader("Authorization") String token) throws BusinessException {
        Cart cart = cartService.createCart(customerId, token);
        return cartMapper.toDto(cart);
    }

    @GetMapping(value = "/{customerId}/latestcart", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDto getLatestCart(@Validated @PathVariable("customerId") Integer customerId, @RequestHeader("Authorization") String token) throws BusinessException {
        Cart cart = cartService.getLatestCart(customerId, token);
        return cartMapper.toDto(cart);
    }

    @PostMapping("/{customerId}/carts/{cartId}/items")
    public CartModificationDto addItemToCart(@Validated @PathVariable("customerId") Integer customerId,
                                             @Validated @PathVariable("cartId")Integer cartId,
                                             @Validated @RequestBody CreateCartItemRequestDTO createCartItemRequestDTO,
                                             @RequestHeader("Authorization") String token) throws BusinessException {
        return cartService.addItemToCart(customerId, cartId, createCartItemRequestDTO, token);
    }

    @PutMapping("/{customerId}/carts/{cartId}/items/{itemId}")
    public CartModificationDto updateCartItemQuantity(@Validated @PathVariable("customerId") Integer customerId,
                                                      @Validated @PathVariable("cartId")Integer cartId,
                                                      @Validated @PathVariable("itemId")Integer itemId,
                                                      @Validated @RequestParam("quantity" ) Long quantity,
                                                      @RequestHeader("Authorization") String token) {
        return cartService.updateCartItemQuantity(customerId, cartId, itemId, quantity,token);
    }

}
