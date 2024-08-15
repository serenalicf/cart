package online.shopping.system.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private String cartId;
    private BigDecimal totalPrice;
    private String customerId;
    private List<CartItemDto> items;

}
