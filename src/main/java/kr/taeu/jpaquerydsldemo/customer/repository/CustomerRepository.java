package kr.taeu.jpaquerydsldemo.customer.repository;

import kr.taeu.jpaquerydsldemo.customer.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
