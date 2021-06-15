package kr.taeu.jpaquerydsldemo.customer.repository;

import kr.taeu.jpaquerydsldemo.customer.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, CustomerRepositorySupporter{

    /*
        select
            customeren0_.seq as seq1_0_,
            customeren0_.age as age2_0_,
            customeren0_.name as name3_0_
        from
            customer_entity customeren0_
        where
            (upper(customeren0_.name) like upper(?) escape ?)
            and customeren0_.age >=?

            파라미터에 %KIM% 같은 형식으로 매핑됨
     */
    List<CustomerEntity> findByNameContainingIgnoreCaseAndAgeGreaterThanEqual(String name, Integer age);

    /*
        select
            customeren0_.seq as seq1_0_,
            customeren0_.age as age2_0_,
            customeren0_.name as name3_0_
        from
            customer_entity customeren0_
        where
            (lower(customeren0_.name) like lower(concat('%', ?, '%')))
            and customeren0_.age >=?
     */
    @Query(value = "select c from CustomerEntity c where lower(c.name) like lower(concat('%', :name, '%')) and c.age >= :age")
    List<CustomerEntity> findWithJpql(@Param("name") String name, @Param("age") Integer age);
}
