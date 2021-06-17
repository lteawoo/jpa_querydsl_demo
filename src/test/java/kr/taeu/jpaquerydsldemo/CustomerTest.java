package kr.taeu.jpaquerydsldemo;

import static kr.taeu.jpaquerydsldemo.customer.domain.QCustomerEntity.customerEntity;
import static kr.taeu.jpaquerydsldemo.order.domain.QOrderEntity.orderEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.taeu.jpaquerydsldemo.customer.domain.CustomerEntity;
import kr.taeu.jpaquerydsldemo.customer.repository.CustomerRepository;
import kr.taeu.jpaquerydsldemo.order.domain.OrderEntity;
import kr.taeu.jpaquerydsldemo.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerTest {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    public void setup() {
        CustomerEntity lee = customerRepository.save(CustomerEntity.builder()
                .name("lee")
                .age(20)
                .build());
        orderRepository.save(OrderEntity.builder()
                .customer(lee)
                .menuName("치킨")
                .price(18000)
                .build());
        orderRepository.save(OrderEntity.builder()
                .customer(lee)
                .menuName("피자")
                .price(29000)
                .build());

        CustomerEntity kim = customerRepository.save(CustomerEntity.builder()
                .name("kim")
                .age(25)
                .build());
        orderRepository.save(OrderEntity.builder()
                .customer(kim)
                .menuName("돼지국밥")
                .price(6500)
                .build());
        orderRepository.save(OrderEntity.builder()
                .customer(kim)
                .menuName("부침개")
                .price(12000)
                .build());

        customerRepository.save(CustomerEntity.builder()
                .name("park")
                .age(30)
                .build());
    }

    @Test
    public void example() {
        jpaQueryFactory.selectFrom(customerEntity).fetch();

        jpaQueryFactory.selectFrom(orderEntity).fetch();
    }

    @Test
    public void implicit_join() {
        jpaQueryFactory.select(orderEntity.customer.name,
                orderEntity.customer.age,
                orderEntity.menuName,
                orderEntity.price)
                .from(orderEntity)
                .fetch();

        /*
        select
            customeren1_.name as col_0_0_,
            customeren1_.age as col_1_0_,
            orderentit0_.menu_name as col_2_0_,
            orderentit0_.price as col_3_0_
        from
            order_base orderentit0_
        cross join customer customeren1_
        where
            orderentit0_.customer_seq = customeren1_.seq
         */
    }

    @Test
    public void leftJoin() {
        jpaQueryFactory.selectFrom(orderEntity)
                .leftJoin(orderEntity.customer, customerEntity)
                .fetch();
    }

    @Test
    public void innerJoin() {
        jpaQueryFactory.selectFrom(customerEntity)
                .innerJoin(orderEntity)
                .on(orderEntity.customer.eq(customerEntity))
                .fetch();
    }

    @Test
    public void where_and() {
        jpaQueryFactory.selectFrom(customerEntity)
                .where(customerEntity.name.in("lee", "kim"),
                        customerEntity.age.gt(10))
                .fetch();
    }
    @Test
    public void where_or() {
        jpaQueryFactory.selectFrom(customerEntity)
                .where(customerEntity.name.in("lee", "kim")
                        .or(customerEntity.age.gt(10)))
                .fetch();
    }

    /*
    customerEntity.name.eq("kim");      // name = 'kim'
        customerEntity.name.ne("lee");      // name != 'lee'
        customerEntity.name.eq("park").not(); // name != 'park'
        customerEntity.name.isNotNull();     // name is not null
        customerEntity.name.in("lee", "kim"); // name in ("lee", "kim")
        customerEntity.name.like("lee%"); // name like 'lee%'
        customerEntity.name.startsWith("lee"); // name like 'lee%'
        customerEntity.name.contains("lee"); // name like '%lee%'
        customerEntity.age.goe(20); // age >= 20
        customerEntity.age.gt(20); // age > 20
        customerEntity.age.loe(20); // age <= 20
        customerEntity.age.lt(20); // age < 20
        customerEntity.age.between(10, 30); // age between
     */
}
