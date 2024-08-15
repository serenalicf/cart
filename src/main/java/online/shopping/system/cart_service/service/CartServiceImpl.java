package online.shopping.system.cart_service.service;

import online.shopping.system.cart_service.constant.CartStatusCode;
import online.shopping.system.cart_service.customer.service.CustomerService;
import online.shopping.system.cart_service.dto.CartModificationDto;
import online.shopping.system.cart_service.dto.CreateCartItemRequestDTO;
import online.shopping.system.cart_service.entity.Cart;
import online.shopping.system.cart_service.entity.CartItem;
import online.shopping.system.cart_service.exception.BusinessException;
import online.shopping.system.cart_service.exception.constant.ErrorCode;
import online.shopping.system.cart_service.product.dto.ProductDto;
import online.shopping.system.cart_service.product.service.ProductService;
import online.shopping.system.cart_service.repository.CartItemRepository;
import online.shopping.system.cart_service.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    CartItemRepository cartItemRepository;
    
    @Override
    public Cart createCart(Integer customerId, String token) throws BusinessException {
        //get latest cart
        Cart cart = getLatestCart(customerId, token);

        //if latest cart is empty , create an empty cart
        if(cart == null) {
            LocalDateTime currentTime = LocalDateTime.now();
            cart = Cart.builder()
                    .customerId(customerId)
                    .totalPrice(BigDecimal.valueOf(0.0))
                    .items(new ArrayList<>())
                    .createdOn(currentTime)
                    .lastModifiedOn(currentTime)
                    .build();
            try {
                cartRepository.save(cart);
            } catch (Exception ex){
                log.error(ex.getMessage(), ex);
                throw new BusinessException(ErrorCode.SYSTEM_BUSY);
            }
        }
        return cart;
    }

    @Override
    public Cart getLatestCart(Integer customerId, String token) throws BusinessException {
        try {
            Optional.ofNullable(customerService.getCustomer(customerId, token))
                    .orElseThrow(()->new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND, customerId));
            return cartRepository.findFirstByCustomerIdOrderByLastModifiedOnDesc(customerId).orElse(null);

        } catch (Exception ex){
            log.error(ex.getMessage(), ex);

            if(ex instanceof BusinessException){
                throw ex;
            }
            throw new BusinessException(ErrorCode.SYSTEM_BUSY);
        }
    }

    @Override
    public CartModificationDto addItemToCart(Integer customerId, Integer cartId, CreateCartItemRequestDTO createCartItemRequestDTO, String token) throws BusinessException {
        try {
           // call customer api
           Optional.ofNullable(customerService.getCustomer(customerId, token))
                    .orElseThrow(()->new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND, customerId));

            //check cart exist
            Cart cart =  cartRepository.findByCartIdAndCustomerIdOrderByLastModifiedOnDesc(cartId, customerId)
                    .orElseThrow(()-> new BusinessException(ErrorCode.CART_NOT_FOUND, cartId));


            ProductDto productDto = Optional.ofNullable(productService.getProduct(createCartItemRequestDTO.getProductCode()))
                    .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, createCartItemRequestDTO.getProductCode()));

            //check product availability
            if (productDto.getAvailableItemCount() < createCartItemRequestDTO.getQuantity()) {
                throw new BusinessException(ErrorCode.INSUFFICIENT_PRODUCT, createCartItemRequestDTO.getProductCode());
            }

            //get cart items
            List<CartItem> cartItemList = cart.getItems();

            CartItem cartItem = cartItemList.stream()
                        .filter(item -> item.getProductCode().equals(createCartItemRequestDTO.getProductCode()))
                        .findFirst()
                        .orElse(null);

            //create a new cart item if cartItem is null
            Long quantity = createCartItemRequestDTO.getQuantity();
            BigDecimal price = productDto.getPrice();
            CartModificationDto addCartItemResponseDto = CartModificationDto.builder()
                    .quantityAdded(createCartItemRequestDTO.getQuantity())
                    .productCode(createCartItemRequestDTO.getProductCode())
                    .build();
            if (cartItem == null) {
                cartItem = CartItem.builder()
                        .productCode(createCartItemRequestDTO.getProductCode())
                        .quantity(quantity)
                        .price(price)
                        .totalPrice(BigDecimal.valueOf(quantity).multiply(price))
                        .createdOn(LocalDateTime.now())
                        .lastModifiedOn(LocalDateTime.now())
                        .cart(cart)
                        .build();
                cartItemRepository.save(cartItem);
                cart.getItems().add(cartItem);
                addCartItemResponseDto.setItemId(cartItem.getItemId());
                addCartItemResponseDto.setQuantity(createCartItemRequestDTO.getQuantity());

            } else {
                Long newQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(newQuantity);
                cartItem.setTotalPrice(BigDecimal.valueOf(newQuantity).multiply(price));
                addCartItemResponseDto.setItemId(cartItem.getItemId());
                addCartItemResponseDto.setQuantity(cartItem.getQuantity());
            }
            calulateCart(cart);
            addCartItemResponseDto.setStatusCode(CartStatusCode.SUCCESS.name());
            return addCartItemResponseDto;
        } catch (Exception ex){
            log.error(ex.getMessage(), ex);
            CartModificationDto cartModificationDto = CartModificationDto.builder()
                    .productCode(createCartItemRequestDTO.getProductCode())
                    .statusCode(CartStatusCode.FAILED.name())
                    .build();
            if(ex instanceof BusinessException){
                cartModificationDto.setErrorCode(((BusinessException) ex).getErrorCode());
            } else {

                cartModificationDto.setErrorCode(ErrorCode.SYSTEM_BUSY.getErrorCode());
            }
            return cartModificationDto;
        }
    }

    @Override
    public CartModificationDto updateCartItemQuantity(Integer customerId, Integer cartId, Integer itemId, Long quantity, String token) {
        try {
            Optional.ofNullable(customerService.getCustomer(customerId, token))
                    .orElseThrow(() -> new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND, customerId));
            Cart cart = cartRepository.findByCartIdAndCustomerIdOrderByLastModifiedOnDesc(cartId, customerId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND, cartId));

            CartItem cartItem = cartItemRepository.findByItemId(itemId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND, itemId));

            Long originalQuantity = cartItem.getQuantity();

            Long quantityAdded = quantity - originalQuantity;

            if(quantity == 0) {
               cartItemRepository.delete(cartItem);
            } else {
                cartItem.setTotalPrice(BigDecimal.valueOf(quantity).multiply(cartItem.getPrice()));
                cartItem.setQuantity(quantity);
            }
            calulateCart(cart);
            cartRepository.save(cart);
            return CartModificationDto.builder()
                    .itemId(itemId)
                    .quantity(quantity)
                    .quantityAdded(quantityAdded)
                    .productCode(cartItem.getProductCode())
                    .statusCode(CartStatusCode.SUCCESS.name())
                    .build();

        } catch (Exception ex){
            log.error(ex.getMessage(), ex);
            CartModificationDto cartModificationDto = CartModificationDto.builder()
                    .itemId(itemId)
                    .statusCode(CartStatusCode.FAILED.name())
                    .build();
            if(ex instanceof BusinessException){
                cartModificationDto.setErrorCode(((BusinessException) ex).getErrorCode());
            } else {
                cartModificationDto.setErrorCode(ErrorCode.SYSTEM_BUSY.getErrorCode());
            }
            return cartModificationDto;
        }
    }

    public void calulateCart(Cart cart){
        //loop the cart items to calulate total price for the cart, update the total price for the cart
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(CartItem item : cart.getItems()){
            totalPrice = totalPrice.add(item.getTotalPrice());
        }
        cart.setTotalPrice(totalPrice);
        // save the cart
        cartRepository.save(cart);

    }
}
