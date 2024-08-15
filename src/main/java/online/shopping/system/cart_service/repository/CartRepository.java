package online.shopping.system.cart_service.repository;

import online.shopping.system.cart_service.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>, JpaSpecificationExecutor<Cart> {

    Optional<Cart> findFirstByCustomerIdOrderByLastModifiedOnDesc(Integer customerId);

    Optional<Cart> findByCartIdAndCustomerIdOrderByLastModifiedOnDesc(Integer cartId, Integer customerId);
}

