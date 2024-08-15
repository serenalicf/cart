package online.shopping.system.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartModificationDto {

    private Integer itemId;
    private Long quantity;
    private Long quantityAdded;
    private String productCode;
    private String statusCode;
    private String errorCode;

}
