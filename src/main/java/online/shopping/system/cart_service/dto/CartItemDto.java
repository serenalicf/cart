package online.shopping.system.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.shopping.system.cart_service.product.dto.ProductDto;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private String itemId;
    private Long quantity;
    private ProductDto product;
    private BigDecimal totalPrice;

}
