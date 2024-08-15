package online.shopping.system.cart_service.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private String customerId;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;

}
