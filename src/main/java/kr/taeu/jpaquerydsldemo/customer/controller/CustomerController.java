package kr.taeu.jpaquerydsldemo.customer.controller;

import kr.taeu.jpaquerydsldemo.customer.dto.CustomerRequest;
import kr.taeu.jpaquerydsldemo.customer.dto.CustomerResponse;
import kr.taeu.jpaquerydsldemo.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/method-names")
    public List<CustomerResponse> getAllByMethodNames(CustomerRequest customerRequest) {
        return customerService.getAllByMethodNames(customerRequest);
    }

    @GetMapping("/jpql")
    public List<CustomerResponse> getAllByJpql(CustomerRequest customerRequest) {
        return customerService.getAllByJpql(customerRequest);
    }

    @GetMapping("/querydsl")
    public List<CustomerResponse> getAllByQuerydsl(CustomerRequest customerRequest) {
        return customerService.getAllByQuerydsl(customerRequest);
    }

    @PostMapping
    public CustomerResponse create(CustomerRequest customerRequest) {
        return customerService.create(customerRequest);
    }

    @PutMapping("/{seq}")
    public CustomerResponse update(@PathVariable Long seq, CustomerRequest customerRequest) {
        return customerService.update(seq, customerRequest);
    }

    @DeleteMapping("/{seq}")
    public void delete(@PathVariable Long seq) {
        customerService.delete(seq);
    }
}
