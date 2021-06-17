package kr.taeu.jpaquerydsldemo.order.repository;

import kr.taeu.jpaquerydsldemo.order.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
