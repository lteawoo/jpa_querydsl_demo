package kr.taeu.jpaquerydsldemo.customer.dto;

import kr.taeu.jpaquerydsldemo.customer.domain.CustomerEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
@NoArgsConstructor
public class CustomerResponse {
    private Long seq;
    private String name;
    private Integer age;

    public void setName(String name) {
        log.info("set name: " + name);
        this.name = name;
    }

    public void setAge(Integer age) {
        log.info("set age: " + age);
        this.age = age;
    }

    @Builder
    public CustomerResponse(
            Long seq,
            String name,
            Integer age) {
        this.seq = seq;
        this.name = name;
        this.age = age;
    }

    public static CustomerResponse entityToDto (CustomerEntity entity) {
        return CustomerResponse.builder()
                .seq(entity.getSeq())
                .name(entity.getName())
                .age(entity.getAge())
                .build();
    }
}
