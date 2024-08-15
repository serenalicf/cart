package online.shopping.system.cart_service.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String productCode;

    private String name;

    private String description;

    private String image;

    private BigDecimal price;

    private Integer availableItemCount;

    private String categoryId;
}
