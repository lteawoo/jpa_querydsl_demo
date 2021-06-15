package kr.taeu.jpaquerydsldemo.customer.repository;

import static kr.taeu.jpaquerydsldemo.customer.domain.QCustomerEntity.customerEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.taeu.jpaquerydsldemo.customer.domain.CustomerEntity;
import kr.taeu.jpaquerydsldemo.customer.dto.CustomerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomerRepositorySupporterImpl implements CustomerRepositorySupporter {
    private final JPAQueryFactory jpaQueryFactory;

    /*
    select
        customeren0_.seq as seq1_0_,
        customeren0_.age as age2_0_,
        customeren0_.name as name3_0_
    from
        customer_entity customeren0_
    where
        (lower(customeren0_.name) like ? escape '!')
        and customeren0_.age >=?

        %kim%형식으로 매핑됨
     */
    @Override
    public List<CustomerEntity> findWithQuerydsl(CustomerRequest customerRequest) {
        return jpaQueryFactory.selectFrom(customerEntity)
                .where(containsName(customerRequest.getName()),
                        goeAge(customerRequest.getAge()))
                .fetch();
    }

    private BooleanExpression containsName(String name) {
        if (StringUtils.hasText(name)) {
            return customerEntity.name.containsIgnoreCase(name);
        }

        return null;
    }

    private BooleanExpression goeAge(Integer age) {
        if (!ObjectUtils.isEmpty(age)) {
            return customerEntity.age.goe(age);
        }

        return null;
    }
}
