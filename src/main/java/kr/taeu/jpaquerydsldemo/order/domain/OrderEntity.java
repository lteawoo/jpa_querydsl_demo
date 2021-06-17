package kr.taeu.jpaquerydsldemo.order.domain;

import kr.taeu.jpaquerydsldemo.customer.domain.CustomerEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ORDER_BASE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_SEQ", referencedColumnName = "SEQ")
    private CustomerEntity customer;

    private String menuName;

    private Integer price;

    @Builder
    public OrderEntity(CustomerEntity customer, String menuName, Integer price) {
        this.customer = customer;
        this.menuName = menuName;
        this.price = price;
    }
}
