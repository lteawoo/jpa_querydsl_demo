package kr.taeu.jpaquerydsldemo.customer.repository;

import kr.taeu.jpaquerydsldemo.customer.domain.CustomerEntity;
import kr.taeu.jpaquerydsldemo.customer.dto.CustomerRequest;

import java.util.List;

public interface CustomerRepositorySupporter {
    List<CustomerEntity> findWithQuerydsl(CustomerRequest customerRequest);
}
