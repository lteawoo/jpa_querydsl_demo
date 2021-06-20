package kr.taeu.jpaquerydsldemo;

import static kr.taeu.jpaquerydsldemo.customer.domain.QCustomerEntity.customerEntity;
import static kr.taeu.jpaquerydsldemo.order.domain.QOrderEntity.orderEntity;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.taeu.jpaquerydsldemo.customer.domain.CustomerEntity;
import kr.taeu.jpaquerydsldemo.customer.repository.CustomerRepository;
import kr.taeu.jpaquerydsldemo.order.domain.OrderEntity;
import kr.taeu.jpaquerydsldemo.order.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@SpringBootTest
public class CustomerTest {
    @PersistenceUnit
    EntityManagerFactory emf;

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
    public void implicitJoin() {
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
    public void notFetchJoin() {
        OrderEntity order = jpaQueryFactory.selectFrom(orderEntity)
                .innerJoin(orderEntity.customer, customerEntity)
                .where(orderEntity.menuName.eq("치킨"))
                .fetchOne();

        Assertions.assertFalse(emf.getPersistenceUnitUtil().isLoaded(order.getCustomer()));
        /*
            select
                orderentit0_.seq as seq1_1_,
                orderentit0_.customer_seq as customer4_1_,
                orderentit0_.menu_name as menu_nam2_1_,
                orderentit0_.price as price3_1_
            from
                order_base orderentit0_
            inner join customer customeren1_ on
                orderentit0_.customer_seq = customeren1_.seq
            where
                orderentit0_.menu_name =?
         */
    }

    @Test
    public void fetchJoin() {
        OrderEntity order = jpaQueryFactory.selectFrom(orderEntity)
                .innerJoin(orderEntity.customer, customerEntity).fetchJoin()
                .where(orderEntity.menuName.eq("치킨"))
                .fetchOne();

        Assertions.assertTrue(emf.getPersistenceUnitUtil().isLoaded(order.getCustomer()));
        /*
            select
                orderentit0_.seq as seq1_1_0_,
                customeren1_.seq as seq1_0_1_,
                orderentit0_.customer_seq as customer4_1_0_,
                orderentit0_.menu_name as menu_nam2_1_0_,
                orderentit0_.price as price3_1_0_,
                customeren1_.age as age2_0_1_,
                customeren1_.name as name3_0_1_
            from
                order_base orderentit0_
            inner join customer customeren1_ on
                orderentit0_.customer_seq = customeren1_.seq
            where
                orderentit0_.menu_name =?
         */
    }


    /*
        Where 구문
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
        Group By 구문
        orderEntity.price.sum()
        orderEntity.price.avg()
        orderEntity.price.min()
        orderEntity.price.max()
        orderEntity.price.count()
     */

    @Test
    public void grouping() {
        jpaQueryFactory
                .select(orderEntity.price.sum(),
                        orderEntity.price.avg())
                .from(orderEntity)
                .groupBy(orderEntity.customer)
                .having(orderEntity.price.sum().goe(50000))
                .fetch();

        /*
            select
                sum(orderentit0_.price) as col_0_0_,
                avg(orderentit0_.price) as col_1_0_
            from
                order_base orderentit0_
            group by
                orderentit0_.customer_seq
            having
                sum(orderentit0_.price) >= 50000
         */
    }

    @Test
    public void getResult() {
        // List
        List<CustomerEntity> customerEntityList = jpaQueryFactory
                .selectFrom(customerEntity)
                .fetch();

        // One - 단 한건만 조회되어야함 아니면 에러
        CustomerEntity entity = jpaQueryFactory
                .selectFrom(customerEntity)
                .where(customerEntity.name.eq("kim"))
                .fetchOne();

        // limit(1).fetchOne()과 동일
        CustomerEntity entity2 = jpaQueryFactory
                .selectFrom(customerEntity)
                .fetchFirst();

        // total count
        long count = jpaQueryFactory
                .selectFrom(customerEntity)
                .fetchCount();

        // 페이징정보를 담은 결과, 카운트쿼리도 같이 조회된다.
        QueryResults<CustomerEntity> pagingList = jpaQueryFactory
                .selectFrom(customerEntity)
                .fetchResults();
        pagingList.getLimit();
        pagingList.getOffset();
        pagingList.getTotal();
        pagingList.getResults();
    }
}
