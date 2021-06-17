package kr.taeu.jpaquerydsldemo.customer.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private String name;

    @Column
    private Integer age;

    @Builder
    public CustomerEntity(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public CustomerEntity updateName(String name) {
        if (StringUtils.hasText(name)) {
            this.name = name;
        }
        return this;
    }

    public CustomerEntity updateAge(Integer age) {
        if (!ObjectUtils.isEmpty(age)) {
            this.age = age;
        }
        return this;
    }
}
