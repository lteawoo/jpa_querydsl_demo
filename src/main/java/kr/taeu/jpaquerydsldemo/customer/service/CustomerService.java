package kr.taeu.jpaquerydsldemo.customer.service;

import kr.taeu.jpaquerydsldemo.customer.domain.CustomerEntity;
import kr.taeu.jpaquerydsldemo.customer.dto.CustomerRequest;
import kr.taeu.jpaquerydsldemo.customer.dto.CustomerResponse;
import kr.taeu.jpaquerydsldemo.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<CustomerResponse> getAll() {
        return customerRepository.findAll().stream().map(CustomerResponse::entityToDto).collect(Collectors.toList());
    }

    public CustomerResponse create(CustomerRequest customerRequest) {
        return CustomerResponse.entityToDto(customerRepository.save(CustomerEntity.builder()
                .name(customerRequest.getName())
                .age(customerRequest.getAge())
                .build()));
    }

    public CustomerResponse update(Long seq, CustomerRequest customerRequest) {
        CustomerEntity customerEntity = customerRepository.findById(seq)
                .orElseThrow(() -> new RuntimeException("Not found customer"))
                .updateAge(customerRequest.getAge())
                .updateName(customerRequest.getName());

        return CustomerResponse.entityToDto(customerRepository.save(customerEntity));
    }

    public void delete(Long seq) {
        CustomerEntity customerEntity = customerRepository.findById(seq)
                .orElseThrow(() -> new RuntimeException("Not found customer"));

        customerRepository.delete(customerEntity);
    }
}
